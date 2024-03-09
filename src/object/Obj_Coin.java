package object;

import Entity.Player;
import main.GamePanel;
import main.KeyHandler;

import static object.toolType.type_pickUponly;

public class Obj_Coin extends OBJ {

    public static final String objName = "Coin";
    GamePanel gp;
    public Obj_Coin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickUponly;
        name = objName;
        value = 1;
        down1 = setup("res/objects/gold_coin",gp.tileSize,gp.tileSize);
    }

    public void use(){Player player = Player.getInstance(gp);
       gp.ui.addMessage("Coin +" + value);
       player.coin += value;
    }
}
