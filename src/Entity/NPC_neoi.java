package Entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * The NPC_neoi class implements the Neoi NPC, which assists the player in their adventure.
 */
public class NPC_neoi extends Entity{

    public NPC_neoi(GamePanel gp)
    {
        super(gp);

        direction = "down";
        speed = 2;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getImage();
        setDialogue();
    }

    public void getImage()
    {
        up1 = setup("res/NPC/neoi_up1_1",gp.tileSize,gp.tileSize);
        up2 = setup("res/NPC/neoi_up2_1",gp.tileSize,gp.tileSize);
        down1 = setup("res/NPC/neoi_down1_1",gp.tileSize,gp.tileSize);
        down2 = setup("res/NPC/neoi_down2_1",gp.tileSize,gp.tileSize);
        right1 = setup("res/NPC/neoi_right1_1",gp.tileSize,gp.tileSize);
        right2 = setup("res/NPC/neoi_right2_1",gp.tileSize,gp.tileSize);
        left1 = setup("res/NPC/neoi_left1_1",gp.tileSize,gp.tileSize);
        left2 = setup("res/NPC/neoi_left2_1",gp.tileSize,gp.tileSize);
    }


    public void setDialogue()
    {
        dialogues[0] = "Hello, Vagadun.\nI am here to help you to find the elixir.\nFollow me!";
    }

    public void setAction()
    {
        if(onPath){

            int goalCol = 33;
            int goalRow = 42;

            searchPath(goalCol, goalRow);
        }
        else {
            actionLockCounter++;

            if(actionLockCounter == 120)
            {
                Random random = new Random();
                int i = random.nextInt(100)+1; //alege un numar de la 1 la 100
                if(i <= 25)
                {
                    direction = "up";
                }
                if(i >= 25 && i <= 50)
                {
                    direction = "down";
                }
                if(i > 50 && i <= 75)
                {
                    direction = "left";
                }
                if(i > 75 && i <=100)
                {
                    direction = "right";
                }

                actionLockCounter = 0;

            }
        }


    }

    public void speak()
    {
        //sa faca lucruri specifice fiecare caracter
        super.speak();

        onPath = true;
    }

}
