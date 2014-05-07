
/*
 * TreinController.java
 *
 * Created on 14 december 2007, 12:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package railcab;
import baan.*;
import connection.Listen;
import data.*;
import java.util.*;

/**
 * Deze klasse controleert alle treinen
 * @author Team 7
 */
public class TreinController{
    private BaanInterface baan;
    private int treinaantal = 0;
    private Trein[] treinlijst;
    private int baangrootte;
    private int maxaantalpassagiersintrein = 5;
    private Main main;
    
    /** Creates a new instance of TreinController */
    public TreinController(int baangrootte, Main main, BaanInterface baan){
        this.baan = baan;
        this.treinlijst = new Trein[9];
        this.baangrootte = baangrootte;
        this.main = main;
    }
    
    /**
     * Deze methode retourneert een treinklasse met het opgegeven ID
     * @param id Het ID van de te zoeken Trein
     * @return De gevonden trein
     */
    protected Trein getTrein(int id){
        return treinlijst[id];
    }
    
    /**
     * Deze methode retourneert het huidige aantal treinen
     * @return Het huidige aantal treinen
     */
    protected int getTreinAantal(){
        return treinaantal;
    }
    
    /**
     * Deze methode update de positie van een trein.
     * @param treinnummer het ID van de trein die geupdate moet worden
     */
    protected void updateTrein(int treinnummer){
        Trein trein = treinlijst[treinnummer];
        int temp = trein.getPositie() + 2;
        
        //controle baanlimiet
        if (temp >= baangrootte) {
            temp = temp - baangrootte;
        }
        
        //controleerd of het volgende baandeel de bestemming v/d trein is
        if (trein.doetTaak()){
            if (trein.getBestemming() != temp){
                    if(!(main.getBaandeel(trein.getPositie()).equals("stationrij"))){
                    temp = temp - 1;
                    if (temp < 0) {
                        temp = temp + baangrootte;
                    }
                }
            }
        }
        else {
            
            //controleerd of het volgende baandeel de VolgVertrekpunt v/d trein is
            if(trein.heeftVolgTaak()){
                if (trein.getVolgVertrekpunt() != temp) {
                    if(!(main.getBaandeel(trein.getPositie()).equals("stationrij"))){
                        temp = temp - 1;
                        if(temp < 0) {
                            temp = temp + baangrootte;
                        }
                    }
                }
            }
        }
        trein.setPositie(temp);
        if (temp == trein.geenreservering) {
            trein.geenreservering = -1;
        }
        
        //update naar de gui met de nieuwe positie
        int[] treinid = new int[1], arg = new int[1];
        treinid[0] = trein.getId();
        arg[0] = temp;
        main.updateGui("trein", treinid, arg);
        StationAankomst(temp, trein);
    }
    
    /**
     * Deze methode laat een trein bij een station stoppen als dat de bedoeling is.
     * @param temp De positie van de trein
     * @param trein De trein die eventueel moet stoppen
     */
    private void StationAankomst(int temp, Trein trein){     
        //Situatie 1: trein aangekomen op bestemming
        if((temp == trein.getBestemming()) && trein.doetTaak()){
            baan.stopTrein(trein.getId());
            trein.setRijdend(false);
            //update naar de gui
            int[] id = new int[1], arg = new int[3], reizigerids = new int[trein.getPassagierAantal()];
            int argR[] = {1};
            id[0] = trein.getId();
            reizigerids = trein.getHuidigePassagiersLijst();
            //Standard value already 0.
            //arg[0] = 0;
            //arg[1] = 0;
            //arg[2] = 0;
            main.updateGui("treindata", id, arg); 
            if(trein.getPassagierAantal() != 0) {
                int[] passagierslijst = trein.getHelePassagiersBestemming();
                System.out.println("begin: " + passagierslijst[0] + ", einde: " + passagierslijst[1] + ", aantal: " + passagierslijst[2]);
                Listen.addCab(passagierslijst[2], passagierslijst[1] - 8);
                main.updateGui("reiziger", reizigerids, argR);
            }
            trein.setRijdend(false);
            trein.setGeenTaak();
            main.zetWisselDicht(temp);
        }

        //Situatie 2: trein aangekomen op volgVertrekpunt
        else{
            if(temp == trein.getVolgVertrekpunt() && trein.heeftVolgTaak()){
                baan.stopTrein(trein.getId());
                trein.setRijdend(false);
                main.zetWisselDicht(temp);
            }

            //Situatie: trein aangekomen op stationstop voor bestemming of VolgVertekpunt
            else{
                StationStopTrein(temp, trein);
            }
        }
    }
    
