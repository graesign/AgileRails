package main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Almo-Werk
 */
public class TimerHandler implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    public Reis reis;
    boolean rijdt, inHalte;
    public int wtijd;
    public int id;
    public int positie;
    public byte status = 0;
    public Timer timer;
    public ReisBeheer rb;
     public TreinBeheer tb;

    public Trein trein = new Trein(id, rb, tb);
    
    //public int snelheid;
    public void actionPerformed(ActionEvent e) {
    
        			if(wtijd>0){
        				wtijd--;
        			}
        			if((wtijd==0&&inHalte) && trein.positie%10==0){
        				inHalte=false;
     //   				System.out.println("bij de timer is de pos: "+positie+" trein "+id);
        				trein.timer.stop();
        				trein.sen1(trein,positie+1);
        				
        			}
        			else if((wtijd==0&&inHalte) && (trein.positie%10==1)){
        				inHalte=false;
        			//	System.out.println("bij de timer is de pos: "+positie+" trein "+id);	
        				this.timer.stop();
        				trein.sen2(trein,positie+1);
        			}
        			
        			else if(trein.positie%10==2){
        				
        				trein.timer.stop();
        				trein.sen3(trein,positie+1);
        			}
        			else if(trein.positie%10==3){
        				
        				trein.timer.stop();
        				
        				trein.sen3(trein,positie);
        			}
        			else if(trein.positie%10==4){
        				
        				trein.timer.stop();
        				if(positie==84){
        					trein.sen0(trein,10);
        				}
        				else{
        					trein.sen0(trein,positie+6);
        				}
        			}			
        }
}
