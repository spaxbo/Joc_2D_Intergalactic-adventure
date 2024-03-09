package Entity;

import main.GamePanel;
import main.KeyHandler;

/**
 * This class implements the projectile that the player can shoot.
 */
public class Projectile extends Entity{

    Entity user;
    public Projectile(GamePanel gp) {
        super(gp);
    }

    /**
     * @param worldX The set() method is used to set the position and other properties of the projectile in the game space.
     * @param worldY
     * @param direction
     * @param alive
     * @param user
     */
    public void set(int worldX, int worldY, String direction, boolean alive ,Entity user){

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    /**
     * The update() method is used to update the state of the projectile during its movement on the screen.
     */
    public void update(){

        Player player = Player.getInstance(gp);
        if(user == player){

            int monsterIndex = gp.cCheck.checkEntity(this,gp.monster);
            if(monsterIndex != 999){

                player.damageMonster(monsterIndex, this,attack * (player.level), knockBackPower);
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;
            }
        }
        if(user != player){

            boolean contactPlayer = gp.cCheck.checkPlayer(this);
            if(!player.invincible && contactPlayer){

                damagePlayer(attack);
                generateParticle(user.projectile, user.projectile);
                alive = false;
            }
        }

        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }

        life--;
        if(life <= 0){

            alive = false;
        }

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

    public boolean haveResources(Entity user){

        boolean haveResource = false;
        return haveResource;
    }

    public void subtractResource(Entity user){}
}
