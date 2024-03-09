package Tile;

import Entity.Player;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * This class implements the game map and handles the importing of tiles and their collisions.
 */
public class TileManager {
    GamePanel gp;
    public Tile [] tile;

    public int[][][] mapTileNum;

    ArrayList<String> fileNames = new ArrayList<>();

    ArrayList<String> colissionStatus = new ArrayList<>();

    public TileManager(GamePanel gp)
    {
        this.gp=gp;

        InputStream is = getClass().getResourceAsStream("/maps/colission03.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        try {
            while((line = br.readLine()) != null ){
                fileNames.add(line);
                colissionStatus.add(br.readLine());

            }
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        tile = new Tile[fileNames.size()];

        //getTileImage();

        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/map03.txt",0);
        loadMap("/maps/level22.txt",1);
        loadMap("/maps/level31.txt",2);
    }

    /**
     * The getTileImage() method retrieves the image associated with a specific tile type, which is used in the setup() and loadMap() methods.
     */
    public void getTileImage()
    {
        for(int i = 0;i < fileNames.size();i++){

            String fileName;
            boolean colission;

            fileName = fileNames.get(i);
            if(colissionStatus.get(i).equals("true")){

                colission = true;
            }
            else{
                colission = false;
            }
            setup(i, fileName, colission);
        }
    }

    /**
     * @param index  The setup() method prepares the map to be populated by the loadMap() method
     * @param imageName
     * @param collision
     */
    public void setup(int index, String imageName, boolean collision)
    {
        UtilityTool uTool = new UtilityTool();

        try
        {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(new FileInputStream("res/tiles/"+imageName));
            tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param filePath The loadMap() method assembles the game map by placing the tiles in their correct positions and creating collisions between them
     * @param map
     */
    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;

                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param g2  The draw() method simply draws the game map on the screen and implements the flyweight design pattern
     */
    public void draw(Graphics2D g2)
    {
        KeyHandler keyH = new KeyHandler(gp);
        Player player = Player.getInstance(gp);
        int worldCol = 0;
        int worldRow = 0;
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)
        {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;

            int worldY = worldRow * gp.tileSize;

            int screenX = worldX - player.worldX + player.screenX;

            int screenY = worldY - player.worldY + player.screenY;


            if(worldX + gp.tileSize > player.worldX - player.screenX && worldX - gp.tileSize < player.worldX + player.screenX && worldY + gp.tileSize > player.worldY- player.screenY && worldY - gp.tileSize < player.worldY + player.screenY )
            {
                g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol)
            {
                worldCol = 0;
                worldRow ++;

            }
        }
    }
}
