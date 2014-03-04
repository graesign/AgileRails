/*
 * Simulatie.java
 *
 * Created on 06 December 2007, 10:52
 */

package gui;

import java.awt.Point;
import javax.swing.JLabel;

/**
 *
 * @author  Martijn de Jongh
 */
public class Simulatie extends javax.swing.JFrame {
    
    public JLabel[] treinarray,wisselarray;
    public Integer[][] baanarray,treinstart;
    public Integer[] treinpos;
    public Integer k = 1;
    public Point p;

    /** Creates new form Simulatie */
    public Simulatie() {
        
        initComponents();
        
        //Verplaats het huidige venster zodat deze beter staat uitgelijnd.
        this.setLocation(30,47);
       
        maakBaan();
        maakWissel();
        maakTrein();

  
    }
    
    /**
    * Deze functie laat een trein naar een andere postie op de baan gaan
    * @param j , de trein die verplaatst wordt.
    * @param i , de locatie waar de trein geplaatst dient te worden.
    */
    public void rijTrein(int j, int i){
       
          treinarray[j].setLocation(baanarray[i][1],baanarray[i][2]);
        
       }

    /**
     * Deze functie zorgt dat de trein niet meer zichtbaar is voor
     * de gebruik wanneer deze is verwijderd.
     * @param a , de trein die word weggehaald
     */
    public void resetTrein(int a){
        treinarray[a].setLocation(700,700);
      }  
    
    /**
     * Dit zorgt ervoor dat als een trein vol zit met reizigers de afbeelding
     * van de trein rood wordt, hierdoor is het visueel snel te herkennen.
     * @param a , de trein die veranderd wordt.
     */
    public void setVol(int a){
        if ( a == 0){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein1vol.gif")));
         }
        else if ( a == 1){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein2vol.gif")));
  
        }
        else if ( a == 2){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein3vol.gif")));
  
        }
        else if ( a == 3){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein4vol.gif")));
  
        }
        else if ( a == 4){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein5vol.gif")));
  
        }
        else if ( a == 5){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein6vol.gif")));
  
        }
        else if ( a == 6){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein7vol.gif")));
  
        }
        else if ( a == 7){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein8vol.gif")));
  
        }
     }
    
    /**
     * Hetzelfde als de vorige functie alleen deze dient om ze leeg te maken
     * @param a De trein die verandert word.
     */
      public void setLeeg(int a){
        if ( a == 0){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein1.gif")));
         }
        else if ( a == 1){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein2.gif")));
  
        }
        else if ( a == 2){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein3.gif")));
  
        }
        else if ( a == 3){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein4.gif")));
  
        }
        else if ( a == 4){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein5.gif")));
  
        }
        else if ( a == 5){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein6.gif")));
  
        }
        else if ( a == 6){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein7.gif")));
  
        }
        else if ( a == 7){
             treinarray[a].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein8.gif")));
  
        }
     }
    
      
    /**
     * De baan wordt hier aangemaakt, met alle bijbehorende positie's waar een
     * trein zich op kan bevinden
     */
    public void maakBaan(){
        
        baanarray = new Integer[30][3];
        baanarray[0][1] = 472;
        baanarray[0][2] = 293;
        baanarray[1][1] = 445;
        baanarray[1][2] = 340;
        baanarray[2][1] = 340;
        baanarray[2][2] = 348;
        baanarray[3][1] = 340;
        baanarray[3][2] = 328;
        baanarray[4][1] = 263;
        baanarray[4][2] = 348;
        baanarray[5][1] = 186;
        baanarray[5][2] = 348;
        baanarray[6][1] = 186;
        baanarray[6][2] = 328;
        baanarray[7][1] = 83;
        baanarray[7][2] = 325;
        baanarray[8][1] = 66;
        baanarray[8][2] = 236;
        baanarray[9][1] = 48;
        baanarray[9][2] = 238;
        baanarray[10][1] = 66;
        baanarray[10][2] = 174;
        baanarray[11][1] = 66;
        baanarray[11][2] = 107;
        baanarray[12][1] = 48;
        baanarray[12][2] = 107;
        baanarray[13][1] = 83;
        baanarray[13][2] = 18;
        baanarray[14][1] = 186;
        baanarray[14][2] = 0;
        baanarray[15][1] = 186;
        baanarray[15][2] = 15;
        baanarray[16][1] = 263;
        baanarray[16][2] = 0;
        baanarray[17][1] = 340;
        baanarray[17][2] = 0;
        baanarray[18][1] = 340;
        baanarray[18][2] = 15;
        baanarray[19][1] = 454;
        baanarray[19][2] = 14;
        baanarray[20][1] = 472;
        baanarray[20][2] = 50;
        baanarray[21][1] = 434;
        baanarray[21][2] = 105;
        baanarray[22][1] = 285;
        baanarray[22][2] = 108;
        baanarray[23][1] = 285;
        baanarray[23][2] = 125;
        baanarray[24][1] = 120;
        baanarray[24][2] = 108;
        baanarray[25][1] = 80;
        baanarray[25][2] = 174;
        baanarray[26][1] = 120;
        baanarray[26][2] = 236;
        baanarray[27][1] = 285;
        baanarray[27][2] = 238;
        baanarray[28][1] = 285;
        baanarray[28][2] = 220;  
        baanarray[29][1] = 445;
        baanarray[29][2] = 245;
                
     }
    
    

