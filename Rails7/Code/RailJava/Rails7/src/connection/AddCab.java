/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import static connection.Listen.normalize;
import railcab.Main;

/**
 *
 * @author Administrator
 */
public class AddCab {
    
    public Main main;
    
    public AddCab(Main main) {
        this.main = main;
    }

    public void add(int start, int end, int passengers) {
        Main.GUI.setPassengers(start - 8, end - 8, passengers);
    }
}