/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import railcab.Main;

/**
 *
 * @author Administrator
 */
public class Listen extends Thread {
    
    private static Listen thread;
    
    public static String railsStart;
    public static String railsEnd;
    
    // Integer to normalize start and end rails.?
    public static int normalize = 0;
    
    private Main main;
    
    public Listen(Main main) {
        this.main = main;
    }
    
    public static void listen(String rails, Main main) {
        System.out.println("LISTENNN");
        Config.load();
        railsStart = Config.get(rails + "start");
        railsEnd = Config.get(rails + "end");
        
        // Store a normalization integer to calculate back the original
        // station numbers.
        int rails6end = Integer.parseInt(Config.get("rails6end"));
        if (Integer.parseInt(railsStart) > rails6end ) {
            normalize = -rails6end;
        }
        
        thread = new Listen(main);
        thread.start();
    }
    
    @Override
    public void run() {
        Queries.connect();
        System.out.println("Listening to database for incoming passengers.");
        
        try {
            // Sleep a second before starting to listening to get other tasks to finish.
            sleep(1000);
            while (true) {
                sleep(1000);
                this.setCabs(Queries.getCabs());
            }
        } catch (Exception ex) {
            Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Set cabs from a resultset in the application.
     * Delete cabs from DB straight after.
     * @param cabs
     * @throws SQLException 
     */
    public void setCabs(ResultSet cabs) throws SQLException {
        while (cabs.next()) {
            int start = cabs.getInt("start");
            int end = cabs.getInt("end");
            int passengers = cabs.getInt("passengers");

            synchronized (main) {
                main.maakReiziger(start + normalize, end + normalize, passengers);
            }
            Queries.deleteCab(cabs.getString("id"));
        }
    }
}