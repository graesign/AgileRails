/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import railcab.Main;
import railcab.Timer;

/**
 *
 * @author Administrator
 */
public class AddCab {
    
    public static Main main;

    public static void add(int start, int end, int passengers) {
        int tijd = (int) Math.ceil(Timer.getTijd() / 60.0);
        while (passengers-- > 0) {
            main.maakReiziger(start, end, tijd);
        }
    }
    
}
