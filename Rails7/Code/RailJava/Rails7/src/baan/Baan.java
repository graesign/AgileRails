package baan;
import baan.onderdelen.*;
import USB4Nemo.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * deze klasse beheert de baan in het geval van een hardwarematige baan
 * via deze klasse kunnen wissels en treinen worden aangestuurt worden.
 * @author Team 7
 */
public class Baan extends Observable implements Observer,BaanInterface{
    Trein[] treinen = new Trein[4];
    String[] compoorten = new String[] { "COM40", "COM41", "COM42", "COM12" };
    Wissel[] wissels = new Wissel[8];
    USB4NemoDevice usb = new USB4NemoDevice();
    int treinteller = 0;
    
    /** maakt een nieuwe Instantie van Baan aan */
    public Baan() {
        // USB4Nemo resetten
        try { 
            usb.getPort0().setPortPinDirections((byte) 0);
            usb.getPort1().setPortPinDirections((byte) 0);
            usb.getPort0().setPortPinValues((byte) 0);
            usb.getPort1().setPortPinValues((byte) 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        // Wisssels aanmaken
        try {
            for(int i = 0; i < 8; i++) {
                wissels[i] = new Wissel(i, usb);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
       
    }
    
    /**
     * Hiermee wordt een trein aan de baan toegevoegt
     * @param trein De ID die de trein moet krijgen
     */
    public void addTrein(int trein) {
        try {
            treinen[trein] = new Trein(0);
            treinen[trein].setCompoort(compoorten[trein]);
            treinen[trein].verbind();
            treinen[trein].setLight(true);
            treinen[trein].setSensor(true);
            treinen[trein].addObserver(this);
            treinteller++;
        }
        catch( Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * methode om een trein te laten rijden.
     * @param trein ID van de trein die moet rijden
     */
    public void startTrein(int trein) {
        try {
            treinen[trein].start();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * methode om een trein te laten stoppen.
     * @param trein ID van de trein die moet stoppen
     */
    public void stopTrein(int trein) {
        try {
            treinen[trein].stop();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Deze methode retourneert een treinklasse
     * @param id ID van de trein
     * @return de gezochte treinklasse
     */
    public TreinInterface getTrein(int id) {
        return treinen[id];
    }
    
    /**
     * Hiermee wordt een trein van de baan verwijderd
     * @param id De ID van de trein die verwijderd moet worden
     */
    public void deleteTrein(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Deze methode veranderd de snelheid van een trein
     * @param trein De ID van de te wijzigenen trein
     * @param snelheid De nieuwe snelheid
     */
    public void setTreinSnelheid(int trein, int snelheid) {
        try {
            treinen[trein].setSnelheid((byte) snelheid);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Deze methode zet een wissel om
     * @param id -Het ID van de om te zetten wissel
     * @param open De nieuwe status van de wissel
     */
    public void zetWisselOm(int id, boolean open) {
        wissels[id].zetOm(open);
    }
    
    /**
     * Deze methode haald de status van een wissel o
     * @param id Het ID van de wissel
     * @return de status van de wissel
     */
    public boolean getWisselStatus(int id){
        return wissels[id].isOpen();
    }

    /**
     * een methode om de ID's van de treinen te vernieuwen als er een trein is verwijderd
     */
    public void idVernieuw() {
        if(treinteller != 0){
            for(int i = 0; i < treinteller;i++ ){
                treinen[i].setTreinNummer(i);
            }
       }
    }
    
    /**
     * Een methode die aangeroepen wordt als de klasse een bericht van een observable binnen krijgt
     * @param object Het object waar het bericht vandaan kwam
     * @param message een eventueel megeleverd bericht
     */
    public void update(Observable object, Object message) {
        Trein trein = (Trein) object;
        Object obj = new Integer(trein.getTreinNummer());
        setChanged();
        notifyObservers(obj);
    }
    
    /**
     * een methode om het baangedeelte van het systeem af te sluiten
     * @return een bevestiging dat het afsluiten is gelukt
     */
    public boolean sluitAf() {
        for (int i = 0; i < treinteller; i++) {
            try {
                treinen[i].verbreek();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("systeem beeindigd");
        return true;
    }

}