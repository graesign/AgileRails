/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package baan;
import baan.onderdelen.TreinInterface;
import java.util.Observable;

/**
 * Deze interface wordt gebruikt om de fisieke en virtuele baan in een programma te gebruiken.
 * Wordt geimplementeert door Baan en SimBaan
 * @author Team 7
 */
public interface BaanInterface {
    
    /**
     * methode om een trein te laten rijden.
     * @param trein ID van de trein die moet rijden
     */
    void startTrein(int trein);
    
    /**
     * methode om een trein te laten stoppen.
     * @param trein ID van de trein die moet stoppen
     */
    void stopTrein(int trein);
    
    /**
     * Deze methode retourneert een treinklasse
     * @param id ID van de trein
     * @return de gezochte treinklasse
     */
    TreinInterface getTrein(int id);
    
    /**
     * Hiermee wordt een trein aan de baan toegevoegt
     * @param id De ID die de trein moet krijgen
     */
    void addTrein(int id);
    
    /**
     * Hiermee wordt een trein van de baan verwijderd
     * @param id De ID van de trein die verwijderd moet worden
     */
    void deleteTrein(int id);
    
    /**
     * Deze methode veranderd de snelheid van een trein
     * @param trein De ID van de te wijzigenen trein
     * @param snelheid De nieuwe snelheid
     */
    void setTreinSnelheid(int trein, int snelheid);
    
    /**
     * Deze methode zet een wissel om
     * @param id -Het ID van de om te zetten wissel
     * @param open De nieuwe status van de wissel
     */
    void zetWisselOm(int id, boolean open);
    
    /**
     * Deze methode haald de status van een wissel o
     * @param id Het ID van de wissel
     * @return de status van de wissel
     */
    boolean getWisselStatus(int id);
    
    /**
     * Een methode die aangeroepen wordt als de klasse een bericht van een observable binnen krijgt
     * @param object Het object waar het bericht vandaan kwam
     * @param message een eventueel megeleverd bericht
     */
    void update(Observable object, Object message);
    
    /**
     * een methode om de ID's van de treinen te vernieuwen als er een trein is verwijderd
     */
    void idVernieuw();
    
    /**
     * een methode om het baangedeelte van het systeem af te sluiten
     * @return een bevestiging dat het afsluiten is gelukt
     */
    boolean sluitAf();
}
