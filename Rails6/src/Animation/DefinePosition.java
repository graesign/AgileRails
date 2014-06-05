/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Animation;
import main.*;
/**
 *
 * @author Almo-Werk
 */
public class DefinePosition {
    
    public int sid;
    ReisBeheer rb;
    TreinBeheer tb;
    public double positie, angle;
    
    
    public void calculatePosition(){
        CabAnimation cabAnime = new CabAnimation(sid, rb, tb);
		// Zorgen dat positie altijd lager is als 100%
		if (positie > 100)
			positie = positie % 100;
		// Voortgang berekenen
		cabAnime.localiseerCabAnime();

		// Bochten berekenen
		cabAnime.turnCabAnime();

		// Poll sensor
		cabAnime.pollSensor();
    }
    
//       public void cabIsLinks() {
//        // CabAnimation is links
//        if (ry > 327 && ry < 333) {
//            checkWissel(7);
//        }
//        wOffset(4);
//        rx = 81 + pl;
//        ry = 405 - ((379 / 12.5) * (positie - 87.5));
//    }
//
//    public void cabIsBeneden() {
//        // CabAnimation is onder
//        rxoff = (int) (Math.round(rx) % 350);
//        if (rxoff > 47 && rxoff < 53) {
//            rxnr = (int) (7 - Math.round((rx - 180) / 350));
//            checkWissel(rxnr);
//        }
//        wOffset(3);
//        rx = 1174 - ((1092 / 37.5) * (positie - 50));
//        ry = 405 + pl;
//    }
//
//    public void cabIsRechts() {
//        // CabAnimation is rechts
//        if (ry > 117 && ry < 123) {
//            checkWissel(3);
//        }
//        wOffset(2);
//        rx = 1174 - pl;
//        ry = 26 + ((379 / 12.5) * (positie - 37.5));
//    }
//
//    public void cabIsBoven() {
//        // CabAnimation is boven
//        rxoff = (int) (Math.round(rx) % 350);
//        if (rxoff > 177 && rxoff < 183) {
//            rxnr = (int) Math.round((rx - 180) / 350);
//            checkWissel(rxnr);
//        }
//        wOffset(1);
//        rx = 81 + ((1092 / 37.5) * positie);
//        ry = 26 + pl;
//    }
//

//	}
//        
}
