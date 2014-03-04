/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package railcab;
import data.*;
import baan.*;

/**
 * Deze klasse reserveert treinen en start treinen
 * @author Maarten
 */
public class Reservering {
    private TreinController treinen;
    private LinkedList wachtlijst;
    private Main main;
    private BaanInterface baan;
    
    private int[] templijst;
    private int templijstaantal;
    private Reiziger tempreiziger;
    
    /**
     * Deze constructor maakt het reserveringssysteem aan.
     * @param treinen De controlle instantie voor de treinen
     * @param wachtlijst De lijst met wachtende reizigers
     * @param main De main instantie
     * @param baan De instantie de de baan beheert
     */
    public Reservering(TreinController treinen, LinkedList wachtlijst, Main main, BaanInterface baan) {
        this.treinen = treinen;
        this.wachtlijst = wachtlijst;
        this.main = main;
        this.baan = baan;
    }
    
    /**
     * Deze methode start een eindeloze loop die de reserverigen afhandeld.
     * de methode spreekt ander methodes binnen deze klasse aan, afhankelijk van de situatie.
     */
    public void loop() {
        while(true) {
           try{
                // wachtende reizigers koppelen aan treinen
                if (wachtlijst.getAantal() != 0) {
                    templijst = new int[wachtlijst.getAantal()];
                    templijstaantal = 0;
                    tempreiziger = wachtlijst.getLast();
                    for (int i = 0; i < wachtlijst.getAantal(); i++) {
                        if ((tempreiziger.getVertrekTijd() - Timer.getTijd()) < 30) {
                           
                           // Situatie 1: Er is nog geen trein gereserveerd, reserveer de dichtsbijzinde
                           if(treinen.heeftTreinVolgVertrekpunt(tempreiziger.getVertrekpunt()) == null) {
                               situatie1();
                           }
                           
                           // Situatie 2: Er gaat al een trein heen, doe er passagiers bij
                           else if (treinen.heeftTreinVolgVertrekpunt(tempreiziger.getVertrekpunt()).getVolgPassagierAantal() < treinen.getMaxAantalPerTrein()) {
                               situatie2();
                           }
                           
                           // Situatie 3: Er is nog een trein vrij, stuur hem naar het dichtsbijzijnde station
                           else {
                               situatie3();
                            }
                        }
                        else {
                            break;
                        }
                        tempreiziger = tempreiziger.getPrevious();
                    }
                    for (int i = 0; i < templijstaantal; i++) {
                    wachtlijst.verwijderReiziger(templijst[i]);
                    }
                }
                // kijken of er treinen zijn die moeten beginnen aan hun taak
                startTaak();
            Thread.sleep(100);
           }catch(Exception e ){
                e.printStackTrace();
           }
        }
    }
    
    /**
     * Deze methode wordt aangesproken als er nog geen trein is gereserveert.
     * Deze mthode reserveerd dan de dichtsbijzijnde trein, gerekend vanaf het station van de waxhtende reiziger
     */
    private void situatie1() {
        if (treinen.reserveerTrein(tempreiziger)) {
            templijst[templijstaantal] = tempreiziger.getId();
            templijstaantal++;
        }
    }
    
    /**
     * Deze methode wordt aangeroepen als er al een trein op het station waar de reiziger
     * zich bevind staat die naar zijn bestemming gaat.
     * De reiziger wordt dan in die trein geplaatst.
     */
    private void situatie2() {
        treinen.zetPassagierInTrein(tempreiziger, treinen.heeftTreinVolgVertrekpunt(tempreiziger.getVertrekpunt()));
        templijst[templijstaantal] = tempreiziger.getId();
        templijstaantal++;
    }
    
    /**
     * Deze methode wordt aangeroepen als er nog een trein vrij is.
     * Deze trein wordt dan gereserveert.
     */
    private void situatie3() {
        Trein temptrein;
        Trein[] uitzonderingen = new Trein[8];
        int uitzonderingenaantal = 0;
                               
        while(true) {
            temptrein = treinen.zoekBeschikbareTrein(tempreiziger.getVertrekpunt(), tempreiziger.getVertrekTijd(), uitzonderingen);
            if ((temptrein == null) || !temptrein.bezet) {
                break;
            }
            uitzonderingen[uitzonderingenaantal] = temptrein;
            uitzonderingenaantal = uitzonderingenaantal + 1;
        }
        if (temptrein != null) {
            if(!temptrein.rijdend()) {
                int tempVrijStation = main.zoekVrijStationVoor(tempreiziger.getVertrekpunt());
                if(tempVrijStation != -1) {
                    if(temptrein.getPositie() < tempreiziger.getVertrekpunt())    {
                        if(tempVrijStation > temptrein.getPositie() && tempVrijStation < tempreiziger.getVertrekpunt()) {
                            int[] treinid = {temptrein.getId()};
                            int[] data = { 0, main.getEchtStationNummer(tempVrijStation), main.getEchtStationNummer(temptrein.getPositie()) };
                            temptrein.gaNaar(tempVrijStation);
                            main.updateGui("treindata", treinid, data);
                            temptrein.bezet = true;
                            baan.startTrein(temptrein.getId());
                        }
                    }
                    else {
                        if (tempVrijStation > temptrein.getPositie() || tempVrijStation < tempreiziger.getVertrekpunt()) {
                            int[] treinid = {temptrein.getId()};
                            int[] data = { 0, main.getEchtStationNummer(tempVrijStation), main.getEchtStationNummer(temptrein.getPositie()) };
                            temptrein.gaNaar(tempVrijStation);
                            main.updateGui("treindata", treinid, data);
                            temptrein.bezet = true;
                            baan.startTrein(temptrein.getId());
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Deze methode controleerd of er treinen zijn die aan een taak moeten beginnen
     * en start de taak als dat nodig is.
     */
    private void startTaak() {
        int[] id = new int[10], arg = new int[10];
        for (int i = 0; i < treinen.getTreinAantal(); i++) {
            if (!treinen.getTrein(i).doetTaak()) {
                if (treinen.getTrein(i).heeftVolgTaak()) {
                    if (treinen.getTrein(i).getVolgVertrekpunt() == treinen.getTrein(i).getPositie()) {
                        if (Timer.getTijd() >= treinen.getTrein(i).getVertrekTijd()) {
                            treinen.getTrein(i).setTaak();
                            arg[0] = treinen.getTrein(i).getPassagierAantal();
                            arg[1] = main.getEchtStationNummer(treinen.getTrein(i).getBestemming());
                            arg[2] = main.getEchtStationNummer(treinen.getTrein(i).getPositie());
                            id[0] = treinen.getTrein(i).getId();
                            main.updateGui("treindata", id, arg);
                            int[] reizigerids = treinen.getTrein(i).getHuidigePassagiersLijst();
                            //Reizigers status wordt geupdate naar "Onderweg naar bestemming"
                            main.updateGui("reiziger", reizigerids, new int[] {0});
                            treinen.startTrein(treinen.getTrein(i));
                        }
                    }
                    else if (!treinen.getTrein(i).rijdend()) {
                        treinen.startTrein(treinen.getTrein(i));
                    }
                }
            }
        }
    }
}
