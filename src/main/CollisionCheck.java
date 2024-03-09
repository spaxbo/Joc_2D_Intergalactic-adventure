package main;

import Entity.Entity;
import Entity.Player;
import object.OBJ;

/**
 *
 The CollisionCheck class aids in implementing collisions with various objects and entities.
 */
public class CollisionCheck {

    GamePanel gp;

    public CollisionCheck(GamePanel gp)
    {
        this.gp = gp;
    }

    /**
     * @param entity The checkTile() method verifies collisions with tiles used in the game,
     */
    public void checkTile(Entity entity)
    {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y+ entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;

        int entityRightCol = entityRightWorldX/gp.tileSize;

        int entityTopRow = entityTopWorldY/gp.tileSize;

        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1,tileNum2;

        String direction = entity.direction;

        if(entity.knockBack){

            direction = entity.knockBackDirection;
        }

        switch (direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }

    /**
     * @param entity The checkObject() method handles checking collisions between the player and objects in the game, such as boxes, treasures, or other interactive objects. It can verify if the player has entered an area occupied by an object, if they have interacted with the object, or if they can pick it up or use it in some way.
     * @param player
     * @return
     */
    public int checkObject(Entity entity, boolean player)
    {
        int index = 999;

        for(int i =0 ;i < gp.obj[1].length;i++)
        {
            if(gp.obj[gp.currentMap][i] != null)
            {
                //position solid area entity
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }
                if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea))
                {
                    if(gp.obj[gp.currentMap][i].collision)
                    {
                        entity.collisionOn = true;
                    }
                    if(player)
                    {
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * @param entity The checkEntity() method checks collisions between the player and entities in the game, such as monsters, NPCs, or other characters. It can determine if the player has entered an area occupied by an entity, if they have been attacked by an entity, or if they have interacted with it in a specific manner.
     * @param target
     * @return
     */
    //npc or monster collision
    public int checkEntity(Entity entity,Entity[][] target)
    {
        int index = 999;

        String direction = entity.direction;

        if(entity.knockBack){

            direction = entity.knockBackDirection;
        }

        for(int i =0 ;i < target[1].length;i++)
        {
            if(target[gp.currentMap][i] != null)
            {
                //position solid area entity
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                switch (direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }
                if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea))
                {
                    if(target[gp.currentMap][i] !=entity)
                    {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity)
    {
        boolean contactPlayer = false;

        Player player = Player.getInstance(gp);

        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        player.solidArea.x = player.worldX + player.solidArea.x;
        player.solidArea.y = player.worldY + player.solidArea.y;

        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
        }
        if(entity.solidArea.intersects(player.solidArea))
        {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;

        return contactPlayer;
    }

}
