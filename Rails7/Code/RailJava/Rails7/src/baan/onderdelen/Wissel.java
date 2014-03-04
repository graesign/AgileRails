/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package baan.onderdelen;

import USB4Nemo.*;

/**
 * Deze klasse wordt gebruikt om de wissel op de baan aan te sturen. 
 * Deze klsse houdt ook bij wat de status van de wissel is.
 * @author Team 7
 */
public class Wissel{
    private boolean naarStation; // de richting van de wissel
    private int wisselNummer;
    private Port poort;
    private int pinNr; // dit is pin die hoog moet zijn om open te gaan.
    
    /**
     * Maakt een nieuwe instantie van WisselSim aan
     * @param wisselNummer De ID dat deze wissel krijgt
     * @param usb het USB4NemoDevice dat gebruikt wordt om de wissels aan te spreken
     */
    public Wissel(int wisselNummer, USB4NemoDevice usb) {
        this.wisselNummer = wisselNummer;
        this.naarStation = false;
        
        // de eerste 4 wissels zitten op poort 0
        if(wisselNummer < 4) {
            this.poort = usb.getPort0();
        } else {
        // en de andere 4 op poort 1
            this.poort = usb.getPort1();
        }
        switch(wisselNummer) {
            case 0: pinNr = 6;
                    break;
            case 1: pinNr = 4;
                    break;
            case 2: pinNr = 2;
                    break;
            case 3: pinNr = 0;
                    break;
            case 4: pinNr = 6;
                    break;
            case 5: pinNr = 4;
                    break;
            case 6: pinNr = 2;
                    break;
            case 7: pinNr = 0;
                    break;        
        }
    }
    
    /**
     * deze methode wordt gebruikt om de wissel om te zetten
     * @param naarStation De nieuwe status voor de wissel
     */
    public void zetOm(boolean naarStation) {
        // moet de status gewijzigd worden?
        if(this.naarStation != naarStation) {
            
            if(naarStation) {
                poort.setPinValue(pinNr + 1, true);
           } 
            if(!naarStation) {
                poort.setPinValue(pinNr, true);
            }
            //pauze om motor te laten draaien
            try {

               Thread.sleep(150);
            }
            catch(InterruptedException e) {
               e.printStackTrace();
            } finally {
            
            if(naarStation)
                poort.setPinValue(pinNr + 1, false);
                this.naarStation = false;
            if(!naarStation)
                poort.setPinValue(pinNr, false);
                this.naarStation = true;
            }
        }
    }
    
    /**
     * Deze methode retourneert Het ID van de wissel
     * @return ID De ID van de wissel
     */
    public int getWisselNummer() {
        return wisselNummer;
    }
    
    /**
     * Deze methode retourneert de status van de wissel.
     * deze kan open (true) of dicht (false) zijn.
     * @return de status van de wissel.
     */
    public boolean isOpen() {
        return naarStation;
    }

}