    /**
     * Het omzetten van een wissel
     * @param i nummer van de wissel
     * @param j , status van de wissel, 1 = ga open, 0 = ga dicht
     */
    public void zetWissel(int i, int j){
        
        if (j == 0){
            wisselarray[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/wisseldicht.gif")));
           
        }
        else { 
            wisselarray[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/wisselopen.gif")));
        }

      }
    
    /**
     * De wissels van de simulatie worden hier aangemaakt en in een array geplaatst
     */
    public void maakWissel(){
      
       wisselarray = new JLabel[8];
       
       wisselarray[0] = this.wissel1;
       wisselarray[1] = this.wissel2;
       wisselarray[2] = this.wissel3;
       wisselarray[3] = this.wissel4;
       wisselarray[4] = this.wissel5;
       wisselarray[5] = this.wissel6;
       wisselarray[6] = this.wissel7;
       wisselarray[7] = this.wissel8;
         
    }
    
    /**
     * De wissels worden op de correcte plaats in de simulatie geplaatst.
     */
    public void plaatsWissel(){
        
       wisselarray[0].setLocation(395, 333);
       wisselarray[1].setLocation(235, 333);
       wisselarray[2].setLocation(45, 275);
       wisselarray[3].setLocation(45, 150);
       wisselarray[4].setLocation(125, 13);
       wisselarray[5].setLocation(285, 13);
       wisselarray[6].setLocation(370, 122);
       wisselarray[7].setLocation(195, 225);
       
       //Hier wordt er tevens nog voor gezorgt dat met opstarten de treinen niet
       //zichtbaar zijn.
       for (int i = 0; i <= 7; i++){
           treinarray[i].setLocation(700, 700);
       }
       
    }
    
    /**
     * De treinen worden aangemaakt en in een array gestopt
     */
    public void maakTrein(){
        
     treinarray = new JLabel[8]; 
    
     treinarray[0] = this.trein1;
     treinarray[1] = this.trein2;
     treinarray[2] = this.trein3;
     treinarray[3] = this.trein4;
     treinarray[4] = this.trein5;
     treinarray[5] = this.trein6;
     treinarray[6] = this.trein7;
     treinarray[7] = this.trein8;
     
     
            
   }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        trein1 = new javax.swing.JLabel();
        trein2 = new javax.swing.JLabel();
        trein3 = new javax.swing.JLabel();
        trein4 = new javax.swing.JLabel();
        trein5 = new javax.swing.JLabel();
        trein6 = new javax.swing.JLabel();
        trein7 = new javax.swing.JLabel();
        trein8 = new javax.swing.JLabel();
        wissel1 = new javax.swing.JLabel();
        wissel2 = new javax.swing.JLabel();
        wissel3 = new javax.swing.JLabel();
        wissel4 = new javax.swing.JLabel();
        wissel5 = new javax.swing.JLabel();
        wissel6 = new javax.swing.JLabel();
        wissel8 = new javax.swing.JLabel();
        wissel7 = new javax.swing.JLabel();

        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setFocusable(false);
        setFocusableWindowState(false);
        setResizable(false);
        setUndecorated(true);

        jLabel1.setBackground(new java.awt.Color(153, 0, 102));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/baan.gif"))); // NOI18N
        jLabel1.setAlignmentX(50.0F);
        jLabel1.setAlignmentY(50.0F);

        trein1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein1.gif"))); // NOI18N

        trein2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein2.gif"))); // NOI18N

        trein3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein3.gif"))); // NOI18N

        trein4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein4.gif"))); // NOI18N

        trein5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein5.gif"))); // NOI18N

        trein6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein6.gif"))); // NOI18N

        trein7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein7.gif"))); // NOI18N

        trein8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/trein8.gif"))); // NOI18N

        wissel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/wisseldicht.gif"))); // NOI18N

        wissel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/wisseldicht.gif"))); // NOI18N

        wissel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/wisseldicht.gif"))); // NOI18N

        wissel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/wisseldicht.gif"))); // NOI18N

        wissel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/wisseldicht.gif"))); // NOI18N

        wissel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/wisseldicht.gif"))); // NOI18N

        wissel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/wisseldicht.gif"))); // NOI18N

        wissel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Icon/wisseldicht.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(trein1)
                            .addComponent(trein4)
                            .addComponent(trein5)
                            .addComponent(trein8)
                            .addComponent(trein7)
                            .addComponent(trein6)
                            .addComponent(trein3)
                            .addComponent(trein2)
                            .addComponent(wissel1)
                            .addComponent(wissel2)
                            .addComponent(wissel3)
                            .addComponent(wissel4)
                            .addComponent(wissel5)
                            .addComponent(wissel6))
                        .addComponent(wissel7))
                    .addComponent(wissel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(trein1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trein2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trein3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trein4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trein5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trein6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trein7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trein8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wissel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wissel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wissel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wissel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wissel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wissel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wissel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wissel8))
                    .addComponent(jLabel1))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel trein1;
    private javax.swing.JLabel trein2;
    private javax.swing.JLabel trein3;
    private javax.swing.JLabel trein4;
    private javax.swing.JLabel trein5;
    private javax.swing.JLabel trein6;
    private javax.swing.JLabel trein7;
    private javax.swing.JLabel trein8;
    private javax.swing.JLabel wissel1;
    private javax.swing.JLabel wissel2;
    private javax.swing.JLabel wissel3;
    private javax.swing.JLabel wissel4;
    private javax.swing.JLabel wissel5;
    private javax.swing.JLabel wissel6;
    private javax.swing.JLabel wissel7;
    private javax.swing.JLabel wissel8;
    // End of variables declaration//GEN-END:variables
    
}
