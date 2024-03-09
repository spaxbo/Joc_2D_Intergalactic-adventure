package Entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ;
import object.toolType;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static object.toolType.type_monster;

/**
 * This class serves as the base for any game entity. It stores information about the position and size of the entity in the game world, such as the x and y coordinates of the position and the width and height of the entity.
 */
public class Entity {

    GamePanel gp;

    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;

    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2,guardUp,guardDown,guardLeft,guardRight;

    public Rectangle solidArea = new Rectangle(0,0,48,48);

    public Rectangle attackArea = new Rectangle(0,0,0,0);

    public int solidAreaDefaultX, solidAreaDefaultY;

    String[] dialogues = new String[20];

    public ArrayList<OBJ> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Entity attacker;

    //State

    public int worldX,worldY;

    public String direction = "down";

    public int movementNum = 1;

    public int attackNum = 1;

    int dialogueIndex = 0;

    public boolean collisionOn = false;

    public boolean invincible = false;

    public boolean attacking = false;

    public boolean alive = true;

    public boolean dying = false;

    public boolean hpBarOn = false;

    public boolean onPath = false;

    public boolean knockBack = false;

    public boolean transparent = false;


    public boolean guarding = false;

    public boolean offBalance = false;

    public boolean inRage = false;

    public boolean boss;

    //Counter

    public int movementCounter=0;
    public int attackCounter=0;

    public int actionLockCounter = 0;

    public int invincibleCounter = 0;

    public int dyingCounter = 0;

    public int hpBarCounter = 0;

    public int shotAvailableCounter = 0;

    int knockBackCounter = 0;

    public int guardCounter = 0;

    int offBalanceCounter = 0;

    //Character atttributes



    public String name;

    public int maxLife;


    public int life;

    public String knockBackDirection;

    public int maxMana;

    public int defaultSpeed;

    public int mana;

    public int speed;

    public int level;

    public int strength;

    public int dexterity;

    public int motion1_duration;

    public int motion2_duration;

    public int attack;

    public int defense;

    public int exp;

    public int nextLevelExp;

    public int coin;

    public int useCost;

    public OBJ currentWeapon;

    public OBJ currentShield;

    public Projectile projectile;



    public int knockBackPower = 0;


    //type
    public toolType type;

    public Entity(GamePanel gp)
    {
        this.gp = gp;
    }

    public void setAction()
    {

    }

    public void damageReaction(){

    }

