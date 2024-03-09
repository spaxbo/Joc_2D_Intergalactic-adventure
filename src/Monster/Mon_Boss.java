package Monster;

import Entity.Entity;
import main.GamePanel;
import main.KeyHandler;
import main.gameState;
import object.*;
import Entity.Player;

import java.util.Random;

import static object.toolType.type_monster;

/**
 * This class implements the final boss monster.
 */
public class Mon_Boss extends Entity {

    GamePanel gp;

    public static final String monName = "Boss";

    public Mon_Boss(GamePanel gp) {

        super(gp);

        this.gp = gp;

        type = type_monster;
        name = monName;
        boss = true;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 60;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 50;
        knockBackPower = 5;

        int size = gp.tileSize * 5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;


        getImage();
        getAttackImage();
    }

    /**
     * The getImage() method uses specific sprites to display the visual appearance of this creature
     */
    public void getImage()
    {
        int i = 5;
        if(!inRage){

            up1 = setup("res/monster/boss_p1_up_1",gp.tileSize * i,gp.tileSize * i);
            up2 = setup("res/monster/boss_p1_up_2",gp.tileSize * i,gp.tileSize * i);
            down1 = setup("res/monster/boss_p1_down_1",gp.tileSize * i,gp.tileSize * i);
            down2 = setup("res/monster/boss_p1_down_2",gp.tileSize * i,gp.tileSize * i);
            left1 = setup("res/monster/boss_p1_left_1",gp.tileSize * i,gp.tileSize * i);
            left2 = setup("res/monster/boss_p1_left_2",gp.tileSize * i,gp.tileSize * i);
            right1 = setup("res/monster/boss_p1_right_1",gp.tileSize * i,gp.tileSize * i);
            right2 = setup("res/monster/boss_p1_right_2",gp.tileSize * i,gp.tileSize * i);
        }
        else {
            up1 = setup("res/monster/boss_p1_up_1",gp.tileSize * i,gp.tileSize * i);
            up2 = setup("res/monster/boss_p1_up_2",gp.tileSize * i,gp.tileSize * i);
            down1 = setup("res/monster/boss_p2_down_1",gp.tileSize * i,gp.tileSize * i);
            down2 = setup("res/monster/boss_p2_down_2",gp.tileSize * i,gp.tileSize * i);
            left1 = setup("res/monster/boss_p2_left_1",gp.tileSize * i,gp.tileSize * i);
            left2 = setup("res/monster/boss_p2_left_2",gp.tileSize * i,gp.tileSize * i);
            right1 = setup("res/monster/boss_p2_right_1",gp.tileSize * i,gp.tileSize * i);
            right2 = setup("res/monster/boss_p2_right_2",gp.tileSize * i,gp.tileSize * i);
        }

    }

    /**
     * The getAttackImage() method uses specific sprites to display the visual appearance of this creature when it attacks.
     */
    public void getAttackImage(){

        int i = 5;

        if(!inRage){
            attackUp1 = setup("res/monster/boss_p1_at_up_1",gp.tileSize * i,gp.tileSize * 2 * i);
            attackUp2 = setup("res/monster/boss_p1_at_up_2",gp.tileSize * i,gp.tileSize * 2 * i);
            attackDown1 = setup("res/monster/boss_p1_at_down_1",gp.tileSize * i,gp.tileSize * 2 * i);
            attackDown2 = setup("res/monster/boss_p1_at_down_2",gp.tileSize * i,gp.tileSize * 2 * i);
            attackLeft1 = setup("res/monster/boss_p1_at_left_1",gp.tileSize * 2 * i,gp.tileSize * i);
            attackLeft2 = setup("res/monster/boss_p1_at_left_2",gp.tileSize * 2 * i,gp.tileSize * i);
            attackRight1 = setup("res/monster/boss_p1_at_right_1",gp.tileSize * 2 * i,gp.tileSize * i);
            attackRight2 = setup("res/monster/boss_p1_at_right_2",gp.tileSize * 2 * i,gp.tileSize * i);
        }
        else{
            attackUp1 = setup("res/monster/boss_p1_at_up_1",gp.tileSize * i,gp.tileSize * 2 * i);
            attackUp2 = setup("res/monster/boss_p1_at_up_2",gp.tileSize * i,gp.tileSize * 2 * i);
            attackDown1 = setup("res/monster/boss_p2_at_down_1",gp.tileSize * i,gp.tileSize * 2 * i);
            attackDown2 = setup("res/monster/boss_p2_at_down_2",gp.tileSize * i,gp.tileSize * 2 * i);
            attackLeft1 = setup("res/monster/boss_p2_at_left_1",gp.tileSize * 2 * i,gp.tileSize * i);
            attackLeft2 = setup("res/monster/boss_p2_at_left_2",gp.tileSize * 2 * i,gp.tileSize * i);
            attackRight1 = setup("res/monster/boss_p2_at_right_1",gp.tileSize * 2 * i,gp.tileSize * i);
            attackRight2 = setup("res/monster/boss_p2_at_right_2",gp.tileSize * 2 * i,gp.tileSize * i);
        }
    }

    /**
     *  The setAction() method implements the abilities and behavior of the monster in the game
     */
    public void setAction() {

        Player player = Player.getInstance(gp);

        if(!inRage && life < maxLife/3){
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 10;
        }
        if(getTileDistance(player) < 10){
            moveTowardPlayer(120);
        }
        else{
            //get a random direction
            getRandomDirection();
        }

        //check if it attacks
        if(!attacking){

            checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * 5);
        }
    }
    public void damageReaction(){

        //Player player = Player.getInstance(gp, new KeyHandler(gp));
        actionLockCounter = 0;
        //direction = player.direction;
        onPath = true;
    }

    public void checkDrop() {

        dropItem(new Obj_frunza(gp));
        mesajTerminareJoc();
    }

    public void mesajTerminareJoc(){

        gp.gameState = gameState.dialogState;
        gp.ui.currentDialogue = "Congratulations you saved Tambola\nGood luck ahead!";
    }
}
