import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


/**
 * Write a description of class Trein here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Trein extends Cab {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Reis reis;
    boolean rijdt, inHalte;
    public int wtijd;
    public Timer timer;
    public int id;
    public int positie;
    public byte status=0;
    //public int snelheid;
    
   
    public int bestemming;
   
   
  
    //public int reserveringen;
    
    TreinBeheer tb;
 
    public Trein(int sid, ReisBeheer rb,TreinBeheer tb) {
    	super(sid,rb,tb);
    	this.id=sid;
    	
    	this.tb=tb;
    	timer =  new Timer(300,new TimerHandler());
    	//timer.start();
		inHalte=false;
    	rijdt=super.running;
		reis=null;
		
		//this.id=sid
		// TODO Auto-generated constructor stub
	}
	
       
    
    public int getId(){
        return id;
    }
    
    //////////////////algoritme/////////////////////////////
    public void sen0(Trein trein,int sid){
		//System.out.println("halte "+sid/10+" heeft "+reisBeheer.haltes.get((sid/10)-1).reizen.size()+" reizen");
		//als de halte de bestemming is of hij is vrij en er zijn wachtenden in halte:
		if(this.bestemming*10==sid||(tb.reisBeheer.haltes.get((sid/10)-1).reizen.size()>0 && this.reis==null)){
			//test sector of die vrij is
			if(tb.sector[sid].trein==null){
				
				tb.reisBeheer.haltes.get((sid/10)-1).open(true);
				this.positie=sid;
				tb.sector[sid].trein=this;
				this.rij(true);
				//als het de eerste sensor is moet de laatste tb.sector vrijgemaakt worden 
				if(sid==10&& tb.sector[84].trein==this){
					tb.sector[84].trein=null;
					//tb.sector[83].trein=null;
				}
				else if(tb.sector[sid-6].trein==this){
					tb.sector[sid-6].trein=null;
					//tb.sector[sid-7].trein=null;
				}
				
			}
			//als de sector niet vrij is stopt die
			else if(this.positie!=sid){
				//System.out.println("trein "+this.id+" stopt omdat tb.sector "+sid+" is bezet  en sector nn "+this.positie+" is geblokkeert");
				this.rij(false);
				this.wacht();
			}
		}
		//als de halte niet de bestemming is:
		else{ 
			//getest of de parallelsector aan de halte vrij is
			if(tb.sector[sid+3].trein==null){
				this.rij(true);
				this.positie=sid+3;
				tb.sector[sid+3].trein=this;
				tb.reisBeheer.haltes.get((sid/10)-1).open(false);
				if(sid==10 && tb.sector[84].trein==this){
					tb.sector[84].trein=null;
					
				}
				else if(tb.sector[sid-6].trein==this) {
					tb.sector[sid-6].trein=null;
					
				}
				
			}
			
			else if(this.positie!=sid+3){
				this.rij(false);
				this.wacht();
				//System.out.println("trein "+this.id+"stopt omdat sector "+(sid+3)+" is bezet  en sector ss "+this.positie+" is geblokkeert");			
				}
			
	
			
		}
	}
	
	//als een this bij de uitstaphalte is:
	public void sen1(Trein trein,int sid){
		//System.out.println("sen1 methode");
		//tb.reisBeheer.haltes.get((sid/10)-1).open(false);
		//heeft de trein een reis in zich
		if(this.reis!=null){
		//	System.out.println("er zit een reis in");
			
			this.rij(false);
			
	    	this.status=0;
	    	
	    	this.reis.status=3;
	    	this.reis.stopReis();
	    	this.reis=null;
	    	this.bestemming=0;
	 //   	System.out.println("trein  wacht");
	    	this.wacht();
	    	
	    	
		}
		else if(tb.sector[sid].trein==null){
			this.rij(true);
			this.positie=sid;
			tb.sector[sid-1].trein=null;
			tb.sector[sid].trein=this;
		}
		else if(this.positie!=sid){
			this.rij(false);
			this.wacht();
		//	System.out.println("trein "+this.id+"stopt omdat sector "+sid+" is bezet  en sector "+this.positie+" is geblokkeert");
			}
		
		
		
		
		
		
		
	}
	public void sen2(Trein trein,int sid){

		if(tb.reisBeheer.haltes.get(((sid-2)/10)-1).reizen.size()>0 && this.reis==null ){
			//oppikken
			this.reis=tb.reisBeheer.haltes.get(((sid-2)/10)-1).reizen.removeLast();
			this.reis.status=2;
			this.rij(false);
			this.bestemming=this.reis.bestemmingHalte.id;
			//System.out.println("wactt"+this.inHalte);
			this.wacht();
		} else if(tb.sector[sid+1].trein==null &&tb.sector[sid].trein==null){
			this.rij(true);
			this.positie=sid;
			tb.sector[sid-1].trein=null;
			tb.sector[sid].trein=this;
			tb.sector[sid+1].trein=this;
			//System.out.println("wactt"+this.inHalte);
		} else if(this.positie!=sid){
			this.rij(false);
			this.wacht();
		//	System.out.println("trein "+this.id+"stopt omdat sector "+sid+" is bezet  en sector "+this.positie+" is geblokkeert");		
		}
	}
	public void sen3(Trein trein,int sid){
		if(tb.sector[sid+1].trein==null){
			tb.sector[sid].trein=null;
			tb.sector[sid-1].trein=null;
			this.rij(true);
			tb.sector[sid+1].trein=this;
			this.positie=sid+1;
			
			
		}
		// deze om te voorkomen dat bij eventueel snelle 2e aanroep de trein niet gestopt worden, want hij is net daarin gezet
		else if(this.positie!=(sid+1) )
			{
			this.rij(false);
			//System.out.println("trein "+this.id+"stopt omdat sector "+(sid+1)+" is bezet  en sector "+this.positie+" is geblokkeert");
			this.wacht();
			}
		
	}	
    //////////////////einde algoritme///////////////////////

   public void wacht(){
	   timer.start();
	   wtijd=2;
	   inHalte=true;
   }

  
    public Trein getThis(){
    	return this;
    
    }
  
    public void addReis(Reis reis){  
          this.reis= reis;
          this.bestemming=rb.haltes.indexOf(reis.getBestemming());
 //         this.bestemming.addFirst(bestemming);
      }
      
    public void rij(boolean r){
    	//System.out.println("trein "+id+" rijdt "+r);
        if (r){
        	super.start();
        	rijdt=true;
        }
        else{
        	super.stop();
        	rijdt=false;
        }
    }
    
    
    public void setPos(int pos){
        positie = pos;
    }
    
    public Reis getReis(){
    	return reis;
    
    }
    public void laadUit(){
    	
    }
    public void removeReis(){
        reis=null;
        status=0;
    }
class TimerHandler implements ActionListener{

    	
        public void actionPerformed(ActionEvent e){
        		
        			if(wtijd>0){
        				wtijd--;
        			}
        			if((wtijd==0&&inHalte) && getThis().positie%10==0){
        				inHalte=false;
     //   				System.out.println("bij de timer is de pos: "+positie+" trein "+id);
        				getThis().timer.stop();
        				sen1(getThis(),positie+1);
        				
        			}
        			else if((wtijd==0&&inHalte) && (getThis().positie%10==1)){
        				inHalte=false;
        			//	System.out.println("bij de timer is de pos: "+positie+" trein "+id);	
        				getThis().timer.stop();
        				sen2(getThis(),positie+1);
        			}
        			
        			else if(getThis().positie%10==2){
        				
        				getThis().timer.stop();
        				sen3(getThis(),positie+1);
        			}
        			else if(getThis().positie%10==3){
        				
        				getThis().timer.stop();
        				
        				sen3(getThis(),positie);
        			}
        			else if(getThis().positie%10==4){
        				
        				getThis().timer.stop();
        				if(positie==84){
        					sen0(getThis(),10);
        				}
        				else{
        					sen0(getThis(),positie+6);
        				}
        			}			
        }
	}    
}