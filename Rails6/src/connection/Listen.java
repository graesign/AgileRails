/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import static java.lang.Thread.sleep;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Listen extends Thread {
    
    public static Listen thread;
    
    public static String railsStart;
    public static String railsEnd;
    
    // Integer to normalize start and end rails.?
    public static int normalize = 0;
    
    private final AddCab addCab;
    
    public Listen(AddCab addCab) {
        this.addCab = addCab;
    }
    
    public static void listen(String rails, AddCab addCab) {
        Config.load();
        railsStart = Config.get(rails + "start");
        railsEnd = Config.get(rails + "end");
        
        // Store a normalization integer to calculate back the original
        // station numbers.
        int rails6end = Integer.parseInt(Config.get("rails6end"));
        if (Integer.parseInt(railsStart) > rails6end ) {
            normalize = -rails6end;
        }
        
        thread = new Listen(addCab);
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
    
    public static void addCab(int aantal, int eindHalte) {
        synchronized (Listen.thread) {
            Listen.thread.saveCab(aantal, eindHalte);
        }
    }
        
    public void saveCab(int aantal, int eindHalte) {
        System.out.println("Adding cab in database, aantal: " + aantal + " eindhalte: " + eindHalte);
        Queries.setCab(9, eindHalte, aantal);
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

            synchronized (addCab) {
                addCab.add(start, end, passengers);
            }
            Queries.deleteCab(cabs.getString("id"));
        }
    }
}