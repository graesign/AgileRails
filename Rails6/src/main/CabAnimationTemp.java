///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package main;
//
//import javax.swing.ImageIcon;
//import main.ReisBeheer;
//import main.Sensor;
//import main.TreinBeheer;
//
///**
// *
// * @author Almo
// */
//public class CabAnimationTemp extends ImageIcon {
//
//    public TreinBeheer TreinBeheer;
//    public ReisBeheer reisBeheer;
//    public Sensor sensor;
//
//    public int sensorId;
//    public boolean zichtbaar;
//    public boolean running = false;
//    public boolean inwissel = false;
//    public double positie, angle;
//
//    public int rxnr, rxoff;
//    public double pl, rx, ry;
//    public int x, y;
//
//    public CabAnimationTemp(int sid, ReisBeheer rb, TreinBeheer tb) {
//        super("./cab.gif");
//        this.TreinBeheer = tb;
//        sensorId = sid;
//        this.reisBeheer = rb;
//        sensor = new Sensor(sid, this); // this from main.Sensor.java
//        setPositie(0);
//        setZichtbaarheid(false);
//    }
//    int sens[]={10,11,12,13,20,21,22,23,30,31,32,33,40,41,42,43,50,51,52,53,60,61,62,63,70,71,72,73,80,81,82,83};
//    
//    public void start() {
//        running = true;
//    }
//
//    public void stop() {
//        running = false;
//    }
//
//    public void run() {
//        setPositie(positie + 0.05);
//    }
//
//    public void setZichtbaarheid(boolean z) {
//        zichtbaar = z; // cabview
//    }
//
//    public void setPositie(double p) {
//        positie = p;
//        defineCabPosition();
//    }
//
//    private void defineCabPosition() {
//
////            CabMovement cabMovement = new CabMovement();
//        // Zorgen dat positie altijd lager is als 100%
//        if (positie > 100) {
//            positie = positie % 100;
//        }
//        // Voortgang berekenen
//        localiseerCabAnime();
//
//        // Bochten berekenen
//        turnCabAnime();
//        // Poll sensor
//        pollSensor();
//    }
//
//    private void checkWissel(int wid) {
//        if (reisBeheer.haltes.get(wid).wissel.status()) {
//            inwissel = true;
//        } else {
//            inwissel = false;
//        }
//    }
//
//    private void wOffset(int pos) {
//        if (pos == 1) {
//            // Cabs zijn boven
//            if (!(rxoff >= 48 && rxoff < 178) && inwissel) {
//                double rxoff2 = rx - (180 + (rxnr * 350));
//                if (rxoff2 < 80.0) {
//                    pl = Math.sin(rxoff2 / 73) * 50;
//                } else if (rxoff2 >= 80 && rxoff2 <= 125) {
//                    pl = 43;
//                } else if (rxoff2 > 125 && rxoff2 < 200) {
//                    pl = Math.sin(1 - ((rxoff2 - 125) / 73)) * 50;
//                } else {
//                    pl = 0;
//                }
//
//            } else {
//                inwissel = false;
//                pl = 0;
//            }
//        } else if (pos == 3) {
//            // Cabs zijn onder
//            if (!(rxoff >= 52 && rxoff < 178) && inwissel) {
//                double rxoff2 = rx - (180 + ((6 - rxnr) * 350));
//                if (rxoff2 > 0 && rxoff2 < 80.0) {
//                    pl = Math.sin(rxoff2 / 73) * 50;
//                } else if (rxoff2 >= 80 && rxoff2 <= 125) {
//                    pl = 43;
//                } else if (rxoff2 > 125 && rxoff2 < 200) {
//                    pl = Math.sin(1 - ((rxoff2 - 125) / 73)) * 50;
//                } else {
//                    pl = 0;
//                }
//            } else {
//                inwissel = false;
//                pl = 0;
//            }
//        } else if (pos == 2 || pos == 4) {
//            // Cabs zijn links of rechts
//            if (inwissel) {
//                if (ry >= 115 && ry <= 205) {
//                    pl = Math.sin((ry - 115) / 73) * 45;
//                } else if (ry >= 205 && ry <= 250) {
//                    pl = 43;
//                } else if (ry >= 250 && ry <= 320) {
//                    pl = Math.sin(1 - ((ry - 250) / 73)) * 45;
//                } else {
//                    pl = 0;
//                }
//            } else {
//                inwissel = false;
//                pl = 0;
//            }
//        }
//    }
//
//    private void localiseerCabAnime() {
//        if (positie >= 0 && positie < 37.5) {
//            cabIsBoven();
//        } else if (positie >= 37.5 && positie < 50) {
//            cabIsRechts();
//        } else if (positie >= 50 && positie < 87.5) {
//            cabIsBeneden();
//        } else if (positie >= 87.5 && positie < 100) {
//            cabIsLinks();
//        }
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
//    public void cabIsLinks() {
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
//    public void turnCabAnime() {
//        int ux = (int) rx;
//        int uy = (int) ry;
//        if (ux < 151 && uy < 96) {
//            // Linksboven
//            angle = getAngle(ux - 151, uy - 96) + Math.PI;
//            rx = 151 + Math.cos(angle) * 70;
//            ry = 96 + Math.sin(angle) * 70;
//        } else if (ux > 1103 && uy < 96) {
//            // Rechtsboven
//            angle = getAngle(ux - 1103, uy - 96) + (Math.PI * 2);
//            rx = 1104 + Math.cos(angle) * 70;
//            ry = 96 + Math.sin(angle) * 70;
//        } else if (ux > 1103 && uy > 335) {
//            // Rechtsonder
//            angle = getAngle(ux - 1103, uy - 335);
//            rx = 1104 + Math.cos(angle) * 70;
//            ry = 335 + Math.sin(angle) * 70;
//        } else if (ux < 151 && uy > 335) {
//            // Linksonder
//            angle = getAngle(ux - 151, uy - 335) + (Math.PI * 1);
//            rx = 151 + Math.cos(angle) * 70;
//            ry = 335 + Math.sin(angle) * 70;
//        }
//
//        x = (int) rx;
//        y = (int) ry;
//    }
//    
//    	private double getAngle(double com1, double com2) {
//		// System.out.println(Math.cos(com1 / com2));
//		return Math.atan(com2 / com1);
//	}
//        
//        	// Controleer of de trein een sensor passeert
//	
//	private void pollSensor() {
//		int scheck = sensor.check();
//		if (scheck > 0) {
//			
//			
//			
//			TreinBeheer.sensorBericht(this.id,sens[scheck-1]);
//			
//			// hij is langs sensor scheck stuur een bericht naar treinbeheer
//		}
//	}
//
//}
