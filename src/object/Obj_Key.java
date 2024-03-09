package object;

import main.GamePanel;

import static object.toolType.type_pickUponly;

public class Obj_Key extends OBJ {

    public static final String objName = "Key";

    public Obj_Key(GamePanel gp)
    {
        super(gp);
        name = objName;

        type = type_pickUponly;
        down1 = setup("res/objects/key",gp.tileSize,gp.tileSize);

        price = 2;
        description = "(" + name +")\nIt opens a door.";
        stackable = true;
    }
}
