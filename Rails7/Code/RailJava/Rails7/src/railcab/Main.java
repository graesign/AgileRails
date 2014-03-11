/*
 * Main.java
 *
 * Created on 19 november 2007, 11:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package railcab;
import javax.swing.*;
import java.awt.event.*;
import data.*;
import baan.*;
import connection.Database;
import java.util.Observer;
import java.util.Observable;
import java.io.*;


/**
 *
 * @author Team 7
 */
public class Main implements Observer {
    private LinkedList wachtlijst;
    private String[] baanarray;
    private int baangrootte;
    public static gui.GUIMain GUI;
    public static gui.Simulatie SIM;
    private BaanInterface baan;
    private TreinController treinen;
    private Reservering reservering;
    
    /**
     * 
     * @param hardwarebaan
     */
    public Main(boolean hardwarebaan) {
        initieerBaanDelen();
        
        wachtlijst = new LinkedList();
        
        if (hardwarebaan) {
            baan = new Baan();
            ((Baan)baan).addObserver(this);
        }
        else {
            baan = new SimBaan();
            ((SimBaan)baan).addObserver(this);
        }
        
        treinen = new TreinController(baangrootte, this, baan);
        
        //Maak de GUI
        SIM = new gui.Simulatie();
        GUI = new gui.GUIMain(this, hardwarebaan, SIM);
        
        SIM.setVisible(true);
        GUI.setVisible(true);
        SIM.plaatsWissel();
        
        new Timer(this, GUI);
        reservering = new Reservering(treinen, wachtlijst, this, baan);
        reservering.loop();
    }
    
    
    /**
     * Deze methode voegt een reiziger toe aan de wachtlijst
     * @param vertrek Het vertrekpunt van de reiziger
     * @param bestemming De bestemming van de reiziger
     * @param tijd De vertrektij van reiziger in secondes
     */
    public void maakReiziger(int vertrek, int bestemming, int tijd) {
        Reiziger reiziger = new Reiziger(zoekStation(vertrek) ,zoekStation(bestemming) ,tijd);
        this.wachtlijst.nieuwReiziger(reiziger);
    }
    
    /**
     * Deze methode geeft aan de TreinController klasse door
     * dat er een trein aangemaakt moet worden.
     * @param station Het ID van het station waaarop de trein moet komen te staan
     * @return DE ID van de nieuwe trein
     */
    public int maakTrein(int station){
        return treinen.maakTrein(zoekStation(station));
    }
    
    /**
     * Deze methode geeft aan de TreinController klasse door
     * dat er een trein verwijdert moet worden.
     * @param trein De ID van de te verwijderen trein
     */
    public void verwijderTrein(int trein){
        treinen.verwijderTrein(trein);
    }
    
    /**
     * Deze methode geeft aan de TreinController klasse door
     * dat het maximum aantel passagiers gewijzigd moet worden
     * @param aantal Het nieuwe maximum aantel passagier
     */
    public void setPassagiersaantal(int aantal) {
        treinen.setPassagiersaantal(aantal);
    }
    
    /**
     * Deze methode zoekt het ID van een station bij een poitie op de baan.
     * @param baandeel Het baandeel Waaraan het station staat
     * @return Het ID van het station
     */
    protected int getEchtStationNummer(int baandeel) {
        int nummer = 0;
        for (int i = 0; i < baangrootte; i++) {
            if (baanarray[i].compareTo("stationstop") == 0) {
                nummer++;
            }
            if (i == baandeel) {
                break;
            }
        }
        return nummer;
    }
    
    /**
     * Deze methode wordt aangeroepen als een trein langs een lichtpoort rijd.
     * deze methode roept dan de updateTrein methode van de TreinController aan.
     * @param object De trein waarvan de update afkomstig is
     * @param message Metadata van de trein
     */
    public void update(Observable object, Object message) {
        int treinid = ((Integer)message).intValue();
        treinen.updateTrein(treinid);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Database.connect();
        
        boolean baan = false;
        if(args.length > 0) {
            System.out.println("baan gekoppeld");
            baan = true;
        }
        new Main(baan);
    }
    
    /**
     * Deze methode initieert de lijst met baandelen.
     */
    private void initieerBaanDelen() {
    baangrootte = 30;
    baanarray = new String[baangrootte];
    baanarray[0] = "rail";
    baanarray[1] = "rail";
    baanarray[2] = "stationrij";
    baanarray[3] = "stationstop";
    baanarray[4] = "rail";
    baanarray[5] = "stationrij";
    baanarray[6] = "stationstop";
    baanarray[7] = "rail";
    baanarray[8] = "stationrij";
    baanarray[9] = "stationstop";
    baanarray[10] = "rail";
    baanarray[11] = "stationrij";
    baanarray[12] = "stationstop";
    baanarray[13] = "rail";
    baanarray[14] = "stationrij";
    baanarray[15] = "stationstop";
    baanarray[16] = "rail";
    baanarray[17] = "stationrij";
    baanarray[18] = "stationstop";
    baanarray[19] = "rail";
    baanarray[20] = "rail";
    baanarray[21] = "rail";
    baanarray[22] = "stationrij";
    baanarray[23] = "stationstop";
    baanarray[24] = "rail";
    baanarray[25] = "rail";
    baanarray[26] = "rail";
    baanarray[27] = "stationrij";
    baanarray[28] = "stationstop";
    baanarray[29] = "rail";
    }

