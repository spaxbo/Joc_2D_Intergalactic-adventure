package main;

import javax.swing.JFrame;
import java.awt.*;
import java.io.IOException;

/**
 * The Main class creates an instance of the GamePanel class and initiates the flow of activities by calling the StartGameThread() method in GamePanel
 */
public class Main {

    public static JFrame window;
    public static void main(String[] args) throws IOException, FontFormatException {
        window= new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Intergalactic adventure");

        GamePanel gamePanel= new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
