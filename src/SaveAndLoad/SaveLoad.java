package SaveAndLoad;

import Entity.Player;
import main.GamePanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

/**
 * SaveLoad: This class implements the database used to save the player's life, remaining mana level, and current level.
 */
public class SaveLoad {

    GamePanel gp;

    private int level;

    private int life;

    private int mana;

    public SaveLoad (GamePanel gp){

        Player player = Player.getInstance(gp);
        this.gp = gp;

        this.level = player.level;
        this.life = player.life;
        this.mana = player.mana;
    }

    public int getLevel(){
        return level;
    }

    public int getLife(){
        return life;
    }

    public int getMana(){
        return mana;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public void setLife(int life){
        this.life = life;
    }

    public void setMana(int mana){
        this.mana = mana;
    }

    /**
     * @throws SQLException
     * The save() method saves the data to the database and creates the table within it
     */
    public void save() throws SQLException {

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:joc.db");
            Statement stmt = c.createStatement();
            String sql = "DROP TABLE SAVE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE SAVE (LEVEL INTEGER, LIFE INTEGER, MANA INTEGER)";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO SAVE (LEVEL,LIFE,MANA) VALUES ("+getLevel()+","+getLife()+","+getMana()+")";
            stmt.executeUpdate(sql);
            stmt.close();
        }
        catch (ClassNotFoundException e){
            System.out.println("Sqlite not found");
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        finally {
            if(c != null){
                try{
                    c.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }

    /**
     * the Load() function retrieves the data and utilizes it.
     */
    public void load(){

        SaveLoad ls = new SaveLoad(gp);
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:joc.db");
            stmt = c.createStatement();
            String sql = "SELECT * FROM SAVE";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                ls.setLevel(rs.getInt("LEVEL"));
                ls.setLife(rs.getInt("LIFE"));
                ls.setMana(rs.getInt("MANA"));
            }
            else{
                System.out.println("No save found");
                ls.setLevel(1);
                ls.setLife(6);
                ls.setMana(4);
            }
            rs.close();
            stmt.close();
        }
        catch (ClassNotFoundException e){
            System.out.println("Sqlite not found");
            e.printStackTrace();
            System.exit(1);
        }
        catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        finally {
            if(c != null){
                try {
                    c.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
