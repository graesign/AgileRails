/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

/**
 *
 * @author Administrator
 */
public class Database {
    
    public static void connect() {
        Dbmanager.connect();
    }
    
    /**
     * Execute query to set a cab in the database
     * @param people
     * @param start
     * @param end 
     */
    public static void setCab(int people, int start, int end) {
        
    }
    
    /**
     * Get new people from the database
     * TODO: Delete the pulled cabs from Db.
     */
    public static void getCabs() {
        
    }
}
