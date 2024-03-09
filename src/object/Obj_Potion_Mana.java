package object;

import Entity.Player;
import main.GamePanel;
import main.KeyHandler;
import main.gameState;

import static object.toolType.type_consumable;

public class Obj_Potion_Mana extends OBJ{

    public static final String objName = "Mana Potion";

    GamePanel gp;

    public Obj_Potion_Mana(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        value = 6;
        name = "Mana Potion";
        down1 = setup("res/objects/mana_galben", gp.tileSize, gp.tileSize);
        description = "(Mana potion)\nBoosts your mana by "+ value + ".";
        price = 4;
        stackable = true;
    }

    public void use(){

        Player player = Player.getInstance(gp);
        gp.gameState = gameState.dialogState;
        gp.ui.currentDialogue = "You drink the "+ name +"!\n"+"Your mana has been recovered by "+value +".";
        player.mana += value;
        if(player.mana > player.maxMana){

            player.mana = player.maxMana;
        }
    }
}
