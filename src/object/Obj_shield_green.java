package object;

import Entity.Entity;
import main.GamePanel;

import static object.toolType.type_shield;

public class Obj_shield_green extends OBJ {

    public static final String objName = "Green shield";
    public Obj_shield_green(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = objName;
        down1 = setup("res/objects/shield_green", gp.tileSize, gp.tileSize);
        defenseValue = 3;
        description = "(" + name + ")\nGreen top shield.";
        price = 5;
    }
}
