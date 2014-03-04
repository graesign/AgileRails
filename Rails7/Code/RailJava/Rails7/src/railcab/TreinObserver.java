/*
 * TreinObserver.java
 *
 * Created on 13 december 2007, 11:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package railcab;
import baan.BaanInterface;
import baan.SimBaan;
import baan.onderdelen.TreinInterface;
import data.Trein;
import java.util.Observer;
import java.util.Observable;
import java.io.*;

/**
 * Deze klasse regelt de situatie als er een trein stilstaat op een station,
 * en er een tweede trein aankomt.
 * @author Team 7
 */
public class TreinObserver implements Observer{
    Trein trein;
    Trein trein2;
    BaanInterface baan;
    
    /** Creates a new instance of TreinObserver */
    public TreinObserver(Trein trein, Trein trein2, BaanInterface baan) {
        this.trein = trein;
        this.trein2 = trein2;
        this.baan = baan;
        this.baan.getTrein(trein2.getId()).addObserver(this);
    }
    
    /**
     * Deze functie wordt aangeroepen zodra de trein op het station 
     * Langs de volgende controlepost rijdt. 
     * De volgende trein gaat dan weer rijden
     * @param object De trein die al op het station stond
     * @param message Extra data voor gebruik in de functie
     */
    public void update(Observable object, Object message)
    {
        try{
            baan.getTrein(trein.getId()).start();
        }catch(Exception e){}
        baan.getTrein(trein2.getId()).deleteObserver(this);
    }   
}