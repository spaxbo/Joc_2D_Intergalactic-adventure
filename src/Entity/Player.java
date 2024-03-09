package Entity;

import main.GamePanel;
import main.KeyHandler;
import main.gameState;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import static object.toolType.*;

/**
 *The Player class utilizes the singleton design pattern. This class implements the game's protagonist along with all their abilities and objects.
 */
public class Player extends Entity{

    private static Player instance;
    KeyHandler keyH;

    public final int screenX;

    public final int screenY;

    public boolean dashOn = false;

    int standCounter = 0;

    public boolean attackCanceled = false;

    private Player(GamePanel gp){
        super(gp);

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX =solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        getGuardImage();
        setItems();
    }

    public void setKeyHandler(KeyHandler keyH){

        this.keyH = keyH;
    }

    public static Player getInstance(GamePanel gp){
        if(instance == null){

            instance = new Player(gp);
        }
        return instance;
    }

    /**
     * The setDefaultValues() method in the Player class initializes the player's attributes and properties to their default values at the start of the game or when the player's state is reset. This method typically assigns values to variables such as the player's health, mana, level, experience, or any other relevant gameplay attributes.
     */
    public void setDefaultValues()
    {
        worldX = gp.tileSize * 6;
        worldY= gp.tileSize * 27;

        direction = "down";
        defaultSpeed = 4;
        speed = defaultSpeed;

        //Player status
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 5;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 20;
        currentWeapon = new Obj_Sword_1(gp);
        currentShield = new Obj_shield_green(gp);
        projectile = new Obj_Fireball(gp);
        attack = getAttack(); // strength and sword
        defense = getDefense(); //dexterity and shield
    }

