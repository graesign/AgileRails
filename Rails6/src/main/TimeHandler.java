/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import main.Trein;

/**
 *
 * @author Almo
 */
public class TimeHandler implements ActionListener {
     public Reis reis;
    boolean rijdt, inHalte;
    public int wtijd;
    public Timer timer;
    public int id;
    public int positie;
    public byte status=0;
    //public int snelheid;
   
    public int bestemming;
    
        public void actionPerformed(ActionEvent e){
        	Trein trein = new Trein();
            
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
                trein.timer.stop();
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
