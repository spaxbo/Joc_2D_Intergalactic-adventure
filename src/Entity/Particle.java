package Entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

/**
 * the particles that jump when the player hits an interactive tile with an axe are implemented
 */
public class Particle extends Entity {

    Entity generator;
    Color color;

    int size;
    int xd;
    int yd;

    int speed;

    int maxLife;

    int life;

    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offset = (gp.tileSize)/2 - (size/2);
        worldX = generator.worldX +offset;
        worldY = generator.worldY + offset;
    }

    /**
     * The update() method is used to modify the particle's state over time, while the draw() method is used to render the particle's progress on the screen.
     */
    public void update(){

        life--;

        if(life < maxLife/3){

            yd++;
            size--;
        }

        worldX += xd * speed;
        worldY += yd * speed;

        if(life == 0){

            alive = false;
        }

    }

    public void draw(Graphics2D g2){

        Player player = Player.getInstance(gp);
        int screenX = worldX - player.worldX + player.screenX;
        int screenY = worldY - player.worldY + player.screenY;

        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);
    }
}