    public void setItems()
    {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new Obj_Key(gp));
    }

    public void setDefaultPositions(){

        worldX = gp.tileSize * 6;
        worldY= gp.tileSize * 27;
        direction = "down";
    }

    public void restoreStatus(){

        life = maxLife/2;
        mana = maxMana/2;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
    }

    public int getAttack(){

        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){

        return defense = dexterity * currentShield.defenseValue;
    }

    /**
     * The getPlayerImage() method in the Player class retrieves the image or sprite that visually represents the player character in the game.
     */
    public void getPlayerImage()
    {
        up1 = setup("res/player/boy_up11_1",gp.tileSize,gp.tileSize);
        up2 = setup("res/player/boy_up12_1",gp.tileSize,gp.tileSize);
        down1 = setup("res/player/boy_down11_1",gp.tileSize,gp.tileSize);
        down2 = setup("res/player/boy_down12_1",gp.tileSize,gp.tileSize);
        right1 = setup("res/player/boy_right_11_1",gp.tileSize,gp.tileSize);
        right2 = setup("res/player/boy_right12_1",gp.tileSize,gp.tileSize);
        left1 = setup("res/player/boy_left11_1",gp.tileSize,gp.tileSize);
        left2 = setup("res/player/boy_left12_1",gp.tileSize,gp.tileSize);
    }

    public void getPlayerAttackImage()
    {

        if(currentWeapon.type == type_sword){
            attackUp1 = setup("res/player/boy_attack_up_11",gp.tileSize,gp.tileSize * 2);
            attackUp2 = setup("res/player/boy_attack_up_12",gp.tileSize,gp.tileSize * 2);
            attackDown1 = setup("res/player/boy_attack_down_11",gp.tileSize,gp.tileSize * 2);
            attackDown2 = setup("res/player/boy_attack_down_12",gp.tileSize,gp.tileSize * 2);
            attackLeft1 = setup("res/player/boy_attack_left_11",gp.tileSize * 2,gp.tileSize);
            attackLeft2 = setup("res/player/boy_attack_left_12",gp.tileSize * 2,gp.tileSize);
            attackRight1 = setup("res/player/boy_attack_right_11",gp.tileSize * 2,gp.tileSize);
            attackRight2 = setup("res/player/boy_attack_right_12",gp.tileSize * 2,gp.tileSize);
        }

        if(currentWeapon.type == type_axe){
            attackUp1 = setup("res/player/boy_attack_axe_up_1",gp.tileSize,gp.tileSize * 2);
            attackUp2 = setup("res/player/boy_attack_axe_up_2",gp.tileSize,gp.tileSize * 2);
            attackDown1 = setup("res/player/boy_attack_axe_down_1",gp.tileSize,gp.tileSize * 2);
            attackDown2 = setup("res/player/boy_attack_axe_down_2",gp.tileSize,gp.tileSize * 2);
            attackLeft1 = setup("res/player/boy_attack_axe_left_1",gp.tileSize * 2,gp.tileSize);
            attackLeft2 = setup("res/player/boy_attack_axe_left_2",gp.tileSize * 2,gp.tileSize);
            attackRight1 = setup("res/player/boy_attack_axe_right_1",gp.tileSize * 2,gp.tileSize);
            attackRight2 = setup("res/player/boy_attack_axe_right_2",gp.tileSize * 2,gp.tileSize);
        }

    }

    public void getGuardImage(){

        guardUp = setup("res/player/boy_guard_up",gp.tileSize,gp.tileSize);
        guardDown = setup("res/player/boy_guard_down",gp.tileSize,gp.tileSize);
        guardLeft = setup("res/player/boy_guard_left",gp.tileSize,gp.tileSize);
        guardRight = setup("res/player/boy_guard_right",gp.tileSize,gp.tileSize);
    }

    /**
     * The update() method modifies the protagonist
     */
    public void update()
    {
        if(knockBack) {


            // Verificare coliziune
            collisionOn = false;
            gp.cCheck.checkTile(this);
            gp.cCheck.checkObject(this,true);
            gp.cCheck.checkEntity(this,gp.npc);
            gp.cCheck.checkEntity(this,gp.monster);
            gp.cCheck.checkEntity(this,gp.iTile);


            if (collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            knockBackCounter++;
            if (knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else if(attacking){

            attacking();

        }
        else if(keyH.shiftPressed){
            guarding = true;
            guardCounter++;
        }
        else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed)
        {
            if(keyH.upPressed)
            {
                direction="up";
            }
            else if(keyH.downPressed)
            {
                direction="down";

            }
            else if(keyH.leftPressed)
            {
                direction="left";

            }
            else if(keyH.rightPressed)
            {
                direction="right";
            }

            // Verificare coliziune
            collisionOn = false;
            gp.cCheck.checkTile(this);

            //verifica daca este coliziune cu obiectele

            int objIndex = gp.cCheck.checkObject(this,true);
            pickUpObject(objIndex);

            //verifica daca este coliziune cu npc

            int npcIndex = gp.cCheck.checkEntity(this,gp.npc);
            interactNpc(npcIndex);

            //verifica daca este coliziune cu monstrul verde

            int monsterIndex = gp.cCheck.checkEntity(this,gp.monster);
            contactMonster(monsterIndex);



            //verificam coliziunea cu interactive tile
            gp.cCheck.checkEntity(this,gp.iTile);

            //check event

            gp.eHandler.checkEvent();

            //daca nu exista coliziune player-ul se poate misca

            if(!collisionOn && !keyH.enterPressed)
            {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            if(keyH.enterPressed && !attackCanceled){
                attacking = true;
                attackCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;
            guarding = false;

            movementCounter++;
            if(movementCounter > 12)
            {
                if(movementNum==1)
                {
                    movementNum = 2;
                }
                else if(movementNum==2)
                {
                    movementNum = 1;
                }
                movementCounter = 0;
            }

        }
        else
        {
            standCounter++;
            if(standCounter == 20)
            {
                movementNum = 1;
                standCounter = 0;
            }
            guarding = false;
            guardCounter = 0;
        }

        if(gp.keyH.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveResources(this)){

            projectile.set(worldX, worldY , direction, true, this);

            projectile.subtractResource(this);

            for(int i = 0;i < gp.projectile[1].length;i++){

                if(gp.projectile[gp.currentMap][i] == null){

                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;


        }

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter < 30){

            shotAvailableCounter++;
        }

        if(life > maxLife){
            life = maxLife;
        }

        if(life < 0){

            life = 0;
        }

        if(mana > maxMana){
            mana = maxMana;
        }

        if(life <= 0){
            gp.gameState = gameState.OverState;
            gp.ui.commandNum = -1;
        }

        if(dashOn)
        {
            speed = 6;
        }
        if(!dashOn)
        {
            speed = 4;
        }

    }

    public int getCurrentWeaponSlot(){

        int currentWeaponSlot = 0;
        for(int i = 0; i < inventory.size();i++){
            if(inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }

    public int getCurrentShieldSlot(){

        int currentShieldSlot = 0;
        for(int i = 0; i < inventory.size();i++){
            if(inventory.get(i) == currentShield){
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }

    public void contactMonster(int i)
    {
        if(i !=999)
        {
            if(!invincible && !gp.monster[gp.currentMap][i].dying) {
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 0)
                {
                    damage = 0;
                }


                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }

    public void damageMonster(int i,Entity attacker, int attack, int knockBackPower){

        if(i !=999){
            if(!gp.monster[gp.currentMap][i].invincible){

                if(knockBackPower > 0){
                    setKnockBack(gp.monster[gp.currentMap][i],attacker, knockBackPower);
                }

                if(gp.monster[gp.currentMap][i].offBalance){
                    attack *= 2;
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0)
                {
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage+" damage!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if(gp.monster[gp.currentMap][i].life <= 0){

                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("killed the "+gp.monster[gp.currentMap][i].name + "!");
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void checkLevelUp()
    {
        if(exp >= nextLevelExp){

            level++;
            exp = exp - nextLevelExp;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.gameState = gameState.dialogState;
            gp.ui.currentDialogue = "You are level "+level+" now\n" +"You are stronger!";
        }
    }

    public void pickUpObject(int i)
    {
        if(i != 999)
        {

            //pickup only objects
            if(gp.obj[gp.currentMap][i].type == type_pickUponly){

                gp.obj[gp.currentMap][i].use();
                gp.obj[gp.currentMap][i] = null;
            }
            else if(gp.obj[gp.currentMap][i].type == type_frunza){

                gp.gameState = gameState.endState;
            }
            //inventory objects
            else{

                String text;
                if(canObtainItem(gp.obj[gp.currentMap][i])){
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                }
                else
                {
                    text = "You cannot carry anymore";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }


    public void interactNpc(int i)
    {
        if(gp.keyH.enterPressed) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gameState.dialogState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }

    public void damageInteractiveTile(int i){

        if(i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this) && !gp.iTile[gp.currentMap][i].invincible){

            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            //generate particle
            generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);

            if(gp.iTile[gp.currentMap][i].life == 0){
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }

    public void selectItem(){

        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if(itemIndex < inventory.size()){

            OBJ selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe){

                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){

                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable){

                selectedItem.use();
                if(selectedItem.amount > 1){
                    selectedItem.amount--;
                }
                else {
                    inventory.remove(itemIndex);
                }
            }
        }
    }

    public void damageProjectile(int i){

        if(i != 999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public int searchItemInInventory(String itemName){

        int itemIndex = 999;
        for(int i = 0; i < inventory.size();i++){

            if(inventory.get(i).name.equals(itemName)){

                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    /**
     * @param item The canObtainItem() method  checks various conditions and constraints to determine if the player is eligible to obtain or pick up an item in the game
     * @return
     */
    public boolean canObtainItem(OBJ item){

        boolean canObtain = false;

        OBJ newItem = gp.eGenerator.getObject(item.name);

        //check if stackable
        if(newItem.stackable){

            int index = searchItemInInventory(newItem.name);
            if(index != 999){

                inventory.get(index).amount++;
                canObtain = true;
            }
            else{
                //new item
                if(inventory.size() != maxInventorySize){

                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else{
            if(inventory.size() != maxInventorySize){

                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }

    /**
     * @param g2 The draw() method is responsible for drawing the changes on the screen.
     */
    public void draw(Graphics2D g2)
    {
        BufferedImage image=null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" -> {
                if (!attacking) {
                    if (movementNum == 1) {
                        image = up1;
                    }
                    if (movementNum == 2) {
                        image = up2;
                    }
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (attackNum == 1) {
                        image = attackUp1;
                    }
                    if (attackNum == 2) {
                        image = attackUp2;
                    }
                }
                if(guarding){
                    image = guardUp;
                }
            }
            case "down" -> {
                if (!attacking) {
                    if (movementNum == 1) {
                        image = down1;
                    }
                    if (movementNum == 2) {
                        image = down2;
                    }
                }
                if (attacking) {
                    if (attackNum == 1) {
                        image = attackDown1;
                    }
                    if (attackNum == 2) {
                        image = attackDown2;
                    }
                }
                if(guarding){
                    image = guardDown;
                }
            }
            case "left" -> {
                if (!attacking) {
                    if (movementNum == 1) {
                        image = left1;
                    }
                    if (movementNum == 2) {
                        image = left2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (attackNum == 1) {
                        image = attackLeft1;
                    }
                    if (attackNum == 2) {
                        image = attackLeft2;
                    }
                }
                if(guarding){
                    image = guardLeft;
                }
            }
            case "right" -> {
                if (!attacking) {
                    if (movementNum == 1) {
                        image = right1;
                    }
                    if (movementNum == 2) {
                        image = right2;
                    }
                }
                if (attacking) {
                    if (attackNum == 1) {
                        image = attackRight1;
                    }
                    if (attackNum == 2) {
                        image = attackRight2;
                    }
                }
                if(guarding){
                    image = guardRight;
                }
            }
        }

        if(transparent){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
        }

        g2.drawImage(image,tempScreenX,tempScreenY,null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
}
