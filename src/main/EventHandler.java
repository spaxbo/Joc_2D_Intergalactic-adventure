package main;

import Entity.Player;
import Entity.Entity;

import java.sql.SQLException;

/**
 *
 The EventHandler class is used to implement map changes through its hit() and teleport() methods
 */
public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;
    int previousEventX, previousEventY;

    boolean canTouchEvent = true;

    int tempMap, tempcol, tempRow;

    public EventHandler(GamePanel gp){

        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while( map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;

            if(col == gp.maxWorldCol){

                col = 0;
                row++;

                if(row == gp.maxWorldRow){

                    row = 0;
                    map++;
                }
            }
        }
    }

    /**
     *  The checkEvent() method is responsible for verifying if a specific tile is hit in order to teleport the player to another map.
     */
    public void checkEvent(){

        Player player = Player.getInstance(gp);
        int xDistance = Math.abs(player.worldX - previousEventX);
        int yDistance = Math.abs(player.worldY - previousEventY);

        int distance = Math.max(xDistance,yDistance);
        if(distance > gp.tileSize){

            canTouchEvent = true;
        }

        if(canTouchEvent){

            if(hit(0, 41, 26, "up")){

                try {
                    healingPool(gameState.dialogState);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if(hit(0, 43, 6, "any")){

                teleport(1,5,2);
            }
            else if(hit(1, 5, 2, "any")){

                teleport(0,43,6);
            }
            else if(hit(1, 2, 48, "any")){

                teleport(2,24,46);
            }
            else if(hit(2, 24, 46, "any")){

                teleport(1,2,48);
            }
            else if(hit(1, 46, 2, "up")){

                speak(gp.npc[1][0]);
            }
        }
    }
    public boolean hit(int map, int col, int row,String reqDirection) {

        boolean hit = false;
        Player player = Player.getInstance(gp);

        if(map == gp.currentMap){

            player.solidArea.x = player.worldX + player.solidArea.x;
            player.solidArea.y = player.worldY + player.solidArea.y;

            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (player.solidArea.intersects(eventRect[map][col][row])) {
                if (player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = player.worldX;
                    previousEventY = player.worldY;
                }
            }

            player.solidArea.x = player.solidAreaDefaultX;
            player.solidArea.y = player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        }

        return hit;
    }

    public void teleport(int map, int col,int row){

        gp.gameState = gameState.transitionState;
        tempMap = map;
        tempcol = col;
        tempRow = row;
        canTouchEvent = false;
    }

    public void healingPool(gameState gameState) throws SQLException {
        Player player = Player.getInstance(gp);
        if(gp.keyH.enterPressed)
        {
            gp.gameState = gameState;
            player.attackCanceled = true;
            gp.ui.currentDialogue = "Your hit the healing tree.\nYour life and mana has been recovered\nThe progress has been saved";
            player.life = player.maxLife;
            player.mana = player.maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
            gp.saveOthers.save2();
        }
    }

    public void speak(Entity entity){

        Player player = Player.getInstance(gp);
        if(gp.keyH.enterPressed){

            gp.gameState = gameState.dialogState;
            player.attackCanceled = true;
            entity.speak();
        }
    }
}
