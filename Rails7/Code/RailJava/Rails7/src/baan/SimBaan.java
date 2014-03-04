/*
 * SimBaan.java
 *
 * Created on 13 december 2007, 1:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package baan;
import baan.onderdelen.*;
import baan.onderdelen.WisselSim;
import java.util.Observable;
import java.util.Observer;

/**
 * Deze klasse beheerdt de Vituele baan.
 * vanuit deze klasse kunne Virtuele wissel en treinen worden beheerd
 * @author Team 7
 */
public class SimBaan extends Observable implements Observer, BaanInterface{
    private TreinInterface[] treinen;
    private WisselSim[] wissels;
    private int treinteller = 0;
    private int wisselteller = 0;
    
    /** Creates a new instance of SimBaan */
    public SimBaan() {
        treinen = new TreinSim[8];
        wissels = new WisselSim[8];
        for(int i = 0; i < wissels.length; i++){
            wissels[i] = new WisselSim(wisselteller);
            wisselteller++;
        }
    }
    
    /**
     * Hiermee wordt een trein aan de baan toegevoegt
     * @param id De ID die de trein moet krijgen
     */
    public void addTrein(int id) {
        treinen[treinteller] = new TreinSim(id);
        treinen[treinteller].addObserver(this);
        treinteller++;
    }
    
    /**
     * Hiermee wordt een trein van de baan verwijderd
     * @param id De ID van de trein die verwijderd moet worden
     */
    public void deleteTrein(int id){
        for(int i = 0;i < treinteller;i++)
        {
            if(treinen[i].getTreinNummer() == id){
                treinen[i] = null;
                for(int j = i; j < treinteller; j++)
                {
                    treinen[i] = treinen[i +1];
                }
                treinteller--;
                idVernieuw();
                break;
            }   
        }
    }
    
    /**
     * methode om een trein te laten rijden.
     * @param trein ID van de trein die moet rijden
     */
    public void startTrein(int trein) {
        try{
           treinen[trein].start(); 
        }catch(Exception e){}
    }
    
    /**
     * methode om een trein te laten stoppen.
     * @param trein ID van de trein die moet stoppen
     */
    public void stopTrein(int trein) {
        try{
            treinen[trein].stop();
        }catch(Exception e){}
    }
    
    /**
     * Deze methode veranderd de snelheid van een trein
     * @param trein De ID van de te wijzigenen trein
     * @param snelheid De nieuwe snelheid
     */
    public void setTreinSnelheid(int trein, int snelheid) {
        try{
            treinen[trein].setSnelheid((byte) snelheid);
        }catch(Exception e){}
    }
    
    /**
     * Deze methode retourneert een treinklasse
     * @param id ID van de trein
     * @return de gezochte treinklasse
     */
    public TreinInterface getTrein(int id){
       for(int i = 0; i < treinteller; i++)
       {
           if(treinen[i].getTreinNummer() == id)
               return treinen[i];
       }
       return null;
    }
    
    /**
     * Een methode die aangeroepen wordt als de klasse een bericht van een observable binnen krijgt
     * @param object Het object waar het bericht vandaan kwam
     * @param message een eventueel megeleverd bericht
     */
    public void update(Observable object, Object message) {
        TreinSim trein = (TreinSim) object;
        Object obj = new Integer(trein.getTreinNummer());
        setChanged();
        notifyObservers(obj);
    }
    
    /**
     * Deze methode voegt een virtuele Wissel toe aan de baan
     */
    public void voegWisselToe() {
        this.wissels[wisselteller] = new WisselSim(wisselteller);
    }
    
    /**
     * Deze methode zet een wissel om
     * @param id -Het ID van de om te zetten wissel
     * @param open De nieuwe status van de wissel
     */
    public void zetWisselOm(int id, boolean open){
       if(wissels[id].isOpen()) wissels[id].zetOm(false);
       else wissels[id].zetOm(true);
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
    public void idVernieuw()
    {
       if(treinteller != 0){
            for(int i = 0; i < treinteller;i++ ){
                treinen[i].setTreinNummer(i);
            }
       }
    }
   
    /**
     * een methode om het baangedeelte van het systeem af te sluiten
     * @return een bevestiging dat het afsluiten is gelukt
     */
    public boolean sluitAf() {
        return true;
    }
}