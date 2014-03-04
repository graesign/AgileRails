/*
 * TreinSim.java
 *
 * Created on 13 december 2007, 1:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package baan.onderdelen;

import java.awt.event.*;
/**
 *
 * @author Team 7
 */
public class TreinSim extends TreinInterface implements ActionListener{
    int treinNummer;
    boolean rijd;
    
    /** Creates a new instance of TreinSim */
    public TreinSim(int treinNummer) {
        this.treinNummer = treinNummer;
        rijd = false;
        railcab.Timer.addActionListener(this);
    }
    
    /**
     * Deze methode wijzigd de snelheid van de trein
     * Deze methode wordt in deze klasse niet gebruikt.
     * @param speed de nieuwe snelheid voor deze trein
     */
    public void setSnelheid(byte speed) {
    }
    
    /**
     * Deze methode retourneert het ID van deze trein
     * @return id De ID van de trein
     */
    public int getTreinNummer() {
        return treinNummer;
    }
    
    /**
     * Deze methode geeft de trein een nieuw ID
     * @param treinNummer het nieuwe trein nummer
     */
    public void setTreinNummer(int treinNummer){
        this.treinNummer = treinNummer;
    }
    
    /**
     * Deze methode start de trein
     */
    public void start() {
        rijd = true;
    }
    
    /**
     * Deze methode stopt de trein
     */
    public void stop() {
        rijd = false;
    }
    
    /**
     * Deze methode geeft door dat, indien de status rijdend is, de trein 1 baandeel verder is.
     * @param e Een actie van de Timer
     */
    public void actionPerformed(ActionEvent e) {
        if (rijd) {
            setChanged();
            notifyObservers();
        }
    }
}