    /**
     * Deze methode laat een trein stopen als het station wat het in moet bezet is, 
     * en laat het weer verder rijden als het station weer vrij is
     * @param temp De positie van de trein
     * @param trein De trein die eventueel moet stoppen
     */
    private void StationStopTrein(int temp, Trein trein){
        //Situatie 3: trein aangekomen op stationstop voor bestemming
        if(temp+2 == trein.getBestemming() && trein.doetTaak()){
            Trein trein2;
            main.zetWisselOpen(temp+2);
            if((trein2 = bevatTrein(temp+2)) != null)
            {
                if(trein2.rijdend() == false)
                {
                    baan.stopTrein(trein.getId());
                    trein.setRijdend(false);
                    if(!trein2.doetTaak()) {
                        int bestemmingTemp = main.zoekVrijStationNa(trein2.getPositie());
                        //Update naar gui
                        int[] treinid = {trein2.getId()};
                        int[] data = { 0, main.getEchtStationNummer(bestemmingTemp), 
                                      main.getEchtStationNummer(trein2.getPositie()) };
                        main.GUI.updateGui("treindata", treinid, data);
                        trein2.gaNaar(bestemmingTemp);
                        baan.startTrein(trein2.getId());
                    }
                    TreinObserver treinobserver = new TreinObserver(trein, trein2, baan);
                }
            }
        }
                    
        //Situatie 4: trein aangekomen op stationstop voor VolgVertrekpunt
        else{
            if((temp+2 == trein.getVolgVertrekpunt()) && trein.heeftVolgTaak()){
                Trein trein2;
                main.zetWisselOpen(temp+2);
                if((trein2 = bevatTrein(temp+2)) != null)
                {
                    if(trein2.rijdend() == false)
                    {
                    baan.stopTrein(trein.getId());
                    trein.setRijdend(false);
                    if(!trein2.doetTaak()) { 
                        int bestemmingTemp = main.zoekVrijStationNa(trein2.getPositie());
                        trein2.gaNaar(bestemmingTemp);
                        //Update naar gui
                        int[] treinid = {trein2.getId()};
                        int[] data = { 0, main.getEchtStationNummer(bestemmingTemp), 
                        main.getEchtStationNummer(trein2.getPositie()) };
                        main.GUI.updateGui("treindata", treinid, data);
                        baan.startTrein(trein2.getId());
                    }
                    TreinObserver treinobserver = new TreinObserver(trein, trein2, baan);
                    }
                }
            }

            //Situatie 5: sluit de wissel op een stations rij
            else{
                if(!(main.getBaandeel(temp).equals("stationstop"))){
                    main.zetWisselDicht(temp+1);
                }
            }
        }
    }
    
    /**
     * Deze methode start een trein.
     * @param trein De trein die gestart moet worden
     */
    protected void startTrein(Trein trein) {
        int temp = trein.getPositie();
        Trein trein2;
        if ((trein2 = bevatTrein(temp - 2)) == null) {
            if ((trein2 = bevatTrein(temp - 1)) == null) {
                if ((trein2 = bevatTrein(temp + 1)) == null) {
                    baan.startTrein(trein.getId());
                    trein.setRijdend(true);
                }
                else {
                    if (trein2.rijdend()) {
                        baan.startTrein(trein.getId());
                        trein.setRijdend(true);
                    }
                    else {
                        TreinstartObserver observer = new TreinstartObserver(this, baan, trein, trein2);
                    }
                }
            }
            else {
                TreinstartObserver observer = new TreinstartObserver(this, baan, trein, trein2);
            }
        }
        else {
            TreinstartObserver observer = new TreinstartObserver(this, baan, trein, trein2);
        }
        
        int bestemming = 0;
        if(trein.doetTaak()) {
            bestemming = trein.getBestemming();
        } else {
            bestemming = trein.getVolgVertrekpunt();
        }
        int[] treinid = { trein.getId() };
        int[] treindata = { trein.getPassagierAantal(),
                            main.getEchtStationNummer(bestemming),
                            main.getEchtStationNummer(trein.getPositie()) };
        Main.GUI.updateGui("treindata", treinid, treindata);
    }
    
