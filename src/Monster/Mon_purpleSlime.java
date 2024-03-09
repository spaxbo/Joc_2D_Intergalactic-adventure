package Monster;

import Entity.Entity;
import main.GamePanel;
import main.KeyHandler;
import object.Obj_Coin;
import object.Obj_Heart;
import object.Obj_Mana;
import object.Obj_Rock;
import Entity.Player;

import java.util.Random;

import static object.toolType.type_monster;

/**
 * This class implements the monsters from the first level of the game.
 */
public class Mon_purpleSlime extends Entity {

    GamePanel gp;

    public Mon_purpleSlime(GamePanel gp) {

        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Purple slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 6;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        boss = false;

        getImage();
    }

    public void getImage()
    {
        up1 = setup("res/monster/slime_down_11",gp.tileSize,gp.tileSize);
        up2 = setup("res/monster/slime_down_21",gp.tileSize,gp.tileSize);
        down1 = setup("res/monster/slime_down_11",gp.tileSize,gp.tileSize);
        down2 = setup("res/monster/slime_down_21",gp.tileSize,gp.tileSize);
        left1 = setup("res/monster/slime_down_11",gp.tileSize,gp.tileSize);
        left2 = setup("res/monster/slime_down_21",gp.tileSize,gp.tileSize);
        right1 = setup("res/monster/slime_down_11",gp.tileSize,gp.tileSize);
        right2 = setup("res/monster/slime_down_21",gp.tileSize,gp.tileSize);
    }

    public void setAction() {

        Player player = Player.getInstance(gp);

        if(onPath){

            //check if stops chasing
            checkStopChasingOrNot(player, 15, 100);

            //search the direction to go
            searchPath(getCoalCol(player), getCoalRow(player));
        }
        else{
            //check if it starts chasing
            checkStartChasingOrNot(player, 5, 100);
            //get a random direction
            getRandomDirection();
        }
    }
    public void damageReaction(){

        //Player player = Player.getInstance(gp, new KeyHandler(gp));
        actionLockCounter = 0;
        //direction = player.direction;
        onPath = true;
    }

    public void checkDrop(){

        int i = new Random().nextInt(100) + 1;

        if(i < 40){

            dropItem(new Obj_Coin(gp));
        }
        if(i >= 40 && i < 75){

            dropItem(new Obj_Heart(gp));
        }
        if(i >= 75 && i < 100){

            dropItem(new Obj_Mana(gp));
        }
    }
}
