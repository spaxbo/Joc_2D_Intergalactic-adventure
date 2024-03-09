package object;

import Entity.Player;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is crucial for the functioning of objects in the game
 */
public class OBJ {

    GamePanel gp;

    public BufferedImage down1;

    public BufferedImage image1,image2,image3;

    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);

    public boolean collision = false;

    public int solidAreaDefaultX, solidAreaDefaultY;

    public String name;

    public int value;

    public toolType type;

    public int attackValue;

    public int defenseValue;

    public String description = "";

    public int worldX,worldY;

    public int life;

    public int motion1_duration;

    public int motion2_duration;

    public int price;

    public int knockBackPower = 0;

    public boolean stackable = false;

    public int amount = 1;

    public OBJ(GamePanel gp){

        this.gp = gp;
    }

    /**
     * The use() method determines if an object can be used by the player based on certain conditions or criteria.
     */
    public void use(){}

    /**
     * @param g2 The draw() method handles the display of object sprites on the screen and applies all necessary updates.
     */
    public void draw(Graphics2D g2){

        Player player = Player.getInstance(gp);
        BufferedImage image = down1;
        int screenX = worldX - player.worldX + player.screenX;;
        int screenY = worldY - player.worldY + player.screenY;
        g2.drawImage(image, screenX, screenY, null);
    }

    /**
     * @param imagePath The setup() method is responsible for loading object sprites from the game's resources and preparing them for use in the game.
     * @param width
     * @param height
     * @return
     */
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
}
