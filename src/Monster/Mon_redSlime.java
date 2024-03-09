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
 * This class implements the monsters from the second level of the game.
 */
public class Mon_redSlime extends Entity {

    GamePanel gp;

    public Mon_redSlime(GamePanel gp) {

        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Red slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 4;
        defense = 0;
        exp = 2;
        projectile = new Obj_Rock(gp);

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
        up1 = setup("res/monster/slime_down_red_1",gp.tileSize,gp.tileSize);
        up2 = setup("res/monster/slime_down_red_2",gp.tileSize,gp.tileSize);
        down1 = setup("res/monster/slime_down_red_1",gp.tileSize,gp.tileSize);
        down2 = setup("res/monster/slime_down_red_2",gp.tileSize,gp.tileSize);
        left1 = setup("res/monster/slime_down_red_1",gp.tileSize,gp.tileSize);
        left2 = setup("res/monster/slime_down_red_2",gp.tileSize,gp.tileSize);
        right1 = setup("res/monster/slime_down_red_1",gp.tileSize,gp.tileSize);
        right2 = setup("res/monster/slime_down_red_2",gp.tileSize,gp.tileSize);
    }

    public void setAction() {

        Player player = Player.getInstance(gp);

        if(onPath){

            //check if stops chasing
           checkStopChasingOrNot(player, 15, 100);

            //search the direction to go
            searchPath(getCoalCol(player), getCoalRow(player));

            //check if it shoots a projectile
            checkShootOrNot(100, 30);
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

        if(i < 25){

            dropItem(new Obj_Coin(gp));
        }
        if(i >= 25 && i < 60){

            dropItem(new Obj_Heart(gp));
        }
        if(i >= 60 && i < 100){

            dropItem(new Obj_Mana(gp));
        }
    }
}
