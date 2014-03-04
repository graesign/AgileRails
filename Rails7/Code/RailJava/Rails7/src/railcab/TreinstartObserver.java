/*
 * TreinstartObserver.java
 *
 * Created on 13 december 2007, 14:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package railcab;
import baan.onderdelen.TreinInterface;
import baan.SimBaan;
import baan.BaanInterface;
import data.Trein;
import java.util.Observable;
import java.util.Observer;

/**
 * Deze klasse regelt de situatie als er een trein die moet vertrekken stilstaat op een station,
 * en er een tweede trein stilstaat voor de trein. 
 * @author Team 7
 */
public class TreinstartObserver implements Observer {
    Trein trein;
    TreinInterface trein2;
    TreinController treincontroller;
    BaanInterface baan;
    
    /** Creates a new instance of TreinstartObserver */
    public TreinstartObserver(TreinController treincontroller, BaanInterface baan, Trein trein, Trein trein2) {
        this.treincontroller = treincontroller;
        this.trein = trein;
        this.trein2 = baan.getTrein(trein2.getId());
        this.baan = baan;
        this.trein2.addObserver(this);
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
        trein2.deleteObserver(this);
        treincontroller.startTrein(trein);
    }
}
