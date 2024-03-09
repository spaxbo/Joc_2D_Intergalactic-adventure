package object;

import Entity.Entity;
import Entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class Obj_Rock extends Projectile {

    public static final String objName = "Rock";

    GamePanel gp;
    public Obj_Rock(GamePanel gp) {

        super(gp);
        this.gp = gp;

        name = objName;
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 3;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = setup("res/Projectile/rock_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("res/Projectile/rock_down_1",gp.tileSize,gp.tileSize);
        down1 = setup("res/Projectile/rock_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("res/Projectile/rock_down_1",gp.tileSize,gp.tileSize);
        left1 = setup("res/Projectile/rock_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("res/Projectile/rock_down_1",gp.tileSize,gp.tileSize);
        right1 = setup("res/Projectile/rock_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("res/Projectile/rock_down_1",gp.tileSize,gp.tileSize);
    }

    public Color getParticleColor(){

        Color color = new Color(40,50,0);
        return color;
    }

    public int getParticleSize(){

        int size = 10;//pixels
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
