package object;

import main.GamePanel;

public class Obj_frunza extends OBJ {

    public static final String objName = "Frunza";
    public Obj_frunza(GamePanel gp)
    {
        super(gp);

        type = toolType.type_frunza;
        name = objName;
        down1 = setup("res/objects/frunza",gp.tileSize,gp.tileSize);

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
