package main;

import Entity.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 The KeyHandler class implements the user interaction with the game. It implements the KeyListener interface, which includes the three main methods: keyPressed(), keyTyped(), and keyReleased()
 */
public class KeyHandler implements KeyListener {

    public boolean upPressed,downPressed,leftPressed,rightPressed,enterPressed, shotKeyPressed, shiftPressed;

    //DEBUG

    boolean  showDebugText = false;
    GamePanel gp;

    public KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * The keyPressed() method checks the current game state (gamestate) and calls one of the methods: titleState(), playState(), pauseState(), dialogState(), characterState(), optionState(), and exitState()
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();

        //Title state

        if(gp.gameState == gameState.titleState) {

            titleState(code);

        }


        //Play state
        else if(gp.gameState == gameState.playState)
        {
            playState(code);
        }

        //Pause state

        else if(gp.gameState == gameState.pauseState)
        {
            pauseState(code);
        }

        //Dialogue state
        else if(gp.gameState == gameState.dialogState)
        {
            dialogState(code);
        }

        //character state
        else if(gp.gameState == gameState.characterState){

            characterState(code);
        }

        //option state

        else if(gp.gameState == gameState.optionState){

            optionState(code);
        }

        //game over state

        else if(gp.gameState == gameState.OverState){

            gameOverState(code);
        }

        //trade state

        else if(gp.gameState == gameState.tradeState){

            tradeState(code);
        }

