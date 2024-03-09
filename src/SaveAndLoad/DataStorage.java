package SaveAndLoad;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class stores the remaining information to be saved and prepares it for saving.
 */
public class DataStorage implements Serializable {

    //player stats not in database
    int maxLife;
    int maxMana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    int worldX;

    int worldY;

    //player inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();

    int currentWeaponSlot;

    int currentShieldSlot;

    //object on map
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;

    int [][] mapObjectWorldY;
}
