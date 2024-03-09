package Entity;

import main.GamePanel;
import main.gameState;
import object.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * The "NPC_vendor" class implements the vendor NPC named Meloi, who assists the player in their adventure.
 */
public class NPC_vanzator extends Entity{
    public NPC_vanzator(GamePanel gp)
    {
        super(gp);

        direction = "down";

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage()
    {
        up1 = setup("res/NPC/meloi_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("res/NPC/meloi_down_2",gp.tileSize,gp.tileSize);
        down1 = setup("res/NPC/meloi_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("res/NPC/meloi_down_2",gp.tileSize,gp.tileSize);
        right1 = setup("res/NPC/meloi_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("res/NPC/meloi_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("res/NPC/meloi_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("res/NPC/meloi_down_2",gp.tileSize,gp.tileSize);
    }

    public void setDialogue()
    {
        dialogues[0] = "Hello, Vagadun.\nNow that you found me do you want to trade?\nI have some shiny toys";
    }

    public void setItems(){

        inventory.add(new Obj_Potion_Mana(gp));
        inventory.add(new Obj_Heart(gp));
        inventory.add(new Obj_Mana(gp));
        inventory.add(new Obj_shield_green(gp));
        inventory.add(new Obj_Potion_Blue(gp));
    }

    public void speak(){

        super.speak();
        gp.gameState = gameState.tradeState;
        gp.ui.npc = this;
    }
}
