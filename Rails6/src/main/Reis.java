package main;


import connection.Listen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;


public class Reis
{
///////////////////////////////////////////////////////ATRIBUTEN/////////////////////////////////////////////// 
   //private Calendar cal = new GregorianCalendar();
   private Timer timer;
   private int aantal;
   private Halte startHalte;
   public Halte bestemmingHalte;
   private int halteBuitenland;
   private int[] tijd={99,99};
   public int id;
   
   public int status;
   
   //positie
   //private Object positie;
   
  private boolean gereserveerd;
   
  
   //in seconden
  public int wachttijd=0;
  public int reistijd=0;
   
 
   
   
    // tijd wordt ingevoer in de volgende formaat: {u,m} uren in 24fotmaat, dus niet hoger dan 23uur en 59minuten! anders loopt die vast
 
    public Reis(int aantal, Halte start, Halte eind, int halteBuitenland, int[] tijd)    {
        this.aantal=aantal;
        this.startHalte = start;
        this.bestemmingHalte = eind;
        this.halteBuitenland = halteBuitenland;
        this.tijd = tijd;
        System.out.println("nieuw reis aangemaakt\n" + aantal + " passagiers\n starthalte="+startHalte.getId() +
        "\n eindhalte=" + bestemmingHalte.getId() + "\n starttijd=" + getStartTijd()); 
        this.timer = new Timer(1000,new TimerHandler());
        this.gereserveerd=true;
//        startReis();
    }
    
    public Reis(int aantal, Halte start, Halte eind, int halteBuitenland){
    	this.aantal=aantal;
        this.startHalte = start;
        this.bestemmingHalte = eind;
        this.halteBuitenland = halteBuitenland;
        this.status=1;
        this.timer = new Timer(1000,new TimerHandler());
//        startReis();
        this.gereserveerd= false;
    }
    
    //////////////////////////////////////////////////////////GETTERS///////////////////////////////////////////////////
    public String getStartTijd(){
        String t="";
        if(tijd[0]<10) t=t+"0";
        t=t+tijd[0]+":";
        if(tijd[1]<10) t=t+"0";
        t=t+tijd[1];
        return t;
    }
    public boolean gereserveerd(){
        return gereserveerd;
    }
    
    public int getAantal(){
    	return aantal;
    }
    
    public int getStatus(){
        return status;
    }
    
    public Halte getStart(){
        return startHalte;
    }
    
     public Halte getBestemming(){
        return bestemmingHalte;
    }
   
    public int getWachtTijd(){
        return wachttijd;
    }
    
    public int getReistijd(){
        return reistijd;
    }
    
    public int getID(){
    	return id;
    }
    //////////////////////////////////////////////////////////SETTERS///////////////////////////////////////////////////
    
    
    public void setStatus(int st){
        status=st;
    }
    
    
//    void startReis(){
//        
//        timer.start();
//        
//    }
    
    void stopReis(){
       
        timer.stop();
        System.out.println("reistijd="+reistijd);
        System.out.println("wachttijd="+wachttijd);
        System.out.println("Reis beeindigt");
        Listen.addCab(aantal, halteBuitenland + 1);
    }
    
    public Reis getThis(){
    	return this;
    }
      //////////////////////////////////////////////////CONTROLE////////////////////////////////////////////////////////
    
                    //TIMERHANDLER//
                    //om de seconde voert die de in houd hiervan in
    class TimerHandler implements ActionListener{

        public void actionPerformed(ActionEvent e){
              Calendar c=new GregorianCalendar();
            int uur = RailsApp.uur; 
            int min = RailsApp.min;
             if(uur==(tijd[0]) && (min==(tijd[1]))) {
                if(status==0){
                	setStatus(1);
                	ReisBeheer.haltes.get(startHalte.id - 1).addReis(getThis());
                }
            }
            //wachttijd en reistijd bijhouden
            if(status==1)
            wachttijd++;
            if(status==2)
            reistijd++;
            if (status==3) stopReis();
            
        }
    
    
    
    }
}
    
        
  