    /**
     * Deze methode zoekt een trein die nog gereserveert kan worden.
     * @param vertrekpunt Het vertrekpunt van de reiziger waarvoor een trein wordt gezocht.
     * @param vertrektijd De vertektijd van de reiziger waarvoor een trein wordt gezocht.
     * @param uitzonderingen Een lijst met treinen die niet in de zoekopdracht moeten worden meegenomen
     * @return De gevonde trein
     */
    protected Trein zoekBeschikbareTrein(int vertrekpunt, int vertrektijd, Trein[] uitzonderingen) {
        Trein beschikbaretrein = null;
        boolean prioriteit = false;
        int beschikbaretreinafstand = baangrootte;
        for (int i = 0; i < treinaantal; i++) {
            boolean uitzondering = false;
            for (int j = 0; j < uitzonderingen.length; j++) {
                if (uitzonderingen[j] == treinlijst[i]) {
                    uitzondering = true;
                }
            }
            if (!uitzondering) {
                if (treinlijst[i].geenreservering == -1) {
                    if (!treinlijst[i].heeftVolgTaak()) {
                        if (!prioriteit) {
                            int treinafstand = vertrekpunt - treinlijst[i].getPositie();
                            if (treinafstand < 0) {
                                treinafstand = treinafstand + baangrootte;
                            }
                            if (treinafstand < beschikbaretreinafstand) {
                                beschikbaretrein = treinlijst[i];
                                beschikbaretreinafstand = treinafstand;
                            }
                        }
                    }
                    else if (treinlijst[i].getVolgVertrekpunt() == vertrekpunt) {
                        if (treinlijst[i].getVolgPassagierAantal() < maxaantalpassagiersintrein) {
                            if (treinlijst[i].getMaxVertrekTijd() >= vertrektijd) {
                                int treinafstand = treinlijst[i].getPositie() - vertrekpunt;
                                if (treinafstand < 0) {
                                    treinafstand = treinafstand + baangrootte;
                                }
                                if (!prioriteit) {
                                    beschikbaretrein = treinlijst[i];
                                    beschikbaretreinafstand = treinafstand;
                                    prioriteit = true;
                                }
                                if (treinafstand < beschikbaretreinafstand) {
                                    beschikbaretrein = treinlijst[i];
                                    beschikbaretreinafstand = treinafstand;
                                }
                            }
                        }
                    }
                }
            }
        }
        return beschikbaretrein;
    }
    
    /**
     * Deze methode reserveert een trein voor een reiziger.
     * @param passagier De reiziger waarvoor een trein moet worden gereserveerd
     * @return true als het is gelukt, false als het reserveren is mislukt
     */
    protected boolean reserveerTrein(Reiziger passagier) {
        int vertrekpunt = passagier.getVertrekpunt();
        int bestemming = passagier.getEindpunt();
        int vertrektijd = passagier.getVertrekTijd();
        Trein trein = zoekBeschikbareTrein(vertrekpunt, vertrektijd, new Trein[0]);
        if (trein == null) {
            return false;
        }
        if (!trein.heeftVolgTaak()) {
            trein.setVolgTaak(vertrekpunt, bestemming, vertrektijd);
        }
        trein.setPassagier(passagier);
        if (trein.getVertrekTijd() < vertrektijd) {
            trein.setVertrekTijd(vertrektijd);
        }
        return true;
    }
    
    /**
     * Deze methode plaatst een reiziger in een trein.
     * @param passagier De te plaatse reiziger 
     * @param trein De trein waar de reiziger in moet
     */
    protected void zetPassagierInTrein(Reiziger passagier, Trein trein) {
        trein.setPassagier(passagier);
    }
    
