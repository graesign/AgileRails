/*
 * Reiziger.java
 *
 * Created on 19 November 2007, 11:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package data;

/**
 *
 * @author Speedy
 */
public class Reiziger {
    private static int idteller;
    private int id;
    private int vertrekpunt;
    private int eindpunt;
    private int eindpuntBuitenland;
    private int vertrektijd;
    private Reiziger previous = null,next = null;
       
   /**
    * Hier wordt een nieuw object van reiziger aangemaakt
    * @param vertrekpunt het vertrekpunt van de reiziger
    * @param eindpunt het eindpunt van de reiziger
    * @param vertrektijd de gewenste vertrektijd van de reiziger
    */  
    public Reiziger(int vertrekpunt, int eindpunt, int vertrektijd, int halteBuitenland) {
        
        id = idteller;
        idteller++;
        
        this.vertrekpunt = vertrekpunt;
        this.eindpunt = eindpunt;
        this.eindpuntBuitenland = eindpuntBuitenland;
        this.vertrektijd = vertrektijd;
    }
    
    public Reiziger(int vertrekpunt, int eindpunt, int vertrektijd) {
        this(vertrekpunt, eindpunt, vertrektijd, 0);
    }
    
    public int getId()
    {
        return id;
    }
    
    public int getVertrekTijd()
    {
        return vertrektijd;
    }
    
    public Reiziger getNext()
    {
        return next;
    }
    
    public int getEindpunt()
    {
        return eindpunt;
    }
    
    public int getEindpuntBuitenland()
    {
        return eindpuntBuitenland;
    }
    
    public int getVertrekpunt()
    {
        return vertrekpunt;
    }
    
    public void setVertrekpunt(int vertrekpunt)
    {
        this.vertrekpunt = vertrekpunt;
    }
    
    public void setEindpunt(int eindpunt)
    {
        this.eindpunt = eindpunt;
    }
    
    public void setEindpuntBuitenland(int eindpuntBuitenland)
    {
        this.eindpuntBuitenland = eindpuntBuitenland;
    }
    
    public void setVertrektijd(int vertrektijd)
    {
        this.vertrektijd = vertrektijd;
    }
    
    public void setNext(Reiziger next)
    {
        this.next = next;
    }
    
    public Reiziger getPrevious()
    {
        return previous;
    }
    public void setPrevious(Reiziger previous)
    {
        this.previous = previous;
    }
    
}
