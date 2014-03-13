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
public class Listen extends Thread {
    private static Listen thread;
    
    public static String railsStart;
    public static String railsEnd;
    
    public static void listen(String rails) {
        Config.load();
        railsStart = Config.get(rails + "start");
        railsEnd = Config.get(rails + "end");
        thread = new Listen();
        thread.start();
    }
    
    public void run() {
        Queries.connect();
        System.out.println("Listening to database for incoming passengers.");
        while (true) {
            try {
                sleep(1000);
                this.setCabs(Queries.getCabs());
            } catch (Exception ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Set cabs from a resultset in the application.
     * Delete cabs from DB straight after.
     * @param cabs
     * @throws SQLException 
     */
    public void setCabs(ResultSet cabs) throws SQLException {
        Queries.deleteCabs(cabs);
        while (cabs.next()) {
            int start = cabs.getInt("start");
            int end = cabs.getInt("end");
            int passengers = cabs.getInt("passengers");
            
            // Insert cabs here!
            AddCab.add(start, end, passengers);
        }
    }
}
