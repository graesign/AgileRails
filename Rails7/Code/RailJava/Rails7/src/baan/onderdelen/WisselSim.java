/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package baan.onderdelen;

/**
 * Dit is een klasse voor een virtuele wissel
 * @author Team 7
 */
public class WisselSim{
    private int wisselnummer;
    private boolean open;
    
    /**
     * Maakt een nieuwe instantie van WisselSim aan
     * @param wisselnummer De ID dat deze wissel krijgt
     */
    public WisselSim(int wisselnummer) {
        this.open  = false;
        this.wisselnummer = wisselnummer;
    }

    /**
     * deze methode wordt gebruikt om de wissel om te zetten
     * @param zetOpen De nieuwe status voor de wissel
     */ 
    public void zetOm(boolean zetOpen) {
        this.open = zetOpen;
    }

    /**
     * Deze methode retourneert de status van de wissel.
     * deze kan open (true) of dicht (false) zijn.
     * @return de status van de wissel.
     */
    public boolean isOpen() {
        return this.open;
    }

    /**
     * Deze methode retourneert Het ID van de wissel
     * @return ID De ID van de wissel
     */
    public int getWisselNummer() {
        return this.wisselnummer;
    }
}



