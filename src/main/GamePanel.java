package main;

import Ai.PathFinder;
import Entity.Entity;
import SaveAndLoad.SaveLoad;
import SaveAndLoad.SaveOthers;
import Tile.TileManager;
import object.OBJ;
import tiles_interactive.interactive_tile;
import Entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The GamePanel class has the role of initializing the game (e.g., creating instances for players/enemies, initializing the map, etc.) and keeping the graphical interface up to date with what is happening in the game.
 */
public class GamePanel extends JPanel implements Runnable {
    // setarile ecranului
    final int originalTileSize = 16;
    final int scale = 3;

    public int tileSize = originalTileSize * scale; //48*48
    public int maxScreenCol = 20;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol;//960 pixels
    public int screenHeight = tileSize * maxScreenRow;//576 pixels

    public final int maxWorldCol = 50;

    public final int maxWorldRow = 50;

    public final int maxMap = 10;

    public int currentMap = 0;

    int FPS = 60;

    //System
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);

    sound music = new sound();

    sound se = new sound();
    public CollisionCheck cCheck = new CollisionCheck(this);

    public AssetSetter aSetter = new AssetSetter(this);

    public UI ui = new UI(this);

    public EventHandler eHandler = new EventHandler(this);

    public ObjectGenerator eGenerator = new ObjectGenerator(this);


    Thread gameThread;

    //Entity and Object

    public OBJ obj[][] = new OBJ[maxMap][20];

    public Entity npc[][] = new Entity[maxMap][10];

    public Entity monster[][] = new Entity[maxMap][20];

    SaveLoad saveLoad = new SaveLoad(this);

    SaveOthers saveOthers = new SaveOthers(this);

    public interactive_tile iTile[][] = new interactive_tile[maxMap][50];

    public Entity projectile[][] = new Entity[maxMap][20];
    //public ArrayList<Entity> projectileList = new ArrayList<>();

    public ArrayList<Entity> particleList = new ArrayList<>();

    ArrayList<Entity> entityList = new ArrayList<>();

    public ArrayList<OBJ> objectList = new ArrayList<>();

    public PathFinder pFinder = new PathFinder(this);

    //Game state

    public gameState gameState;

    public GamePanel() throws IOException, FontFormatException {
        Player.getInstance(this);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        Player.getInstance(this).setKeyHandler(keyH);
        this.setFocusable(true);
    }

    /**
     * The setupGame() method initializes the game by performing several important tasks. Firstly, it creates instances of essential game objects such as the player, enemies, and map. These instances are created using appropriate classes and stored in variables for future reference.
     * Next, it sets up the graphical user interface (GUI) components necessary for displaying the game. This includes creating a window or frame to hold the game canvas, setting the size and layout of the window, and configuring any additional GUI elements such as buttons or labels.
     */
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();

        playMusic(1);
        gameState = main.gameState.titleState;

    }


    /**
     * The startGameThread() method initiates the game thread, which is a separate execution flow that handles the game's activities. It creates an instance of the Thread class based on the current instance of the GamePanel class.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    ;

    public void retry(){

        Player player = Player.getInstance(this);
        currentMap = 0;
        player.setDefaultPositions();
        player.restoreStatus();
        aSetter.setNPC();
        aSetter.setMonster();
    }

    public void restart(){

        Player player = Player.getInstance(this);
        player.setDefaultValues();
        player.setItems();
        aSetter.setObject();
        aSetter.setInteractiveTile();
    }

    /**
     *  This run() method initializes the game and then controls the frames per second using a while loop, preparing the new scene (Update()) to be drawn on the graphical interface (paintComponent()).
     */
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int drawCount = 0;
        long timer = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * The Update() method updates the game state (e.g., changes the players' positions based on key presses, moves enemies using AI techniques, creates different elements (tiles), etc.). If we are referring to a 2D game with a square map, it is divided into cells of the same size (like a chessboard), and these cells are tiles that can have different textures and represent various objects (e.g., tank projectiles, a tile with remnants of map elements but with a transparent background).
     */
    public void update() {

        Player player = Player.getInstance(this);
        if (gameState == main.gameState.playState) {
            //player
            player.update();
            //npc
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            //monster verde
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive) {
                        projectile[currentMap][i].update();
                    }
                    if (!projectile[currentMap][i].alive) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }

            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }

        }
        if (gameState == main.gameState.exitState) {
            gameState = main.gameState.titleState;
        }
    }

    /**
     * The paintComponent() method draws the changes made by the Update() method on the graphical interface. The graphical interface is a canvas, and by analogy, it can be considered as a drawing canvas on which various objects are drawn.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Player player = Player.getInstance(this);
        Graphics2D g2 = (Graphics2D) g;
        long drawStart = 0;
        if (keyH.showDebugText) {
            drawStart = System.nanoTime();
        }

        //Title screen

        if (gameState == main.gameState.titleState) {
            ui.draw(g2);
        } else {
            //Tile
            tileM.draw(g2);

            //interactive tile
            for (int i = 0; i < iTile[1].length; i++) {

                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            //Add entities to the list
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    objectList.add(obj[currentMap][i]);
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            //sort

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            Collections.sort(objectList, new Comparator<OBJ>() {
                @Override
                public int compare(OBJ e1, OBJ e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //Draw entities

            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            for (int i = 0; i < objectList.size(); i++) {
                objectList.get(i).draw(g2);
            }

            //Empty entity list

            entityList.clear();

            objectList.clear();

            //UI

            ui.draw(g2);


        }


        //DEBUG
        if (keyH.showDebugText) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);

            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX " + player.worldX, x, y);
            y += lineHeight;
            g2.drawString("WorldY " + player.worldY, x, y);
            y += lineHeight;
            g2.drawString("Col " + (player.worldX + player.solidArea.x) / tileSize, x, y);
            y += lineHeight;
            g2.drawString("Row " + (player.worldY + player.solidArea.y) / tileSize, x, y);
            y += lineHeight;

            g2.drawString("Draw Time: " + passed, x, y);

        }
        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }
}


