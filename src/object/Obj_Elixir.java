package object;

import main.GamePanel;

public class Obj_Elixir extends OBJ {

    public static final String objName = "Elixir";
    public Obj_Elixir(GamePanel gp)
    {
        super(gp);

        name = objName;
        down1 = setup("res/objects/elixir",gp.tileSize,gp.tileSize);

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
