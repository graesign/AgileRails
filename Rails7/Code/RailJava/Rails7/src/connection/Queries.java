/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Queries {
    
    public static void connect() {
        Dbmanager.connect();
    }
    
    /**
     * Execute query to set a cab in the database
     * @param people
     * @param start
     * @param end 
     */
    public static void setCab(int start, int end, int people) {
        Dbmanager.insertQuery("INSERT INTO `rails` (`start`, `end`, `passengers`)"
                + "VALUES (" + start + ", " + end + ", " + people + ");");
    }
    
    /**
     * Get new people from the database
     * TODO: Delete the pulled cabs from Db.
     * TODO: Alleen mensen die bij deze rails starten.
     * @return 
     */
    public static ResultSet getCabs() {
        ResultSet rs = Dbmanager.doQuery("SELECT * FROM `rails` WHERE `start` >= " + Listen.railsStart + " AND `start` <= " + Listen.railsEnd + ";");

        return rs;
    }
    
    public static void deleteCab(String id) {
        Dbmanager.insertQuery("DELETE FROM `rails` WHERE id = " + id + ";");
    }
}
