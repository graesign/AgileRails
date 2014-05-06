/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import static connection.Listen.normalize;
import javax.swing.JFrame;
//import railcab.Main;

/**
 *
 * @author Administrator
 */
public class AddCab {
    
    public JFrame main;
    
    public AddCab(JFrame main) {
        this.main = main;
    }

    public void add(int start, int end, int passengers) {
        System.out.println("Adding cabs" + start + " " + end + " " + passengers);
        //Main.GUI.setPassengers(start + normalize, end + normalize, passengers);
    }
    
    public void save(int start, int end, int passengers) {
        
    }
}