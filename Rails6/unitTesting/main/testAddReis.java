/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;
import java.util.ArrayList;
import java.util.LinkedList;
import junit.framework.TestCase;
import org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Almo-Werk
 */

public class testAddReis extends TestCase {
    // testAddReis
     public int id;
     public boolean gereserveerd;
     
     private int[] tijd={99,99};
     public int rx, ry, rxnr, rxoff;
     double pl;
     
     // testPositieBOven()
     public ReisBeheer rb;
     public TreinBeheer tb;
     public double positie, angle;
     
     public boolean zichtbaar, inwissel = false, running = false;
	
	public Sensor sensor;
	
	public int tpositie =8;
     
     
    @Test
    public void testAddReis(){
        LinkedList<Reis> reizen = new LinkedList<Reis>();
        Halte start = new Halte(id);
        Halte eind = new Halte(id);
        start.id = 4;
        eind.id = 6;
        
        Reis reis = new Reis(5,start, eind,1,tijd);
        reizen.addFirst(reis);
        Boolean expects = true;
        Boolean result = reizen.contains(reis);
        
       assertEquals(expects, result);
        } 
    
    @Test
    public void testTreinPositieBoven(){
        
        CabAnimation cabAnime = new CabAnimation(31, rb, tb);
        rx = 800;
        rxoff = (int) (Math.round(rx) % 350);
        int tempRxOff = rxoff;
        
        // checkWissel op wisselId is 178 t/m 182 wordt nooit uitgevoerd + wisselId bestaat niet.. 
        if (rxoff > 177 && rxoff < 183) {
            rxnr = (int) Math.round((rx - 180) / 350);
            cabAnime.checkWissel(tempRxOff);
        }
        cabAnime.wOffset(1);
        rx = (int) (81 + ((1092 / 37.5) * positie));
        ry = 26 + (int)pl;
        
        int expects = 100;
        int result  = rxoff;
        System.out.println(rxoff);
        assertEquals(expects, result);
    }
    @Test
    public static ArrayList<Reis> TestGetActReizen(){
       LinkedList<Reis> reizen = new LinkedList<Reis>();
        ArrayList<Reis> ar =new ArrayList<Reis>();
        
        Halte start = new Halte();
        Halte eind = new Halte();
        
        eind.id = 6;
        start.id = 2;
        
        for(Reis r:reizen){
            if(r.getStatus()>0&&r.getStatus()<3)
            ar.add(r);
            
        Boolean expects = true;
        Boolean result  = ar.contains(r);
        System.out.println("printje " + r);
        assertEquals(expects, result);
        }
        return ar;  
    }
}
    