    /**
     * Deze methode controleert of er een trein op het opgegeven baandeel staat
     * @param baandeel Het baandeel dat geconroleerd moet worden
     * @return De eventueel gevonden trein, of null als er geen trein is gevonden
     */
    protected Trein bevatTrein(int baandeel) {
        for (int i = 0; i < treinaantal; i++) {
            if (baandeel == treinlijst[i].getPositie()) {
                return treinlijst[i];
            }
        }
        return null;
    }
    
    /**
     * Deze methode maakt een nieuwe trein aan op een opgegeven station
     * @param positie het station waar de trein geplaatst moet worden
     * @return Het ID van de nieuwe trein
     */
    protected int maakTrein(int positie){
        for (int i = 0; i <= treinaantal; i++){
            if (treinlijst[i] == null){
                treinlijst[i] = new Trein(positie, treinaantal++);
                baan.addTrein(treinlijst[i].getId());
                //update naar gui
                    int trein[] = new int[1];
                    trein[0] = treinlijst[i].getId();
                    int[] arg = new int[1];
                    arg[0] = positie;
                    main.updateGui("trein", trein, arg);
                return treinlijst[i].getId();
            }
        }
        return 0;
    }
    
    /**
     * Deze methode verwijderd een trein
     * @param id Het ID van de te verwijdere trein
     */
    protected void verwijderTrein(int id){
        for(int i = 0;i < treinaantal;i++)
        {
            if(treinlijst[i].getId() == id){
                treinlijst[i] = null;
                for(int j = i; j < treinaantal; j++)
                {
                    treinlijst[j] = treinlijst[j +1];
                }
                baan.deleteTrein(id);
                treinaantal--;
                idVernieuw();
                printTreinen();
                treinRepaint();
                break;
            }   
        }
    }
    
    /**
     * Deze methode stuurt de nieuwe gegevens van de treinen naar de gui
     */
    private void treinRepaint()
    {
        if(treinaantal != 0){
            for(int i =0; i < treinaantal; i++){
                int[] id = {treinlijst[i].getId()};
                int[] arg = {treinlijst[i].getPositie()};
                main.updateGui("trein", id, arg);
            }
        }
    }
    
    /**
     * Vernieuwd de ID´s van de treinen
     */
    protected void idVernieuw()
    {
        if(treinaantal != 0){
            for(int i = 0; i < treinaantal;i++ ){
                treinlijst[i].setId(i);
            }
        } 
    }
    
    /** Print alle treinen */
    public void printTreinen(){
        System.out.println("");
        for(int i = 0;i < treinaantal;i++){
            System.out.println("TreinCon: trein: " + treinlijst[i].getId() + " positie: " + treinlijst[i].getPositie()+ " i: "+ i);
        }
    }
    
    /**
     * Deze methode controleert of er een trein is met het opgegeven vertekpunt.
     * @param vertrekpunt Het vertrekpunt waarvoor een trein wordt gezocht
     * @return De trein met het opgegeven vertrekpunt of null, als er geen trein wordt gevonden
     */
    protected Trein heeftTreinVolgVertrekpunt(int vertrekpunt) {
        for(int i = 0; i < treinaantal; i++) {
            if(getTrein(i).heeftVolgTaak()) {
               if(getTrein(i).getVolgVertrekpunt() == vertrekpunt) {
                return getTrein(i);
               }
            }

        }
        return null;
    }
    
    /**
     * Deze methode controleert of er een trein is met de opgegeven bestemming.
     * @param bestemming De bestemming waarvoor een trein gezocht wordt
     * @return De trein met de opgegeven bestemming of null, als er geen trein wordt gevonden
     */
    protected Trein heeftTreinBestemming(int bestemming) {        
        for(int i = 0; i < treinaantal; i++) {
               if(getTrein(i).getBestemming() == bestemming) {
                return treinlijst[i];
               }
        }
        return null;
    }
    
    /**
     * Deze methode wijzigt het aaaximale aantal passagiers per trein.
     * @param aantal Het nieuwe maximale aantal passagiers per trein
     */
    protected void setPassagiersaantal(int aantal) {
        maxaantalpassagiersintrein = aantal;
    }
    
    /**
     * Deze methode retourneert het maximale aantal passagiers per trein.
     * @return Het maximale aantal passagiers per trein.
     */
    protected int getMaxAantalPerTrein() {
        return maxaantalpassagiersintrein;
    }
}
