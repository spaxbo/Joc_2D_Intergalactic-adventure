package main;

import Entity.Entity;
import object.OBJ;
import object.Obj_Coin;
import object.Obj_Heart;
import object.Obj_Mana;
import Entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * The UI class manages user interaction in a detailed manner.
 */
public class UI {
    GamePanel gp;

    Graphics2D g2;
    Font maruMonica;

    BufferedImage heart_full,heart_half,heart_blank,mana_full,mana_blank,coin;

    ArrayList<String> message = new ArrayList<>();

    ArrayList<Integer> messageCounter = new ArrayList<>();

    public String currentDialogue = "";

    public int commandNum = 0;

    public int playerSlotCol = 0;

    public int playerSlotRow = 0;

    public int npcSlotCol = 0;

    public int npcSlotRow = 0;

    int subState = 0;

    int counter = 0;

    public Entity npc;
    public UI(GamePanel gp) throws IOException, FontFormatException {

        this.gp = gp;

        try
        {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT,is);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (FontFormatException e)
        {
            e.printStackTrace();
        }

        //Create hud object

        OBJ heart = new Obj_Heart(gp);
        heart_full = heart.image1;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        OBJ crystal = new Obj_Mana(gp);
        mana_full = crystal.image1;
        mana_blank = crystal.image2;
        OBJ   ccoin = new Obj_Coin(gp);
        coin = ccoin.down1;
    }

    public void addMessage(String text)
    {
        message.add(text);
        messageCounter.add(0);
    }

    /**
     * @param g2 The draw() method determines the game state and handles what needs to be displayed on the screen for each state
     */
    public void draw(Graphics2D g2)
    {
        this.g2 = g2;

        Player player = Player.getInstance(gp);
        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.cyan);

        //Title state

        if(gp.gameState == gameState.titleState)
        {
            drawTitleScreen();
        }

        //Play state
        if(gp.gameState == gameState.playState)
        {
            drawPlayerLife();
            drawMonsterLife();
            drawMessage();
        }
        //Pause state
        if(gp.gameState == gameState.pauseState)
        {
            drawPlayerLife();
            drawPauseScreen();
        }
        //Dialogue state
        if(gp.gameState == gameState.dialogState)
        {
            drawDialogueScreen();
        }

        //character screen

        if(gp.gameState == gameState.characterState){

            drawCharacterScreen();
            drawInventory(player, true);
        }

        //options screen

        if(gp.gameState == gameState.optionState){

            drawOptionsScreen();
        }

        //game over state

        if(gp.gameState == gameState.OverState){

            drawGameOverScreen();
        }

        //transition state
        if(gp.gameState == gameState.transitionState){

            drawTransition();
        }

        //trade screen
        if(gp.gameState == gameState.tradeState){

            drawTradeScreen();
        }