    /**
     * Deze methode zet een wissel open.
     * @param positie De positie van de wissel op de baan
     */
    protected void zetWisselOpen(int positie) {
        int id = zoekWissel(positie);
        if(!baan.getWisselStatus(id)){
            SIM.zetWissel(id, 1);
            baan.zetWisselOm(id, true);
        }  
    }
    
    /**
     * Deze methode zet een wissel dicht.
     * @param positie De positie van de wissel op de baan
     */
    protected void zetWisselDicht(int positie) {
        int id = zoekWissel(positie);
        if(baan.getWisselStatus(id)){
            SIM.zetWissel(id, 0);
            baan.zetWisselOm(id, false);
        }    
    }
    
    /**
     * Deze methode zoekt een wissel bij een positie op de baan
     * @param positie De positie van de trein, waarvoor de wissel omgezet moet worden
     * @return De ID van de gevonden wissel
     */
    protected int zoekWissel(int positie){
        int w = 0;
        if(positie <= 3) {
            w  = 1;
        } else {
            for(int i = 0; i < positie; i++){
                if (baanarray[i].compareTo("stationrij") == 0){
                    w++;
                }
            }
        }
        return (w-1);
    }
    
    // Nodig voor reizigerstabel. Misschien anders oplossen?
    /**
     * 
     * @param subject
     * @param id
     * @param arg
     */
    protected void updateGui(String subject, int[] id, int[] arg){
        GUI.updateGui(subject, id, arg);
    }
    
    /**
     * Deze methode zoekt het baandeel bij een ID van een station
     * @param station Het ID van het station
     * @return Het baandeel bij het station
     */
    protected int zoekStation(int station){
        int i = 0, s = 0;
        while ( i != (station)) {
            if (baanarray[s].compareTo("stationstop") == 0) {
                i++;
            }
            s++;
        }
        return (s-1);
    }
    
    // Methode om het volledig vrije station voor het opgegeven baandeel te vinden.
    /**
     * 
     * @param stationBaandeel
     * @return
     */
    protected int zoekVrijStationVoor(int stationBaandeel) {
        int vrijstation = stationBaandeel - 1;
        
        while(vrijstation != stationBaandeel) {
            if( baanarray[vrijstation].equals("stationstop")) {
                if(treinen.bevatTrein(vrijstation) == null)
                    if(treinen.heeftTreinVolgVertrekpunt(vrijstation) == null)
                        if(treinen.heeftTreinBestemming(vrijstation) == null)
                            return vrijstation;
            }
            if (vrijstation == 0) {
                vrijstation = baangrootte - 1;
            } else {
                vrijstation--;
            }
        }
        return -1;
    }
    
    /**
     * 
     * @param stationBaandeel
     * @return
     */
    protected int zoekVrijStationNa(int stationBaandeel) {
        int vrijstation = stationBaandeel + 1;
        
        while(vrijstation != stationBaandeel) {
            if( baanarray[vrijstation].equals("stationstop")) {
                if(treinen.bevatTrein(vrijstation) == null)
                    if(treinen.heeftTreinVolgVertrekpunt(vrijstation) == null)
                        if(treinen.heeftTreinBestemming(vrijstation) == null)
                            return vrijstation;
            }
            if (vrijstation == baangrootte - 1) {
                vrijstation = 0;
            } else {
                vrijstation++;
            }
        }
        return -1;
    }
    
    /**
     * Deze methode contoleert of een station leeg is
     * @param station Het ID van het station
     * @return True als het station leeg is, false als er een trein staat
     */
    public boolean StationIsLeeg(int station)
    {
        if(treinen.bevatTrein(zoekStation(station)) != null){
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @return
     */
    public int sluitAf() {
        baan.sluitAf();
        return javax.swing.WindowConstants.EXIT_ON_CLOSE;
    }
    
    /**
     * Deze methode retourneert de stringwaarde van een baandeel
     * @param baandeel Het baandeel waarvan de stringwaarde wordt gezocht
     * @return De stringwaarde van het baandeel
     */
    protected String getBaandeel(int baandeel){
        return this.baanarray[baandeel];
    }
}
