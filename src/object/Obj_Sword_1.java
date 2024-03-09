package object;

import main.GamePanel;

import static object.toolType.type_sword;

public class Obj_Sword_1 extends OBJ {

    public static final String objName = "Sword";
    public Obj_Sword_1(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = objName;
        down1 = setup("res/objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "(" + name +")\nFirst Sword.";
        price = 2;
        knockBackPower = 5;
        motion1_duration = 5;
        motion2_duration = 25;
    }
}
