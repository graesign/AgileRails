/*
 * Trein.java
 *
 * Created on 19 November 2007, 11:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package data;

/**
 *
 * @author Team 7
 */
public class Trein {
    
    // variabelen gebruikt voor huidige acties
    int positie;
    boolean rijdend;
    boolean doettaak;
    public int geenreservering;
    public boolean bezet;
    int passagieraantal;
    Reiziger[] passagierslijst = new Reiziger[25];
    int bestemming;
    int id;
    
    // variabelen gebruikt voor de reservering van de volgende taak
    int volg_passagieraantal;
    Reiziger[] volg_passagierslijst = new Reiziger[25];
    int volg_vertrekpunt;
    int volg_bestemming;
    boolean heeftvolgtaak;
    int vertrektijd;
    int maxvertrektijd;
    
    /** Maakt een nieuw Trein object */
    public Trein(int positie, int id) {
        this.id = id;
        rijdend = false;
        this.positie = positie;
        doettaak = false;
        heeftvolgtaak = false;
        geenreservering = -1;
        volg_vertrekpunt = -1;
        volg_bestemming = -1;
    }
    
    public void setId(int id){
        this.id = id;
    }
    /** Methode die een taak aan de trein geeft */
    public void setVolgTaak(int vertrekpunt, int bestemming, int vertrektijd) {
        volg_vertrekpunt = vertrekpunt;
        volg_bestemming = bestemming;
        this.vertrektijd = vertrektijd;
        maxvertrektijd = vertrektijd + 10;
        heeftvolgtaak = true;
    }
    
    public void setPassagier(Reiziger passagier) {
        volg_passagierslijst[volg_passagieraantal] = passagier;
        volg_passagieraantal++;
    }
    
    /** method called when the train is at the source of his next task,
     * setting the "next task" variables to the variables for the currently to perform task */
    public void setTaak() {
        bestemming = volg_bestemming;
        passagieraantal = volg_passagieraantal;
        for (int i = 0; i < passagieraantal; i++) {
            passagierslijst[i] = volg_passagierslijst[i];
            volg_passagierslijst[i] = null;
        }
        volg_passagieraantal = 0;
        doettaak = true;
        heeftvolgtaak = false;
        volg_bestemming = -1;
        volg_vertrekpunt = -1;
        bezet = false;
        geenreservering = positie + 1;
    }
    
    public void gaNaar(int baandeel) {
        this.bestemming = baandeel;
        doettaak = true;
        this.rijdend = true;
    }
    
    /** method actived after the task if is completed */
    public void setGeenTaak() {
        doettaak = false;
        passagieraantal = 0;
    }
    
    /** method setting the position of the train */
    public void setPositie(int positie) {
        this.positie = positie;
    }
    
    public void setRijdend(boolean status) {
        rijdend = status;
    }
    
    public void setVertrekTijd(int vertrektijd) {
        this.vertrektijd = vertrektijd;
    }
    
    /** method returning the position of the train */
    public int getPositie() {
        return positie;
    }
    
    public int getVertrekTijd() {
        return vertrektijd;
    }
    
    public int getMaxVertrekTijd() {
        return maxvertrektijd;
    }
    
    public int getVolgPassagierAantal() {
        return volg_passagieraantal;
    }
    
    public int getPassagierAantal() {
        return passagieraantal;
    }
    
    public int getBestemming() {
        return bestemming;
    }
    
    public int getVolgVertrekpunt() {
        return volg_vertrekpunt;
    }
    
    public int getId() {
        return id;
    }
    public boolean doetTaak() {
        return doettaak;
    }
    
    public boolean heeftVolgTaak() {
        return heeftvolgtaak;
    }
    
    public boolean rijdend() {
        return rijdend;
    }
    
    public int[] getHuidigePassagiersLijst() {
        int[] passagierids = new int[passagieraantal];
        
        for(int i = 0; i < passagieraantal; i++) {
            passagierids[i] = passagierslijst[i].getId();
        }
        return passagierids;
    }
}
