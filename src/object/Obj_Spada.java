package object;

import main.GamePanel;

public class Obj_Spada extends OBJ {

    public static final String objName = "Spada";
    public Obj_Spada(GamePanel gp)
    {
        super(gp);

        name = objName;
        down1 = setup("res/objects/spada",gp.tileSize,gp.tileSize);

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
