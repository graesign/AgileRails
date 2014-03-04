

import java.util.ArrayList;

public class ReisBeheer {
    public static ArrayList<Reis> reizen;
    public static ArrayList<Reis> treizen;
    public static ArrayList<Halte> haltes;
    int[] def = {99,99};
    TreinBeheer treinbeheer;
    

	public static int getWacht(int stat) {   //int station
		int wacht = 0;
			for (int i = 0; i < reizen.size(); i++) {
			if ((reizen.get(i).getStart().id) == (stat - 1)) {
				if (reizen.get(i).getStatus() == 0) {
					wacht++;
				}
			}
		}
		return wacht;
	}

    public ReisBeheer(TreinBeheer tb) {
        reizen=new ArrayList<Reis>();
        treizen=new ArrayList<Reis>();
        haltes=new ArrayList<Halte>();
        for(int i=1;i<9;i++){
            haltes.add(new Halte(i));
        }
 
   
        int[] tijd={99,99}; //add reizen voor test
        
        /*for(int i=0;i<8;i++){
        	addReis(5,(8-i),i+1,tijd);
        }
        */
    }
    
    public static void addReis(int aantal, int start, int bestemming, int[] tijd){
        Reis r;
       
        if(tijd[0]==99&&tijd[1]==99){
            r=new Reis(aantal, haltes.get(start), haltes.get(bestemming));
            reizen.add(r);
            treizen.add(r);
            haltes.get(start).addReis(r);
            
        }
        else{
            r=new Reis(aantal,haltes.get(start), haltes.get(bestemming),tijd);
            reizen.add(r);
            treizen.add(r);
        }
        
       

        
    }
    public  static double gemReisttijd(){
    	int rt=0;
    	int aantal=0;
    	for(Reis r:reizen){
    		if(r.status>1 && r.status < 3){
    			rt=rt+r.reistijd;
    			aantal++;
    		}
    	}
    	if(rt>0&&aantal>0)
    	return rt/aantal;
    	else return 0;
    
    	
    }
    
    public static double gemWachttijd(){
    	int wt=0;
    	int aantal=0;
    	for(Reis r:reizen){
    		if(r.status>0 && r.status <3){
    			wt=wt+r.wachttijd;
    			aantal++;
    		}
    	}
    	if(wt>0&&aantal>0){
    	return wt/aantal;
    	}else{
    		return 0;
    	}
    }
    
    public Halte getHalte(int id){
        return haltes.get(id);
    }
    
    
    public void addHalte(int id){
        Halte h=new Halte(id);
        haltes.add(h);
    }
    

    //reizen die wachten en in trein zitten
    public static ArrayList<Reis> getActieveReizen(){
        ArrayList<Reis> ar=new ArrayList<Reis>();
        for(Reis r:reizen){
            if(r.getStatus()>0&&r.getStatus()<3)
            ar.add(r);
        }
        
        
        return ar;
    }
    

    
    //geeft alle reizen met deze status
    public static ArrayList<Reis> getReizen(int status){
        ArrayList<Reis> ar=new ArrayList<Reis>();
        
        for(int i=0;i<reizen.size();i++){
            if((reizen.get(i)).getStatus()==status)
            ar.add(reizen.get(i));
            
            
        }
       // System.out.println("reizen met status "+status+" zijn:"+ar.size());
        
        return ar;
    }
    //geeft de halte met de meeste wachtenden
    public Halte druksteHalte(){
        int aantal=0;
        Halte drukste =(Halte)haltes.get(0);
        for(int i=1;i<haltes.size();i++){
            Halte h= haltes.get(i);
            if(h.getAantalWachtenden()>aantal){
                drukste = h;
                aantal = h.getAantalWachtenden();
            }
        }
        if(aantal==0) return null;
        //System.out.println("halte nr "+drukste.getId());
        return drukste;
    }
    
	public Halte langsteWachttijdHalte() {
		Halte h, langsteWachttijdHalte = null;
		int wachttijd = 0;
		for (int i = 0; i < haltes.size(); i++) {
			h = haltes.get(i);
			if (h.getEersteReis() != null)
				if (h.getEersteReis().getWachtTijd() > wachttijd) {
					wachttijd = h.getEersteReis().getWachtTijd();
					langsteWachttijdHalte = h;
				}
		}

		if (langsteWachttijdHalte != null)
			System.out.println("halte nr " + langsteWachttijdHalte.getId());
		return langsteWachttijdHalte;
	}

          
}
