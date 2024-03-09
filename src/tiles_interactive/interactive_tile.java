package tiles_interactive;

import Entity.Entity;
import main.GamePanel;
import Entity.Player;
import main.KeyHandler;

import java.awt.*;

/**
 * The InteractiveTile class implements tiles that can be cut by the player with an axe and replaced.
 */
public class interactive_tile extends Entity {

    GamePanel gp;
    public boolean destructible = false;

    public interactive_tile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    /**
     * @return The getDestroyedForm() method helps find the destroyed form of the tiles
     */
    public interactive_tile getDestroyedForm(){

        interactive_tile tile = null;
        return tile;
    }

    public boolean isCorrectItem(Entity entity){

        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    public void update(){

        if(invincible == true){

            invincibleCounter++;
            if(invincibleCounter > 20){

                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){

        Player player = Player.getInstance(gp);
        int screenX = worldX - player.worldX + player.screenX;

        int screenY = worldY - player.worldY + player.screenY;

        if(worldX + gp.tileSize > player.worldX - player.screenX && worldX - gp.tileSize < player.worldX + player.screenX && worldY + gp.tileSize > player.worldY- player.screenY && worldY - gp.tileSize < player.worldY + player.screenY )
        {
            g2.drawImage(down1, screenX, screenY, null);
        }
    }
}
