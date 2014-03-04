/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package railcab;
import gui.GUIMain;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Deze klasse beheert de timer die gebruikt wordt binnen dit project
 * @author Team 7
 */
public class Timer implements ActionListener {
    static private javax.swing.Timer timer;
    static private int tijd;
    Main main;
    GUIMain gui;
    
    /**
     * Deze constructor Initialiseert de timer
     * @param main De main klasse
     * @param gui De main klasse van de GUI
     */
    public Timer(Main main, GUIMain gui) {
        tijd = 0;
        this.main = main;
        this.gui = gui;
        timer = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                tijd++;
             }
        });
        timer.addActionListener(this);
    }
    
    /**
     * Deze methode wordt aangeroepen als de timer een tik verder gaat.
     * De methode stuurt dan de nieuwe tijd naar de GUI.
     * @param e - Het event dat de methode aanroept
     */
    public void actionPerformed(ActionEvent e) {
        gui.setTijd(tijd);
    }
    
    /**
     * Deze methode voegt een nieuwe ActionListener aan de timer toe.
     * @param a D nieuwe ActionListener
     */
    static public void addActionListener(ActionListener a) {
        timer.addActionListener(a);
    }
    
    /**
     * Deze methode retourneert de tijd van de timer
     * @return De tijd in secondes
     */
    public static int getTijd() {
        return tijd;
    }
    
    /**
     * Deze methode wijzigt de snelheid van de timer
     * @param i De nieuw snelheid voor de timer
     */
    static public void setSimSnelheid(int i) {
        timer.setDelay(i);
    }
    
    /**
     * Deze methode retourneert de huidige snelheid van de timer
     * @return De huidige snelheid van de timer
     */
    static public int getSimSnelheid() {
        return timer.getDelay();
    }
    
    /**
     * Deze methode start de timer
     */
    static public void startTimer() {
        timer.start();
    }
    
    /**
     * Deze methode stopt de timer
     */
    static public void stopTimer() {
        timer.stop();
    }
}