    public void speak()
    {
        Player player = Player.getInstance(gp);
        if(dialogues[dialogueIndex] == null)
        {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (player.direction) {
            case "up" -> {
                direction = "down";
            }
            case "down" -> {
                direction = "up";
            }
            case "left" -> {
                direction = "right";
            }
            case "right" -> {
                direction = "left";
            }
        }
    }

    public Color getParticleColor(){

        Color color = null;
        return color;
    }

    public int getParticleSize(){

        int size = 0;//pixels
        return size;
    }

    public int getParticleSpeed(){

        int speed = 0;//pixels
        return speed;
    }

    public int getParticleMaxLife(){

        int maxLife = 0;//pixels
        return maxLife;
    }

    public void generateParticle(Entity generator, Entity target){

        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife,  2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2,  1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife,  2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    /**
     * The checkCollision() method is used to determine if there is a collision between the current entity and another entity or object in the game. It takes into account the position and dimensions of both entities and checks if their bounding boxes overlap.
     */
    public void checkCollision(){

        collisionOn = false;
        gp.cCheck.checkTile(this);
        gp.cCheck.checkObject(this,false);
        gp.cCheck.checkEntity(this,gp.npc);
        gp.cCheck.checkEntity(this,gp.monster);
        gp.cCheck.checkEntity(this,gp.iTile);
        boolean contactPlayer = gp.cCheck.checkPlayer(this);

        if(this.type == type_monster && contactPlayer){

            damagePlayer(attack);
        }
    }

    /**
     * The update() method in the Entity class is used to modify the properties and behavior of the entity during each game frame. It handles various tasks such as updating the entity's position, velocity, animations, health, or other attributes.
     */
    public void update()
    {
        Player player = Player.getInstance(gp);
        if(knockBack){

            checkCollision();
            if(collisionOn){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(!collisionOn){
                switch (knockBackDirection){
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else if(attacking){

            attacking();
        }

        else{
            setAction();

            checkCollision();


            //daca nu exista coliziune player-ul se poate misca

            if(!collisionOn)
            {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            movementCounter++;
            if(movementCounter > 24)
            {
                if(movementNum == 1)
                {
                    movementNum=2;
                }
                else if(movementNum == 2)
                {
                    movementNum=1;
                }
                movementCounter = 0;
            }
        }

        if(invincible)
        {
            invincibleCounter++;
            if(invincibleCounter > 40)
            {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter < 30){

            shotAvailableCounter++;
        }
        if(offBalance){
            offBalanceCounter++;
            if(offBalanceCounter > 60){
                offBalance = false;
                offBalanceCounter = 0;
            }
        }
    }

    /**
     * @param g2 The draw() method in the Entity class is used to display the entity's visual representation on the game screen. It utilizes graphics rendering techniques to draw the entity's sprite or graphical elements at its current position.
     */
    public void draw(Graphics2D g2)
    {
        Player player = Player.getInstance(gp);
        BufferedImage image=null;

        if(inCamera())
        {
            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

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
                        tempScreenY = getScreenY() - up1.getHeight();
                        if (attackNum == 1) {
                            image = attackUp1;
                        }
                        if (attackNum == 2) {
                            image = attackUp2;
                        }
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
                        tempScreenX = getScreenX() - left1.getWidth();
                        if (attackNum == 1) {
                            image = attackLeft1;
                        }
                        if (attackNum == 2) {
                            image = attackLeft2;
                        }
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
                }
            }

            if (invincible) {

                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }
            if(dying){
                dyingAnimation(g2);
            }


            g2.drawImage(image,tempScreenX,tempScreenY,null);

            changeAlpha(g2,1F);
        }
    }

    public boolean inCamera(){

        Player player = Player.getInstance(gp);
        boolean inCamera = false;
        if(worldX + gp.tileSize * 5 > player.worldX - player.screenX && worldX - gp.tileSize < player.worldX + player.screenX && worldY + gp.tileSize * 5 > player.worldY- player.screenY && worldY - gp.tileSize < player.worldY + player.screenY ){
            inCamera = true;
        }
        return inCamera;
    }

    public int getScreenX(){

        Player player = Player.getInstance(gp);
        return worldX - player.worldX + player.screenX;
    }

    public int getScreenY(){

        Player player = Player.getInstance(gp);
        return worldY - player.worldY + player.screenY;
    }

    public int getXDistance(Entity target){

        return Math.abs(getCenterX() - target.getCenterX());
    }
    public int getYDistance(Entity target){

        return Math.abs(getCenterY() - target.getCenterY());
    }

    public int getCenterX(){

        return worldX + up1.getWidth()/2;
    }

    public int getCenterY(){

        return worldY + up1.getHeight()/2;
    }

    public int getTileDistance(Entity target){
        int tileDistance = (getXDistance(target) - getYDistance(target))/gp.tileSize;
        return tileDistance;
    }
    public int getCoalCol(Entity target){
        Player player = Player.getInstance(gp);
        int goalCol = (player.worldX + player.solidArea.x)/gp.tileSize;
        return goalCol;
    }

    public int getCoalRow(Entity target){
        Player player = Player.getInstance(gp);
        int goalRow = (player.worldY + player.solidArea.y)/gp.tileSize;
        return goalRow;
    }

    public void checkDrop(){}

    /**
     * The attacking() method handles the logic and effects of the entity's attack action. It typically involves determining the target of the attack, calculating damage or other effects, and applying them to the target.
     */
    public void attacking()
    {
        Player player = Player.getInstance(gp);
        attackCounter++;

        if(attackCounter <= motion1_duration)
        {
            attackNum = 1;
        }
        if(attackCounter > motion1_duration && attackCounter <= motion2_duration){
            attackNum = 2;

            //save the current worldX,worldY,solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //adjust new position

            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += gp.tileSize;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += gp.tileSize;
            }

            //attack area becomes solid area

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if(type == type_monster){
                if(gp.cCheck.checkPlayer(this)){
                    damagePlayer(attack);
                }
            }
            else {
                //player
                //check monster collision with the updated worldX,worldY,solidArea
                int monsterIndex = gp.cCheck.checkEntity(this,gp.monster);
                player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);

                int iTileIndex = gp.cCheck.checkEntity(this,gp.iTile);
                player.damageInteractiveTile(iTileIndex);

                int projectileIndex = gp.cCheck.checkEntity(this, gp.projectile);
                player.damageProjectile(projectileIndex);

            }

            worldX = currentWorldX;
            worldY = currentWorldY;

            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;


        }
        if(attackCounter > motion2_duration){
            attackNum = 1;
            attackCounter = 0;
            attacking = false;
        }
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal){

        Player player = Player.getInstance(gp);
        boolean targetInRange = false;
        int xDis = getXDistance(player);
        int yDis = getYDistance(player);

        switch (direction) {
            case "up" -> {
                if (player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "down" -> {
                if (player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "left" -> {
                if (player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "right" -> {
                if (player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
            }
        }
        if(targetInRange){
            int i = new Random().nextInt(rate);
            if(i == 0){
                attacking = true;
                attackNum = 1;
                attackCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }

    public void dropItem(OBJ droppedItem){

        for(int i = 0;i < gp.obj[1].length;i++){

            if(gp.obj[gp.currentMap][i] == null){

                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; //worldX dead monster
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    public void damagePlayer(int attack){

        Player player = Player.getInstance(gp);
        if(!player.invincible){
            int damage = attack - player.defense;

            //get an opposite direction

            String canGuardDirection = getOppositeDirection(direction);

            if(player.guarding && player.direction.equals(canGuardDirection)){

                //parry
                if(player.guardCounter < 10){
                    damage = 0;
                }
                else {
                    damage /= 3;
                }
            }
            else{
                //not guarding
                if(damage < 1){

                    damage = 1;
                }
            }

            if(damage !=0){

                player.transparent = true;
            }

            player.life -= damage;
            player.invincible = true;
        }
    }

    public String getOppositeDirection(String direction){

        String oppositeDirection = "";

        switch (direction){

            case "up":oppositeDirection = "down";break;
            case "down":oppositeDirection = "up";break;
            case "left":oppositeDirection = "right";break;
            case "right":oppositeDirection = "left";break;
        }
        return oppositeDirection;
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate){

        if(getTileDistance(target) > distance){

            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = false;
            }
        }
    }

    public void checkStartChasingOrNot(Entity target, int distance, int rate){

        if(getTileDistance(target) < distance){

            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = true;
            }
        }
    }

    public void getRandomDirection(){

        actionLockCounter++;

        if(actionLockCounter > 120)
        {
            Random random = new Random();
            int i = random.nextInt(100)+1; //alege un numar de la 1 la 100
            if(i <= 25)
            {
                direction = "up";
            }
            if(i >= 25 && i <= 50)
            {
                direction = "down";
            }
            if(i > 50 && i <= 75)
            {
                direction = "left";
            }
            if(i > 75 && i < 100)
            {
                direction = "right";
            }

            actionLockCounter = 0;

        }
    }

    public void checkShootOrNot(int rate, int shotInterval){

        int i = new Random().nextInt(rate);
        if(i == 0 && !projectile.alive && shotAvailableCounter == shotInterval){

            projectile.set(worldX, worldY ,direction , true, this);
            for(int j = 0; j < gp.projectile[1].length;j++){
                if(gp.projectile[gp.currentMap][j] == null){
                    gp.projectile[gp.currentMap][j] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }

    public void setKnockBack(Entity target,Entity attacker, int knockBackPower){

        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }
    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i) {changeAlpha(g2,0f);}
        if(dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2,1f);}
        if(dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2,0f);}
        if(dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2,1f);}
        if(dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2,0f);}
        if(dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2,1f);}
        if(dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2,0f);}
        if(dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2,1f);}
        if(dyingCounter > i*8)
        {
            alive = false;
        }
    }

    /**
     * @param interval The moveTowardPlayer() method calculates the necessary movements or pathfinding to make another entity or NPC move towards the current position of the player. It uses algorithms or techniques such as pathfinding (e.g., A* algorithm) to determine the most optimal path or route for the entity to reach the player.
     */
    public void moveTowardPlayer(int interval){

        Player player = Player.getInstance(gp);
        actionLockCounter++;
        if(actionLockCounter > interval){

            if(getXDistance(player) > getYDistance(player)){
                if(player.getCenterX() < getCenterX()){
                    direction = "left";
                }
                else{
                    direction = "right";
                }
            }
            else if(getXDistance(player) < getYDistance(player)){
                if(player.getCenterY() < getCenterY()){
                    direction = "up";
                }
                else{
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue)
    {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public BufferedImage setup(String imagePath, int width, int height)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try
        {
            image = ImageIO.read(new FileInputStream(imagePath +".png"));
            image = uTool.scaleImage(image,width,height);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow){

        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search()){

            //next worldx,worldy
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){

                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){

                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){

                //left or right
                if( enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX){

                //up or left
                direction = "up";
                checkCollision();
                if(collisionOn){

                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){

                //up or right
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){

                //down or left
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){

                //down or right
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }

            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow){

                onPath = false;
            }

        }
    }
}