        //end game state
        if(gp.gameState == gameState.endState){

            finishGame();
        }

    }

    public void drawPlayerLife()
    {
        //gp.player.life = 6;
        Player player = Player.getInstance(gp);
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //Draw blank heart
        while(i < player.maxLife/2)
        {
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x += gp.tileSize;
        }

        //_Reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //Draw current life
        while(i < player.life)
        {
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i < player.life)
            {
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x += gp.tileSize;
        }

        //draw mana

        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while(i < player.maxMana){

            g2.drawImage(mana_blank, x, y, null);
            i++;
            x += 35;
        }

        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while(i < player.mana){

            g2.drawImage(mana_full, x, y, null);
            i++;
            x += 35;
        }


    }

    public void drawMessage()
    {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));

        for(int i = 0;i < message.size();i++)
        {
            if(message.get(i) != null)
            {
                g2.setColor(Color.black);
                g2.drawString(message.get(i),messageX + 2,messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i),messageX,messageY);

                int counter = messageCounter.get(i) + 1; //messageCounter++
                messageCounter.set(i,counter);
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }

    }

    public void drawInventory(Entity entity, boolean cursor){

        Player player = Player.getInstance(gp);
        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotRow;
        int slotCol;

        if(entity == player){

            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }


        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //slot

        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        //draw player items

        for(int i = 0;i < entity.inventory.size();i++){

            if(entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield){

                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY ,null);

            if(entity == player && entity.inventory.get(i).amount > 1){

                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXForAligntoRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;

                //shadow

                g2.setColor(new Color(60,60,60));
                g2.drawString(s, amountX, amountY);

                //number
                g2.setColor(Color.white);
                g2.drawString(s, amountX -3, amountY - 3);
            }

            slotX += slotSize;

            if(i % 5 == 4 && i < 19){

                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //cursor
        if(cursor){
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            //draw cursor
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            //description frame

            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;

            //draw description text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if( itemIndex < entity.inventory.size())
            {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for(String line: entity.inventory.get(itemIndex).description.split("\n")){

                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }

        }

    }

    public int getItemIndexOnSlot(int slotCol, int slotRow){

        int index = slotCol + (slotRow * 5);
        return index;
    }

    public void drawTitleScreen()
    {
        Player player = Player.getInstance(gp);
        g2.setColor(new Color(51,153,255));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        //Title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
        String text = "Intergalactic adventure";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;

        //Shadow
        g2.setColor(Color.gray);
        g2.drawString(text,x+5,y+5);

        //Main color
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //Image

        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(player.down1,x,y,gp.tileSize*2,gp.tileSize*2,null);

        //Menu

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text,x,y);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "LOAD GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 1)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "QUIT";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 2)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }

    }

    public void drawPauseScreen()
    {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));

        String text = "PAUSED";


        int x = getXForCenteredText(text);

        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }

    public void drawDialogueScreen()
    {
        //window
        int x = gp.tileSize * 3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize *4;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line:currentDialogue.split("\n"))
        {
            g2.drawString(line,x,y);
            y += 40;
        }
    }

    public void drawSubWindow(int x,int y,int width,int height)
    {
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);

    }

    public void drawCharacterScreen(){

        Player player = Player.getInstance(gp);
        //create a frame

        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //text

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY +gp.tileSize;
        final int lineHeight = 35;

        //names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        //values
        int tailX = (frameX + frameWidth) - 30;
        //reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(player.level);
        textX = getXForAligntoRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(player.life + "/" + player.maxLife);
        textX = getXForAligntoRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(player.mana + "/" + player.maxMana);
        textX = getXForAligntoRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(player.strength);
        textX = getXForAligntoRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(player.dexterity);
        textX = getXForAligntoRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(player.attack);
        textX = getXForAligntoRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(player.defense);
        textX = getXForAligntoRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(player.exp);
        textX = getXForAligntoRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(player.nextLevelExp);
        textX = getXForAligntoRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(player.coin);
        textX = getXForAligntoRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;

        g2.drawImage(player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);
    }

    public void drawGameOverScreen(){

        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));

        text = "Game Over";
        //Shadow
        g2.setColor(Color.black);
        x = getXForCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        //Main
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";

        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-40, y);
        }

        //Back to title screen

        text = "Quit";
        x = getXForCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-40, y);
        }
    }

    public void drawOptionsScreen(){

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        //sub window
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 12;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0 -> {
                options_top(frameX, frameY);
            }
            case 1 -> {
                options_control(frameX, frameY);
            }
            case 2 -> {
                options_endGame(frameX, frameY);
            }
        }
        gp.keyH.enterPressed = false;
    }

    public void options_top(int frameX, int frameY){

        int textX;
        int textY;

        //title
        String text = "Options";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //music
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Music", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
        }

        //se
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
        }

        //controls
        textY += gp.tileSize;
        g2.drawString("Controls", textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 1;
                commandNum = 0;
            }
        }

        //end game
        textY += gp.tileSize;
        g2.drawString("End game", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 2;
                commandNum = 0;
            }
        }

        //back
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                gp.gameState = gameState.playState;
                commandNum = 0;
            }
        }

        //music volume
        textX = frameX + (int)(gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //se volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
    }

    public void options_control(int frameX, int frameY){

        int textX;
        int textY;

        //title
        String text = "Controls";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Confirm/attack", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Character screen", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Options", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Speed up", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Guard", textX, textY);
        textY += gp.tileSize;

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize;
        g2.drawString("F", textX, textY);
        textY += gp.tileSize;
        g2.drawString("C", textX, textY);
        textY += gp.tileSize;
        g2.drawString("P", textX, textY);
        textY += gp.tileSize;
        g2.drawString("M", textX, textY);
        textY += gp.tileSize;
        g2.drawString("SPACE", textX, textY);
        textY += gp.tileSize;
        g2.drawString("SHIFT", textX, textY);
        textY += gp.tileSize;

        //back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 11;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){

            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 2;
            }
        }

    }

    public void options_endGame(int frameX, int frameY){

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";

        for(String line:currentDialogue.split("\n")){

            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //yes

        String text = "Yes";
        textX = getXForCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){

            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){

                subState = 0;
                gp.gameState = gameState.titleState;
            }
        }

        //no
        text = "No";
        textX = getXForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){

                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void drawTransition(){

        Player player = Player.getInstance(gp);
        counter++;
        g2.setColor(new Color(0,0,0,counter * 5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        if(counter == 50){

            counter = 0;
            gp.gameState = gameState.playState;
            gp.currentMap = gp.eHandler.tempMap;
            player.worldX = gp.eHandler.tempcol * gp.tileSize;
            player.worldY = gp.eHandler.tempRow * gp.tileSize;
            gp.eHandler.previousEventX = player.worldX;
            gp.eHandler.previousEventY = player.worldY;
        }

    }

    public void drawTradeScreen(){

        switch (subState) {
            case 0 -> trade_select();
            case 1 -> trade_buy();
            case 2 -> trade_sell();
        }
        gp.keyH.enterPressed = false;
    }

    public void trade_select(){

        drawDialogueScreen();

        //draw window
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        //draw texts
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed){

                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNum == 1){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed){

                subState = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if(commandNum == 2){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed){

                commandNum = 0;
                gp.gameState = gameState.dialogState;
                currentDialogue = "Good bye and safe travels";
            }
        }
        y += gp.tileSize;
    }

    public void trade_buy(){

        Player player = Player.getInstance(gp);
        //draw player inventory
        drawInventory(player, false);

        //draw npc inventory
        drawInventory(npc, true);

        //draw hint window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //draw player coin window
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your coins: "+player.coin, x+24, y+60);

        //draw price window

        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){

            x = (int)(gp.tileSize * 5.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+10, 32, 32,null);

            int price = npc.inventory.get(itemIndex).price;
            String text = ""+price;
            x = getXForAligntoRightText(text, gp.tileSize * 8 -20);
            g2.drawString(text, x, y+32);

            //buy an item
            if(gp.keyH.enterPressed){

                if(npc.inventory.get(itemIndex).price > player.coin){

                    subState = 0;
                    gp.gameState = gameState.dialogState;
                    currentDialogue = "You need more coins to buy that";
                    drawDialogueScreen();
                }
                else{
                    if(player.canObtainItem(npc.inventory.get(itemIndex))){
                        player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else{
                        subState = 0;
                        gp.gameState = gameState.dialogState;
                        currentDialogue = "You cannot carry any more";
                    }
                }
            }
        }
    }

    public void trade_sell(){

        Player player = Player.getInstance(gp);
        //draw player inventory
        drawInventory(player, true);

        int x;
        int y;
        int width;
        int height;

        //draw hint window
        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //draw player coin window
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your coins: "+player.coin, x+24, y+60);

        //draw price window

        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < player.inventory.size()){

            x = (int)(gp.tileSize * 15.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+10, 32, 32,null);

            int price = player.inventory.get(itemIndex).price/2;
            String text = ""+price;
            x = getXForAligntoRightText(text, gp.tileSize * 18 -20);
            g2.drawString(text, x, y+32);

            //sell an item
            if(gp.keyH.enterPressed) {

                if(player.inventory.get(itemIndex) == player.currentWeapon|| player.inventory.get(itemIndex) == player.currentShield){

                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gameState.dialogState;
                    currentDialogue = "You cannot sell an equipped item";
                }
                else{
                    if(player.inventory.get(itemIndex).amount > 1){
                        player.inventory.get(itemIndex).amount--;
                    }
                    else{
                        player.inventory.remove(itemIndex);
                    }
                    player.coin += price;
                }
            }
        }
    }

    public void drawMonsterLife(){


        for(int i = 0;i < gp.monster[1].length;i++){

            if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].inCamera()){
                if(gp.monster[gp.currentMap][i].hpBarOn && !gp.monster[gp.currentMap][i].boss){

                    double oneScale = (double)gp.tileSize/gp.monster[gp.currentMap][i].maxLife;
                    double hpBarValue = oneScale*gp.monster[gp.currentMap][i].life;

                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(gp.monster[gp.currentMap][i].getScreenX()-1, gp.monster[gp.currentMap][i].getScreenY()-16, gp.tileSize+2, 12);

                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(gp.monster[gp.currentMap][i].getScreenX(), gp.monster[gp.currentMap][i].getScreenY()-15, (int)hpBarValue, 10);

                    gp.monster[gp.currentMap][i].hpBarCounter++;

                    if(gp.monster[gp.currentMap][i].hpBarCounter > 600){

                        gp.monster[gp.currentMap][i].hpBarCounter = 0;
                        gp.monster[gp.currentMap][i].hpBarOn = false;
                    }
                }
                else if(gp.monster[gp.currentMap][i].boss){

                    double oneScale = (double)gp.tileSize * 8/gp.monster[gp.currentMap][i].maxLife;
                    double hpBarValue = oneScale*gp.monster[gp.currentMap][i].life;

                    int x = gp.screenWidth/2 - gp.tileSize * 4;
                    int y = gp.tileSize * 10;

                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(x - 1, y - 1, gp.tileSize * 8 + 2, 22);

                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(x, y , (int)hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD,24f));
                    g2.setColor(Color.white);
                    g2.drawString(gp.monster[gp.currentMap][i].name, x+4, y-10);
                }
        }

        }

    }

    public void finishGame(){

        Player player = Player.getInstance(gp);
        counter++;
        g2.setColor(new Color(0,0,0,counter * 5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        if(counter == 50){
            counter = 0;
            gp.gameState = gameState.titleState;
            player.setDefaultValues();
            player.setItems();
        }
    }
    public int getXForCenteredText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        int x = gp.screenWidth/2 - length/2;

        return x;
    }

    public int getXForAligntoRightText(String text, int tailX)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        int x = tailX -length;

        return x;
    }

}
