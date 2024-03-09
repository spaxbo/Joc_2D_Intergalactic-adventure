package main;

import object.OBJ;
import object.OBJ_Axe;
import object.Obj_Elixir;
import object.Obj_frunza;
import object.Obj_Heart;
import object.Obj_Key;
import object.Obj_Mana;
import object.Obj_Potion_Blue;
import object.Obj_Potion_Mana;
import object.Obj_Spada;
import object.Obj_shield_green;
import object.Obj_Sword_1;

public class ObjectGenerator {

    GamePanel gp;

    public ObjectGenerator(GamePanel gp){

        this.gp = gp;
    }

    public OBJ getObject(String itemName){

        OBJ obj = null;
        switch (itemName){

            case OBJ_Axe.objName: obj = new OBJ_Axe(gp);break;
            case Obj_Elixir.objName:obj = new Obj_Elixir(gp);break;
            case Obj_Sword_1.objName:obj = new Obj_Sword_1(gp);break;
            case Obj_Potion_Mana.objName:obj = new Obj_Potion_Mana(gp);break;
            case Obj_shield_green.objName:obj = new Obj_shield_green(gp);break;
            case Obj_Potion_Blue.objName:obj = new Obj_Potion_Blue(gp);break;
            case Obj_frunza.objName:obj = new Obj_frunza(gp);break;
            case Obj_Spada.objName:obj = new Obj_Spada(gp);break;
            case Obj_Heart.objName:obj = new Obj_Heart(gp);break;
            case Obj_Key.objName:obj = new Obj_Key(gp);break;
            case Obj_Mana.objName:obj = new Obj_Mana(gp);break;
        }
        return obj;
    }
}
