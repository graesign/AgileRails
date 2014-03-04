import java.util.LinkedList;
public class Halte
{
    Wissel wissel = new Wissel();
   
    int id;
    public LinkedList<Reis> reizen;
    int tijd;
    int wachtenden;
    
    public Halte(int id){
        
        reizen = new LinkedList<Reis>();
        this.id=id;
    }
    
    
    
    public int getId(){
        return id;
    }
    
    void addReis(Reis reis){
        //reis.setStatus(1);
        if (reis.gereserveerd()){
        reizen.addFirst(reis);
        }
        
        else{
        	reizen.addLast(reis);
        }
         System.out.println("reis aan halte "+id+" toegevoegd");
    	
    }
    
    public void open(boolean op){
    	wissel.set(op);
    }
   
    
    public Reis getEersteReis(){
        if(reizen.isEmpty()) return null;
        if(reizen.size()==1) return (Reis)reizen.getFirst();
        Reis r0=(Reis)reizen.getFirst();
        Reis r1=(Reis)reizen.get(1);
        if(!r0.gereserveerd() && r1.gereserveerd())
        return r1;
        return r0;
    
    }
    
    public int getAantalWachtenden(){
        return reizen.size();
    }
        
    
        
    
    

    
     
}
