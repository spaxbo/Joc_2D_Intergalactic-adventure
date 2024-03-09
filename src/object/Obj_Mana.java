package object;

import main.GamePanel;
import Entity.Player;
import main.KeyHandler;

import static object.toolType.type_pickUponly;

public class Obj_Mana extends OBJ {

    public static final String objName = "Mana";

    GamePanel gp;

    public Obj_Mana(GamePanel gp) {

        super(gp);
        this.gp = gp;

        type = type_pickUponly;
        name = objName;
        value = 1;
        down1 = setup("res/objects/manacrystal_full",gp.tileSize,gp.tileSize);
        image1 = setup("res/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("res/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
        price = 2;
        description = "+1 mana";
        stackable = true;
    }

    public void use() {

        Player player = Player.getInstance(gp);
        gp.ui.addMessage("Mana +" + value);
        player.mana += value;
    }
}
