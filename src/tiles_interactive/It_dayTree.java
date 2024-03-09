package tiles_interactive;

import Entity.Entity;
import main.GamePanel;

import java.awt.*;

import static object.toolType.type_axe;

public class It_dayTree extends interactive_tile{

    GamePanel gp;

    public It_dayTree(GamePanel gp, int col, int row) {

        super(gp,col,row);
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("res/tiles_interactive/copac_taiat",gp.tileSize,gp.tileSize);
        destructible = true;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity){

        boolean isCorrectItem = false;

        if(entity.currentWeapon.type == type_axe){

            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    public interactive_tile getDestroyedForm(){

        interactive_tile tile = new IT_trunk(gp, worldX/ gp.tileSize, worldY/gp.tileSize);
        return tile;
    }

    public Color getParticleColor(){

        Color color = new Color(65,50,30);
        return color;
    }

    public int getParticleSize(){

        int size = 6;//pixels
        return size;
    }

    public int getParticleSpeed(){

        int speed = 1;//pixels
        return speed;
    }

    public int getParticleMaxLife(){

        int maxLife = 20;//pixels
        return maxLife;
    }
}
