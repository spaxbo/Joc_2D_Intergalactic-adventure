package main;

import Entity.NPC_neoi;
import Entity.NPC_vanzator;
import Monster.Mon_Boss;
import Monster.Mon_purpleSlime;
import Monster.Mon_redSlime;
import object.*;
import tiles_interactive.It_dayTree;

/**
 * The AssetSetter class allows for placing various entities and objects on the map at different positions.
 */
public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp)
    {
        this.gp = gp;
    }

    /**
     * The setObject() method enables placing an object on the map at a specific position. It can be used to position objects within the game map.
     */
    public void setObject()
    {
        int mapNum = 0;
        int i = 0;

        //Map 0
        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 10;
        gp.obj[mapNum][i].worldY = gp.tileSize * 8;
        i++;
        gp.obj[mapNum][i] = new Obj_Potion_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 28;
        gp.obj[mapNum][i].worldY = gp.tileSize * 7;
        i++;
        gp.obj[mapNum][i] = new Obj_Potion_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 7;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new Obj_Potion_Mana(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 39;
        gp.obj[mapNum][i].worldY = gp.tileSize * 9;
        i++;
        gp.obj[mapNum][i] = new Obj_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 6;
        gp.obj[mapNum][i].worldY = gp.tileSize * 43;
        i++;
        gp.obj[mapNum][i] = new Obj_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 28;
        gp.obj[mapNum][i].worldY = gp.tileSize * 43;
        i++;
        gp.obj[mapNum][i] = new Obj_Elixir(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 33;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;

        //Map 1
        mapNum = 1;
        i = 0;
        gp.obj[mapNum][i] = new Obj_Spada(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize;
        gp.obj[mapNum][i].worldY = gp.tileSize * 24;
        i++;
    }

    /**
     * The setNPC() method enables placing an NPC (non-playable character) on the map at a specific position. NPCs are non-playable characters with which the player can interact, such as quest characters or vendors in a role-playing game.
     */
    public void setNPC()
    {
        int mapNum = 0;
        try {
            if(mapNum > 2){
                throw new NoMapException("Nu exista mapa aceasta");
            }
        } catch (NoMapException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        gp.npc[mapNum][i] = new NPC_neoi(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 28;
        gp.npc[mapNum][i].worldY = gp.tileSize * 42;
        i++;

        mapNum = 1;
        try {
            if(mapNum > 2){
                throw new NoMapException("Nu exista mapa aceasta");
            }
        } catch (NoMapException e) {
            throw new RuntimeException(e);
        }
        i = 0;
        gp.npc[mapNum][i] = new NPC_vanzator(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 46;
        gp.npc[mapNum][i].worldY = 0;
    }

    /**
     * The setMonster() method enables placing a monster on the map at a specific position. Monsters are enemies that the player encounters in the game and must fight against to progress.
     */
    public void setMonster()
    {
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 8;
        gp.monster[mapNum][i].worldY = gp.tileSize * 8;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 11;
        gp.monster[mapNum][i].worldY = gp.tileSize * 8;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 27;
        gp.monster[mapNum][i].worldY = gp.tileSize * 8;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28;
        gp.monster[mapNum][i].worldY = gp.tileSize * 9;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28;
        gp.monster[mapNum][i].worldY = gp.tileSize * 14;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28;
        gp.monster[mapNum][i].worldY = gp.tileSize * 22;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 29;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 8;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 17;
        gp.monster[mapNum][i].worldY = gp.tileSize * 36;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 33;
        gp.monster[mapNum][i].worldY = gp.tileSize * 43;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 43;
        gp.monster[mapNum][i].worldY = gp.tileSize * 15;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 43;
        gp.monster[mapNum][i].worldY = gp.tileSize * 16;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 43;
        gp.monster[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 43;
        gp.monster[mapNum][i].worldY = gp.tileSize * 17;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 43;
        gp.monster[mapNum][i].worldY = gp.tileSize * 18;
        i++;
        gp.monster[mapNum][i] = new Mon_purpleSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 43;
        gp.monster[mapNum][i].worldY = gp.tileSize * 19;
        i++;

        mapNum = 1;
        i = 0;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 15;
        gp.monster[mapNum][i].worldY = gp.tileSize * 39;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 15;
        gp.monster[mapNum][i].worldY = gp.tileSize * 41;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 10;
        gp.monster[mapNum][i].worldY = gp.tileSize * 43;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 12;
        gp.monster[mapNum][i].worldY = gp.tileSize * 40;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 8;
        gp.monster[mapNum][i].worldY = gp.tileSize * 46;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 7;
        gp.monster[mapNum][i].worldY = gp.tileSize * 45;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 5;
        gp.monster[mapNum][i].worldY = gp.tileSize * 41;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 7;
        gp.monster[mapNum][i].worldY = gp.tileSize * 41;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 9;
        gp.monster[mapNum][i].worldY = gp.tileSize * 41;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 8;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 5;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 35;
        gp.monster[mapNum][i].worldY = gp.tileSize * 40;
        i++;
        gp.monster[mapNum][i] = new Mon_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
        gp.monster[mapNum][i].worldY = gp.tileSize * 40;
        i++;

        mapNum = 2;
        i = 0;
        gp.monster[mapNum][i] = new Mon_Boss(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 5;
        i++;
    }

    public void setInteractiveTile() {

        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new It_dayTree(gp,21,26);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,22,26);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,23,26);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,24,26);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,25,26);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,26,26);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,21,27);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,22,27);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,23,27);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,24,27);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,25,27);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,26,27);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,21,28);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,22,28);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,23,28);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,24,28);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,25,28);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,26,28);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,18,15);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,19,15);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,20,15);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,21,15);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,22,15);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,23,15);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,24,15);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,25,15);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,26,15);
        i++;

        mapNum = 1;
        i = 0;
        gp.iTile[mapNum][i] = new It_dayTree(gp,44,38);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,45,38);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,46,38);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,1,47);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,2,47);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,3,47);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,4,47);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,5,47);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,1,48);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,3,48);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,4,48);
        i++;
        gp.iTile[mapNum][i] = new It_dayTree(gp,5,48);
        i++;
    }

}
