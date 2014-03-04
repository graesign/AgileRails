/*
 * LinkedList.java
 *
 * Created on 19 november 2007, 12:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package railcab;
import data.Reiziger;
/**
 * Deze klassen koppelt de reizigers in een lineair gelinkte lijst aan elkaar.
 * Ook kan je met deze klassen de lijst beheren en elementen zoeken in de lijst.
 * @author Team 7
 */
public class LinkedList {
    
    
    private Reiziger header;
    private int aantalreizigers;
    
    /** Maakt een nieuwe instantie van de klasse LinkedList aan */
    public LinkedList() 
    {
        header = new Reiziger(0,0,0);
    }
    
    /**
     * Hier start de sequens om een nieuwe reiziger toe te voegen aan de lijst
     * @param nieuw De reiziger die toegevoegd moet worden
     */ 
    protected void nieuwReiziger(Reiziger nieuw)
    {   
        //Voeg reiziger toe als eerste element als de lijst leeg is
        if(header.getVertrekTijd() == 0)
        {
            header = nieuw;
        }
        else
        {
            //Voeg Reizger toe als er nog maar 1 reiziger in de lijst staat
            if(header.getNext() == null)
            {
                addSecond(nieuw);
            }
            //Voeg reizger toe als er twee of meer reizigers in de lijst staan.
            else
            {
                addBetween(nieuw);
            }
        }
        aantalreizigers++;
    }
    
    /**
     * Dit wordt aangeroepen als er 1 reiziger in de lijst staat.
     * @param nieuw De reiziger die toegevoegd moet worden
     */ 
    private void addSecond(Reiziger nieuw)
    {
        //voeg reiziger vooraan de lijst toe
        if(header.getVertrekTijd() <= nieuw.getVertrekTijd() && header.getId() > nieuw.getId())
        {
            Reiziger temp = header;
            header = nieuw;
            header.setNext(temp);
            temp.setPrevious(header);
        }
        //voeg reiziger achter aan de lijst toe
        else
        {
            header.setNext(nieuw);
            nieuw.setPrevious(header);
        }
    }
    
    /**
     * Dit wordt aangeroepen als er twee of meer reizigers in de lijst staan.
     * @param nieuw De reiziger die toegevoegd moet worden
     */ 
    private void addBetween(Reiziger nieuw){
        Reiziger temp = zoekTijd(nieuw.getVertrekTijd());
        //Voeg reiziger tussen twee reizigers toe
        if(temp != header && temp != null)
        {
            nieuw.setPrevious(temp.getPrevious());
            nieuw.setNext(nieuw.getPrevious().getNext());
            nieuw.getPrevious().setNext(nieuw);
            nieuw.getNext().setPrevious(nieuw);
        }
        else
        {
            //voeg reiziger aan het eind van de lijst toe
            if(temp == null)
            {
                nieuw.setPrevious(getLast());
                getLast().setNext(nieuw);
            }
            //voeg reiziger aan het begin van de lijst toe
            else
            {        
                Reiziger temp2 = header;
                header = nieuw;
                header.setNext(temp2);
                temp2.setPrevious(header);
            }
        }
    }
    
    /**
     * Deze methode retourneert het eerste element uit de lijst
     * @return Het eerste element uit de lijst
     */ 
    protected Reiziger getFirst(){
        return header;
    }
    
    /**
     * Deze methode retourneert het laatste element uit de lijst
     * @return Het laatste element uit de lijst
     */ 
    protected Reiziger getLast(){
        Reiziger temp = header;
        while(temp.getNext() != null){
            temp = temp.getNext();
        }
        return temp;
    }
    
    /**
     * Deze methode retourneert het aantal elementen in de lijst
     * @return Het aantal elementen in de lijst
     */ 
    protected int getAantal(){
        return aantalreizigers;
    }
    
    /**
     * Deze methode zoekt het eerste element uit de lijst met een vertrektijd die lager is dan de opgegeven tijd
     * @param tijd Een tijd in secondes (integer)
     * @return Reiziger meteen vertrektijd lager dan de meegegeven tijd, of null, als er niks wordt gevonden
     */ 
    protected Reiziger zoekTijd(int tijd){
       Reiziger temp = header;
       while(temp != null){
            if(temp.getVertrekTijd() < tijd)
               return temp;
            temp = temp.getNext();
       }
       return null;
    }
    
    /**
     * Deze methode zoekt en retourneert het eerste element met de opgegeven tijd
     * die het tegen komt
     * @param tijd Een tijd in secondes (integer)
     * @return Reiziger met de opgegeven vertrektijd, of null, als er niks wordt gevonden
     */ 
    protected Reiziger zoekTijdExact(int tijd){
        Reiziger temp = header;
       while(temp != null){
            if(temp.getVertrekTijd() == tijd)
               return temp;
            temp = temp.getNext();
       }
        return null;
    }
    
    /**
     * Deze methode zoekt en retourneert het eerste element met de opgegeven tijd
     * die het tegen komt
     * @param id Een ID in de vorm van een integer
     * @return Reiziger met de opgegeven ID, of null, als er niks wordt gevonden
     */ 
    protected Reiziger zoekID(int id){
       Reiziger temp = header;
       while(temp != null){
            if(temp.getId() == id)
               return temp;
            temp = temp.getNext();
       }
        return null;
    }
    /**
     * Deze methode verwijdert een reiziger met de opgegeven ID, indien die reiziger in de lijst staat 
     * @param id Een ID in de vorm van een integer.
     */ 
    protected void verwijderReiziger(int id){
        Reiziger temp = zoekID(id);
        
        if((temp.getPrevious() != null) && (temp.getNext() != null))
        {
            temp.getPrevious().setNext(temp.getNext());
            temp.getNext().setPrevious(temp.getPrevious());
        }
        else if((temp.getPrevious() == null) && (temp.getNext() != null))
        {
            header = temp.getNext();
            header.setPrevious(null);
        }
        else if((temp.getNext() == null) && (temp.getPrevious() != null))
        {
            temp.getPrevious().setNext(null);
        }
        else if((temp.getPrevious() == null)&& (temp.getNext() == null))
        {
            temp.setVertrektijd(0);
            temp.setVertrekpunt(0);
            temp.setEindpunt(0);
        }
        aantalreizigers--;
    }
    
    /** Deze methode print alle elementen in de lijst */ 
    public void print(){
        Reiziger temp = header;
        System.out.println("=====================================");
       while(temp != null){
            System.out.println("ID: " + temp.getId());
            System.out.println("Vertrektijd: " + temp.getVertrekTijd());
            if(temp.getNext() == null){
                System.out.println("next = null");
        }
        else{
            System.out.println("next = " + temp.getNext().getId());
        }
        
        if(temp.getPrevious() == null){
            System.out.println("prev = null");
        }
        else{
            System.out.println("prev = " + temp.getPrevious().getId());
        }
            System.out.println("=====================================");
            temp = temp.getNext();
       }
    }
}
