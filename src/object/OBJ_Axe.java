package object;

import main.GamePanel;

import static object.toolType.type_axe;

public class OBJ_Axe extends OBJ {

    public static final String objName = "Woodcutter's axe";

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        type = type_axe;
        name = objName;
        down1 = setup("res/objects/axe",gp.tileSize,gp.tileSize);
        attackValue = 2;
        attackArea.width = 25;
        attackArea.height = 25;
        description = "(Woodcutter's axe)\nA sharp weapon that can\ncut sharply";
        price = 3;
        knockBackPower = 10;
        motion1_duration = 20;
        motion2_duration = 40;
    }
}
