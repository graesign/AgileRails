

public class Sector {
	public int id;
	public Trein trein;

	
	public Sector(int id){
		this.id=id;
		trein=null;
	}
	public boolean vrij(){
		if (trein==null)
			return true;
		else return false;
	}
	
	

}
