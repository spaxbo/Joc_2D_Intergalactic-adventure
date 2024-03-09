package object;

import main.GamePanel;
import Entity.Player;
import main.KeyHandler;


import static object.toolType.type_pickUponly;

public class Obj_Heart extends OBJ {

    public static final String objName = "Heart";

    GamePanel gp;
    public Obj_Heart(GamePanel gp)
    {
        super(gp);
        this.gp = gp;

        type = type_pickUponly;
        name = objName;
        value = 2;
        down1 = setup("res/objects/heart_full",gp.tileSize,gp.tileSize);
        image1 = setup("res/objects/heart_full",gp.tileSize,gp.tileSize);
        image2 = setup("res/objects/heart_half",gp.tileSize,gp.tileSize);
        image3 = setup("res/objects/heart_blank",gp.tileSize,gp.tileSize);
        price = 2;
        description = "+2 life";
        stackable = true;
    }

    public void use() {

        Player player = Player.getInstance(gp);
        gp.ui.addMessage("Life +" + value);
        player.life += value;
    }
}