        //DEBUG
        if( code == KeyEvent.VK_T)
        {
           showDebugText = ! showDebugText;
        }
    }

    public void titleState(int code){

        if( code == KeyEvent.VK_W)
        {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0)
            {
                gp.ui.commandNum = 2;
            }
        }
        if( code == KeyEvent.VK_S)
        {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 2)
            {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER)
        {
            if(gp.ui.commandNum == 0)
            {
                gp.gameState = gameState.playState;
                gp.stopMusic();
                gp.playMusic(0);
            }
            if(gp.ui.commandNum == 1)
            {
                gp.saveLoad.load();
                gp.saveOthers.load2();
                gp.gameState = gameState.playState;
                gp.playMusic(0);
            }
            if(gp.ui.commandNum == 2)
            {
                System.exit(0);
            }
        }
        if( code == KeyEvent.VK_ESCAPE)
        {
            gp.gameState = gameState.exitState;
        }
    }
    public void playState(int code){

        Player player = Player.getInstance(gp);
        if( code == KeyEvent.VK_W)
        {
            upPressed=true;
        }
        if( code == KeyEvent.VK_S)
        {
            downPressed=true;
        }
        if( code == KeyEvent.VK_A)
        {
            leftPressed=true;
        }
        if( code == KeyEvent.VK_D)
        {
            rightPressed=true;
        }
        if( code == KeyEvent.VK_SPACE)
        {
            player.dashOn = true;
        }
        if( code == KeyEvent.VK_P)
        {
            gp.gameState = gameState.pauseState;
            gp.stopMusic();
        }
        if( code == KeyEvent.VK_C)
        {
            gp.gameState = gameState.characterState;
        }
        if( code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
        if( code == KeyEvent.VK_F)
        {
            shotKeyPressed = true;
        }
        if( code == KeyEvent.VK_M)
        {
            gp.gameState = gameState.optionState;
        }
        if( code == KeyEvent.VK_SHIFT)
        {
            shiftPressed = true;
        }
        if( code == KeyEvent.VK_ESCAPE)
        {
            gp.gameState = gameState.exitState;
        }
    }
    public void pauseState(int code){

        if( code == KeyEvent.VK_P)
        {
            gp.gameState = gameState.playState;
            gp.playMusic(0);
        }
        if( code == KeyEvent.VK_ESCAPE)
        {
            gp.gameState = gameState.exitState;
        }
    }
    public void dialogState(int code){

        if(code == KeyEvent.VK_ENTER)
        {
            gp.gameState = gameState.playState;
        }
        if( code == KeyEvent.VK_ESCAPE)
        {
            gp.gameState = gameState.exitState;
        }

    }
    public void characterState(int code){

        Player player = Player.getInstance(gp);
        if(code == KeyEvent.VK_C)
        {
            gp.gameState = gameState.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            player.selectItem();
        }

       playerInventory(code);
    }

    public void optionState(int code){

        if(code == KeyEvent.VK_M){

            gp.gameState = gameState.playState;
        }

        if(code == KeyEvent.VK_ENTER){

            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0 -> {
                maxCommandNum = 5;
            }
            case 2 -> {
                maxCommandNum = 1;
            }
        }

        if(code == KeyEvent.VK_W){

            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){

                gp.ui.commandNum = maxCommandNum;
            }
        }

        if(code == KeyEvent.VK_S){

           gp.ui.commandNum++;
            if(gp.ui.commandNum > maxCommandNum){

                gp.ui.commandNum = 0;
            }
        }

        if(code == KeyEvent.VK_A){

            if(gp.ui.subState == 0){

                if(gp.ui.commandNum == 0 && gp.music.volumeScale > 0){

                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }

                if(gp.ui.commandNum == 1 && gp.se.volumeScale > 0){

                    gp.se.volumeScale--;
                }
            }

        }

        if(code == KeyEvent.VK_D){

            if(gp.ui.subState == 0){

                if(gp.ui.commandNum == 0 && gp.music.volumeScale < 5){

                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }

                if(gp.ui.commandNum == 1 && gp.se.volumeScale < 5){

                    gp.se.volumeScale++;
                }
            }
        }

        if(gp.gameState == main.gameState.exitState)
        {
            gp.gameState = main.gameState.titleState;
        }
    }

    public void gameOverState(int code){

        if( code == KeyEvent.VK_W)
        {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {

                gp.ui.commandNum = 1;
            }

        }

        if( code == KeyEvent.VK_S)
        {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1) {

                gp.ui.commandNum = 0;
            }

        }

        if(code == KeyEvent.VK_ENTER){

            if(gp.ui.commandNum == 0){

                gp.gameState = gameState.playState;
                gp.retry();
            }

            else if(gp.ui.commandNum == 1){

                gp.gameState = gameState.titleState;
                gp.restart();
            }
        }
    }

    public void tradeState(int code){

        if(code == KeyEvent.VK_ENTER){

            enterPressed = true;
        }

        if(gp.ui.subState == 0){

            if(code == KeyEvent.VK_W){

                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }

            if(code == KeyEvent.VK_S){

                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
        }
        if(gp.ui.subState == 1){

            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE){

                gp.ui.subState = 0;
            }
        }
        if(gp.ui.subState == 2){

            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){

                gp.ui.subState = 0;
            }
        }
    }

    public void playerInventory(int code){

        if(code == KeyEvent.VK_W){
            if(gp.ui.playerSlotRow!= 0){
                gp.ui.playerSlotRow--;
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.playerSlotCol != 0){
                gp.ui.playerSlotCol--;
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.playerSlotRow != 3){
                gp.ui.playerSlotRow++;
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.playerSlotCol != 4){
                gp.ui.playerSlotCol++;
            }
        }
    }

    public void npcInventory(int code){

        if(code == KeyEvent.VK_W){
            if(gp.ui.npcSlotRow!= 0){
                gp.ui.npcSlotRow--;
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.npcSlotCol != 0){
                gp.ui.npcSlotCol--;
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.npcSlotRow != 3){
                gp.ui.npcSlotRow++;
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.npcSlotCol != 4){
                gp.ui.npcSlotCol++;
            }
        }
    }

    /**
     * @param e the event to be processed The keyReleased() method checks when the user releases a key and stops the player's movement action. This class also handles keyboard-related events such as key presses or key releases and takes appropriate actions based on the current game state and its requirements.
     */
    @Override
    public void keyReleased(KeyEvent e) {

        int code=e.getKeyCode();

        Player player = Player.getInstance(gp);

        if( code == KeyEvent.VK_W)
        {
            upPressed=false;
        }
        if( code == KeyEvent.VK_S)
        {
            downPressed=false;
        }
        if( code == KeyEvent.VK_A)
        {
            leftPressed=false;
        }
        if( code == KeyEvent.VK_D)
        {
            rightPressed=false;
        }
        if( code == KeyEvent.VK_D)
        {
            player.dashOn = false;
        }
        if( code == KeyEvent.VK_F)
        {
            shotKeyPressed = false;
        }
        if( code == KeyEvent.VK_ENTER)
        {
            enterPressed = false;
        }
        if( code == KeyEvent.VK_SHIFT)
        {
            shiftPressed = false;
        }
    }
}

