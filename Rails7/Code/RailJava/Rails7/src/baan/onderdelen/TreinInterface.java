/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package baan.onderdelen;
import java.io.*;
import java.util.Observable;
/**
 * Deze klasse maakt het mogelijk om fiezieke en virtuele treinen in het programma te gebruiken,
 * afhankelijk of de baan is aangesloten of niet
 * @author Team 7
 */
public class TreinInterface extends Observable{
    
    /**
     * Deze methode wijzigd de snelheid van de trein
     * @param speed de nieuwe snelheid voor deze trein
     * @throws java.io.IOException
     */
    public void setSnelheid(byte speed) throws IOException{
        
    }
    
    /**
     * Deze methode retourneert het ID van deze trein
     * @return id De ID van de trein
     */
    public int getTreinNummer(){
        return 0;
    }
    
    /**
     * Deze methode geeft de trein een nieuw ID
     * @param treinNummer het nieuwe trein nummer
     */
    public void setTreinNummer(int treinNummer){
        
    }
    
    /**
     * Deze methode start de trein
     * @throws baan.onderdelen.TreinException
     * @throws java.io.IOException
     */
    public void start() throws TreinException , IOException
    {
        
    }
    
    /**
     * Deze methode stopt de trein
     * @throws baan.onderdelen.TreinException
     * @throws java.io.IOException
     */
    public void stop() throws TreinException , IOException{
        
    }
}
