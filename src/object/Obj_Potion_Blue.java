package object;

import Entity.Player;
import main.GamePanel;
import main.KeyHandler;
import main.gameState;

import static object.toolType.type_consumable;

public class Obj_Potion_Blue extends OBJ {

    public static final String objName = "Blue Potion";

    GamePanel gp;
    public Obj_Potion_Blue(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        value = 10;
        name = objName;
        down1 = setup("res/objects/blue_potion", gp.tileSize, gp.tileSize);
        description = "(Blue potion)\nHeals your life by "+ value + ".";
        price = 4;
        stackable = true;
    }

    public void use(){

        Player player = Player.getInstance(gp);
        gp.gameState = gameState.dialogState;
        gp.ui.currentDialogue = "You drink the "+name +"!\n"+"Your life has been recovered by "+value +".";
        player.life += value;
        if(player.life > player.maxLife){

            player.life = player.maxLife;
        }
    }
}
