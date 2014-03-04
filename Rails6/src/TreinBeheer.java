import java.util.ArrayList;

import javax.swing.Timer;




public class TreinBeheer{
    public ReisBeheer reisBeheer;
	//actieve treinen
    public static ArrayList<Trein> treinen = new ArrayList<Trein>();
	

	public static ArrayList<Trein> alleTreinen = new ArrayList<Trein>();
	RailScherm rs;
	
	public Sector[] sector = new Sector[85];
	Timer timer;
	
	
	public TreinBeheer(ReisBeheer reisBeheer,RailScherm rs) {
		
		//super.start();
		this.rs=rs;
		this.reisBeheer = reisBeheer;
    	for (int i = 0 ; i <10; i++){
    		  // Cabs aanmaken
    		  alleTreinen.add(new Trein(i,reisBeheer,this));
    		  alleTreinen.get(i).status=-1;
    		  alleTreinen.get(i).zichtbaar =false;
          	  //alleTreinen.get(i).start();
    	}
    	for (int i = 0 ; i < 85; i++){
    		sector[i]=new Sector(i);
    	}
    	
    treinBij();
    	
	}
	
	Trein trein;
	
	int ttid,ssid;
	public void sensorBericht(int tid, int sid){	
		
		// hierdoor wordt voorkomen dat bij identieke berichten achter elkaar actie wordt genomen
		if (!(this.ssid==sid&&this.ttid==tid)) {
			this.ssid=sid;
			this.ttid=tid;
			trein=treinen.get(tid);
			
			for(int i=0;i<sector.length;i++){
				if(sector[i].trein!=null)
				System.out.println("in sector "+i+" rijdt trein "+sector[i].trein.id);
			}
			//als een trein bij een wissel is word hier gecheckt of dat zijn bestemming is, bij ja wordt de wissel geopent
			if((sid%10)==0){
				treinen.get(tid).sen0(trein,sid);
			}
			
			//als een trein de eerste sensor in een halte triggert, wordt de wissel meteen gesloten
			if(sid%10==1){
				treinen.get(tid).sen1(trein,sid);		
			}
			if(sid%10==2){
				treinen.get(tid).sen2(trein,sid);		
			}
			
			if(sid%10==3){
				
			
				treinen.get(tid).sen3(trein,sid);	
			}
			//System.out.println("in sector "+trein.positie+" rijdt trein "+trein.id);
			//System.out.println("*****************");
			//trein.positie=sid;
			System.out.println("sensor: "+sid+" trein: "+tid);
			for(int i=0;i<sector.length;i++){
				//if(sector[i].trein!=null)
				//System.out.println("in sector "+i+" rijdt trein "+sector[i].trein.id);
			}
			//System.out.println("////////////////////");
			if((reisBeheer.getReizen(1).size() > (treinen.size()*3))&&treinen.size()<alleTreinen.size() ) {
	        	treinBij();
	        }
			if(sid==83 &&reisBeheer.getReizen(1).size() < treinen.size()*3 && trein.reis==null&& trein.equals(treinen.get(treinen.size()-1)) && treinen.size()>1){
				treinAf();
			}
			
		}
			//als de volgende sector bezet is, stop
			
			
	}
	public void treinBij(){
		if(sector[84].trein==null){
			System.out.println("trein toegevoed");
			System.out.println( "er zijn "+ alleTreinen.size()+" alle treinen");
			Trein t=alleTreinen.get(treinen.size());
			treinen.add(t);
			sector[84].trein= t;
			t.positie=84;
			t.status=0;
			treinen.get(t.id).zichtbaar=true;
			treinen.get(t.id).rij(true);
			
		}
	}
	public void treinAf(){
		System.out.println("trein verwijderd");
		Trein t=treinen.get(treinen.size()-1);
		t.rij(false);
		t.positie=0;
		trein.status=-1;
		treinen.remove(t);
		t.setZichtbaarheid(false);
		sector[84].trein=null;
		System.out.println("--treinAf");
	}   
    
}
