/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import static connection.Listen.normalize;
import javax.swing.JFrame;
import main.ReisBeheer;
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
        System.out.println("Adding cabs" + (start) + " " + (end) + " " + passengers);
        int[] tijd={99,99};
        ReisBeheer.addReis(passengers, start - 1, end - 1, tijd);
    }
    
}