package SaveAndLoad;

import Entity.Player;
import main.GamePanel;
import object.*;

import java.io.*;

/**
 * This class saves the information from DataStorage(), such as the player's inventory, the inventory state, or the objects on the map.
 */
public class SaveOthers {

    GamePanel gp;

    public SaveOthers(GamePanel gp){
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

    public void save2(){

        Player player = Player.getInstance(gp);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            ds.maxLife = player.maxLife;
            ds.maxMana = player.maxMana;
            ds.strength = player.strength;
            ds.dexterity = player.dexterity;
            ds.exp = player.exp;
            ds.nextLevelExp = player.nextLevelExp;
            ds.coin = player.coin;
            ds.worldX = player.worldX;
            ds.worldY = player.worldY;

            //player inventory
            for(int i = 0; i < player.inventory.size();i++){

                ds.itemNames.add(player.inventory.get(i).name);
                ds.itemAmounts.add(player.inventory.get(i).amount);
            }

            //player equipment
            ds.currentWeaponSlot = player.getCurrentWeaponSlot();
            ds.currentShieldSlot = player.getCurrentShieldSlot();

            //objects on map
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];

            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++){

                for(int i = 0; i < gp.obj[1].length; i++){

                    if(gp.obj[mapNum][i] ==null){
                        ds.mapObjectNames[mapNum][i] = "NA";
                    }
                    else{
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                    }
                }
            }

            // write the DataStorage object
            oos.writeObject(ds);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void load2(){

        Player player = Player.getInstance(gp);
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            // read the DataStorage object
            DataStorage ds = (DataStorage) ois.readObject();

            player.maxLife = ds.maxLife;
            player.maxMana = ds.maxMana;
            player.strength = ds.strength;
            player.dexterity = ds.dexterity;
            player.exp = ds.exp;
            player.nextLevelExp = ds.nextLevelExp;
            player.coin = ds.coin;
            player.worldX = ds.worldX;
            player.worldY = ds.worldY;

            //player inventory
            player.inventory.clear();
            for(int i = 0;i < ds.itemNames.size();i++){
                player.inventory.add(getObject(ds.itemNames.get(i)));
                player.inventory.get(i).amount = ds.itemAmounts.get(i);
            }

            //player equipment
            player.currentWeapon = player.inventory.get(ds.currentWeaponSlot);
            player.currentShield = player.inventory.get(ds.currentShieldSlot);
            player.getAttack();
            player.getDefense();
            player.getPlayerAttackImage();

            //objects on map

            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++){

                for(int i = 0; i < gp.obj[1].length; i++){
                    if(ds.mapObjectNames[mapNum][i].equals("NA")){
                        gp.obj[mapNum][i] = null;
                    }
                    else{
                        gp.obj[mapNum][i] = getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
