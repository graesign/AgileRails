/*
 * GUIMain.java
 *
 * Created on November 19, 2007, 12:41 PM
 */

package gui;
import java.awt.*;
import javax.swing.*;
import railcab.Main;
import railcab.Timer;
import java.awt.Point;

/**
 *
 * @author  Fabian van den IJssel
 */
public class GUIMain extends javax.swing.JFrame {

//Maak de panels aan in de tabbladen    
public static gui.OverzichtPaneel pnlOverzicht = new gui.OverzichtPaneel();
public static gui.ReizigerPaneel pnlReiziger = new gui.ReizigerPaneel();
public static gui.StationPaneel pnlStation = new gui.StationPaneel();
public static gui.TreinPaneel pnlTrein = new gui.TreinPaneel();

//maak een pointer naar de simulatie
public static gui.Simulatie sim;

//set de globale variabelen
public static int aantaltreinen = 0;
public static int maxtreinen;

//Maakt een pointer naar main aan
Main main;


      /**
     * Maakt een nieuwe instantie van GUImain aan. 
     * @param main een referentie naar main
     * @param baan of de baan is gekoppeld
     * @param sim referentie naar de simulatie
     */
    public GUIMain(Main main, boolean baan, Simulatie sim) {
        this.sim = sim;
        this.main = main;
        initComponents(); 
        
        
        if(baan) {
            sldSimulatieSnelheid.setEnabled(false);
            pnlOverzicht.voegActieToe("De hardware baan is gekoppeld");
            maxtreinen = 4;
            lblMaxAantalTreinen.setText("Maximaal: " + maxtreinen);
            btnStop.setEnabled(false);
        } else {
            pnlOverzicht.voegActieToe("Programma is in simulatiemodus");
            maxtreinen = 7;
            lblMaxAantalTreinen.setText("Maximaal: " + maxtreinen);
            btnStop.setEnabled(true);
        }
        //Start de panel reizigers in een nieuwe thread
        Thread pnlReizigerThread = new Thread(pnlOverzicht, "Simulatiestatistiek Thread");
        pnlReizigerThread.start();
        pnlReizigerThread.setPriority(2); 
        
        //Aantal treinen weergeven
        this.lblHuidigTreinenAantal.setText("Huidig aantal treinen: "+aantaltreinen);
    
        //Foutmelding venster laden
        dlgFoutmelding.setLocationRelativeTo(null);
        dlgFoutmelding.setVisible(false);
    }
 
    public void setPassengers(int start, int end, int passengers) {
        System.out.println("inside GUI Main");
        vertrekpuntBox.getModel().setSelectedItem("Station " + start);
        bestemmingBox.getModel().setSelectedItem("Station " + end);
        aantalReizSpinner.getModel().setValue(passengers);

        this.voegMensenToe();
    }
    
/**
* veranderd de tijd in de gui. 
* @param tijdInSeconden de tijd vor de simulatie in seconden.
*/
        public void setTijd(int tijdInSeconden){
            String strTijd[] = tijdNaarString(tijdInSeconden, true);
            lblTijd.setText("Tijd: "+strTijd[0]+":"+strTijd[1]+":"+strTijd[2]);
            pnlReiziger.setTijd(tijdInSeconden);
            
            if(tijdInSeconden % 60 == 0) {
            
            if( (Integer) (sprVertrekTijdMinuten.getValue()) <=  new Integer(strTijd[1]))
                sprVertrekTijdMinuten.getModel().setValue(new Integer(strTijd[1]) + 1);
       
            if ( (Integer)(sprVertrekTijdMinuten.getValue()) == new Integer(60))
                sprVertrekTijdMinuten.getModel().setValue(new Integer(0));
            
            
            if( (Integer) (sprVertrekTijdUren.getValue()) <  new Integer(strTijd[0])){
                
                sprVertrekTijdUren.getModel().setValue(new Integer(strTijd[0]));
                sprVertrekTijdMinuten.getModel().setValue(new Integer(strTijd[1]) + 1);
            }
        }
        }
        
/**
* Geeft een foutmelding venster van de ingevoerde tekst
* @param titel De titel van de foutmelding
* @param message Het bericht dat in de foutmelding moet staan
*/        
    public static void foutmelding(String titel, String message){
        lblErrorTitel.setText("Fout - " + titel);
        txtFoutmelding.setText(message);
        dlgFoutmelding.setVisible(true);
        dlgFoutmelding.setLocationRelativeTo(null);
        dlgFoutmelding.repaint();
    }

/**
* Coverteert de tijd in seconden van een int naar een stringarray
* @param tijdInSeconden de tijd in seconden
* @param inDelen true = tijd word in een array teruggestuurd, false = tijd word in 1 string teruggestuurd
 *@return de tijd in een string
*/     
    public static String[] tijdNaarString(int tijdInSeconden, boolean inDelen) {
        int uren, minuten, seconden;
        String strUren, strMinuten, strSeconden;
        uren = 0;
        minuten = 0;
        seconden = 0;
               
        //Bereken de hele uren en haal ze eraf
        if(tijdInSeconden >= 3600){
            uren = tijdInSeconden/3600;
            tijdInSeconden = tijdInSeconden - (uren*3600);
        }
             
        //Bereken de hele minuten en haal ze eraf
        if(tijdInSeconden >= 60){
            minuten = tijdInSeconden/60;
            tijdInSeconden = tijdInSeconden - (minuten*60);
        }
                              
        //Geef de overige seconden weer
        if(tijdInSeconden > 0){
            seconden = tijdInSeconden;
        }
               
        if(uren<=9){
            strUren = "0"+Integer.toString(uren);
        }
            else{
               strUren = Integer.toString(uren);
               }

        if(minuten<=9){
            strMinuten = "0"+Integer.toString(minuten);
        }
            else{
                strMinuten = Integer.toString(minuten);
               }

        if(seconden<=9){
            strSeconden = "0"+Integer.toString(seconden);
            }
            else{
                strSeconden = Integer.toString(seconden);
        }
        if(inDelen)
            return new String[] { strUren, strMinuten, strSeconden };
        else  
            return new String[] {""+strUren+":"+strMinuten+":"+strSeconden};
    }
        

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlgFoutmelding = new javax.swing.JDialog();
        lblErrorTitel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtFoutmelding = new javax.swing.JTextArea();
        btnFoutmeldingOK = new javax.swing.JButton();
        pnlSimulatieSnelheid = new javax.swing.JPanel();
        sldSimulatieSnelheid = new javax.swing.JSlider();
        lblTijd = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        lblControle = new javax.swing.JLabel();
        lblSnelheid = new javax.swing.JLabel();
        pnlReizigerToevoegen = new javax.swing.JPanel();
        vertrekpuntBox = new javax.swing.JComboBox();
        bestemmingBox = new javax.swing.JComboBox();
        aantalReizSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        aantalLabel = new javax.swing.JLabel();
        sprVertrekTijdMinuten = new JSpinner(new SpinnerNumberModel(01, 0, 59, 1));
        vertrekTijdLabel = new javax.swing.JLabel();
        sprVertrekTijdUren = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
        bevestigButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlTreinAantal = new javax.swing.JPanel();
        lblHuidigTreinenAantal = new javax.swing.JLabel();
        lblPositie = new javax.swing.JLabel();
        btnVoegTreinToe = new javax.swing.JButton();
        lblTreinnr = new javax.swing.JLabel();
        btnVerwijderTrein = new javax.swing.JButton();
        sprPositie = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
        sprTreinNr = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
        lblMaxAantalTreinen = new javax.swing.JLabel();
        lblPassagiersPerTrein = new javax.swing.JLabel();
        sprPassagiersPerTrein = new JSpinner(new SpinnerNumberModel(5, 2, 10, 1));
        btnSlaOp = new javax.swing.JButton();
        tbcTabbladen = new javax.swing.JTabbedPane();
        lblSimulatieUitgeschakeld = new javax.swing.JLabel();
        lblSimulatieActiveren = new javax.swing.JLabel();

        dlgFoutmelding.setTitle("Foutmelding"); // NOI18N
        dlgFoutmelding.setAlwaysOnTop(true);
        dlgFoutmelding.setMinimumSize(new java.awt.Dimension(420, 200));
        dlgFoutmelding.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblErrorTitel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblErrorTitel.setText("Fout - Geen titel ingevoerd"); // NOI18N
        dlgFoutmelding.getContentPane().add(lblErrorTitel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtFoutmelding.setColumns(20);
        txtFoutmelding.setEditable(false);
        txtFoutmelding.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtFoutmelding.setLineWrap(true);
        txtFoutmelding.setRows(5);
        txtFoutmelding.setText("In dit venster word een beschrijving van de foutmelding weergegeven.\nEventueel Met oplossing."); // NOI18N
        jScrollPane1.setViewportView(txtFoutmelding);

        dlgFoutmelding.getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 390, 100));

        btnFoutmeldingOK.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnFoutmeldingOK.setText("OK"); // NOI18N
        btnFoutmeldingOK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFoutmeldingOKMouseClicked(evt);
            }
        });
        dlgFoutmelding.getContentPane().add(btnFoutmeldingOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, 70, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RailCab - Hoofdvenster"); // NOI18N
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(900, 700));
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlSimulatieSnelheid.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Simulatie snelheid", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        pnlSimulatieSnelheid.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sldSimulatieSnelheid.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        sldSimulatieSnelheid.setMajorTickSpacing(100);
        sldSimulatieSnelheid.setMaximum(999);
        sldSimulatieSnelheid.setMinorTickSpacing(50);
        sldSimulatieSnelheid.setPaintTicks(true);
        sldSimulatieSnelheid.setValue(0);
        sldSimulatieSnelheid.setFocusable(false);
        sldSimulatieSnelheid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sldSimulatieSnelheidMouseReleased(evt);
            }
        });
        pnlSimulatieSnelheid.add(sldSimulatieSnelheid, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 180, -1));

        lblTijd.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblTijd.setText("Tijd: 00:00:00"); // NOI18N
        pnlSimulatieSnelheid.add(lblTijd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        btnStart.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnStart.setText("Start");
        btnStart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStartMouseClicked(evt);
            }
        });
        pnlSimulatieSnelheid.add(btnStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 70, -1));

        btnStop.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnStop.setText("Pauzeer");
        btnStop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStopMouseClicked(evt);
            }
        });
        pnlSimulatieSnelheid.add(btnStop, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 70, -1));

        lblControle.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblControle.setText("Controle:");
        pnlSimulatieSnelheid.add(lblControle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        lblSnelheid.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblSnelheid.setText("Snelheid:");
        pnlSimulatieSnelheid.add(lblSnelheid, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        getContentPane().add(pnlSimulatieSnelheid, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 260, 140));

        pnlReizigerToevoegen.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reizigers toevoegen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        pnlReizigerToevoegen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vertrekpuntBox.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        vertrekpuntBox.setMaximumRowCount(9);
        vertrekpuntBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vertrekpunt", "Station 1", "Station 2", "Station 3", "Station 4", "Station 5", "Station 6", "Station 7", "Station 8" }));
        pnlReizigerToevoegen.add(vertrekpuntBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 220, -1));

        bestemmingBox.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        bestemmingBox.setMaximumRowCount(9);
        bestemmingBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bestemming", "Station 1: Duitsland", "Station 2: Duitsland", "Station 3: Duitsland", "Station 4: Duitsland", "Station 5: Duitsland", "Station 6: Duitsland", "Station 7: Duitsland", "Station 8: Duitsland", "Station 1: Nederland", "Station 2: Nederland", "Station 3: Nederland", "Station 4: Nederland", "Station 5: Nederland", "Station 6: Nederland", "Station 7: Nederland", "Station 8: Nederland" }));
        pnlReizigerToevoegen.add(bestemmingBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 220, -1));

        aantalReizSpinner.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        aantalReizSpinner.setFocusable(false);
        pnlReizigerToevoegen.add(aantalReizSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 130, -1));

        aantalLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        aantalLabel.setText("Aantal:"); // NOI18N
        pnlReizigerToevoegen.add(aantalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        sprVertrekTijdMinuten.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        sprVertrekTijdMinuten.setFocusable(false);
        pnlReizigerToevoegen.add(sprVertrekTijdMinuten, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 40, -1));

        vertrekTijdLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        vertrekTijdLabel.setText("Vertrektijd:"); // NOI18N
        pnlReizigerToevoegen.add(vertrekTijdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        sprVertrekTijdUren.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        sprVertrekTijdUren.setFocusable(false);
        sprVertrekTijdUren.setVerifyInputWhenFocusTarget(false);
        pnlReizigerToevoegen.add(sprVertrekTijdUren, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 40, -1));

        bevestigButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        bevestigButton.setText("Bevestig"); // NOI18N
        bevestigButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bevestigButtonMouseClicked(evt);
            }
        });
        pnlReizigerToevoegen.add(bevestigButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 110, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText(":");
        pnlReizigerToevoegen.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel2.setText(": 00");
        pnlReizigerToevoegen.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        getContentPane().add(pnlReizigerToevoegen, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 140, 260, 180));

        pnlTreinAantal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Treinen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        pnlTreinAantal.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        pnlTreinAantal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHuidigTreinenAantal.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblHuidigTreinenAantal.setText("Huidig aantal treinen: 4"); // NOI18N
        pnlTreinAantal.add(lblHuidigTreinenAantal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        lblPositie.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblPositie.setText("Voeg toe op Station:");
        pnlTreinAantal.add(lblPositie, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        btnVoegTreinToe.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnVoegTreinToe.setText("Voeg toe");
        btnVoegTreinToe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVoegTreinToeMouseClicked(evt);
            }
        });
        pnlTreinAantal.add(btnVoegTreinToe, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 80, -1));

        lblTreinnr.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblTreinnr.setText("Verwijder trein nr:"); // NOI18N
        pnlTreinAantal.add(lblTreinnr, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        btnVerwijderTrein.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnVerwijderTrein.setText("Verwijder");
        btnVerwijderTrein.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerwijderTreinMouseClicked(evt);
            }
        });
        pnlTreinAantal.add(btnVerwijderTrein, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 80, -1));

        sprPositie.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        pnlTreinAantal.add(sprPositie, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 40, -1));

        sprTreinNr.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        pnlTreinAantal.add(sprTreinNr, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 40, -1));

        lblMaxAantalTreinen.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblMaxAantalTreinen.setText("Maximaal: 0");
        pnlTreinAantal.add(lblMaxAantalTreinen, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        lblPassagiersPerTrein.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblPassagiersPerTrein.setText("Passagiers per trein:");
        pnlTreinAantal.add(lblPassagiersPerTrein, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        sprPassagiersPerTrein.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        pnlTreinAantal.add(sprPassagiersPerTrein, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 40, -1));

        btnSlaOp.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSlaOp.setText("Sla op");
        btnSlaOp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSlaOpMouseClicked(evt);
            }
        });
        pnlTreinAantal.add(btnSlaOp, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 80, -1));

        getContentPane().add(pnlTreinAantal, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 320, 260, 140));
        pnlTreinAantal.getAccessibleContext().setAccessibleName("mm");

        tbcTabbladen.setBackground(getForeground());
        tbcTabbladen.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        tbcTabbladen.addTab("Overzicht",  new javax.swing.ImageIcon(getClass().getResource("Icon/overzicht.gif")), pnlOverzicht, "Een overzicht van de statistieken");
        tbcTabbladen.addTab("Reizigers", new javax.swing.ImageIcon(getClass().getResource("Icon/reiziger.gif")), pnlReiziger, "Informatie over de reizigers");
        tbcTabbladen.addTab("Stations", new javax.swing.ImageIcon(getClass().getResource("Icon/station.gif")), pnlStation, "Informatie over de stations");
        tbcTabbladen.addTab("Treinen",  new javax.swing.ImageIcon(getClass().getResource("Icon/trein.gif")), pnlTrein, "Informatie over de treinen");
        getContentPane().add(tbcTabbladen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 910, 210));

        lblSimulatieUitgeschakeld.setText("De simulatie is uitgeschakeld tijdens inactiviteit ivm. processorgebruik");
        getContentPane().add(lblSimulatieUitgeschakeld, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, -1, -1));

        lblSimulatieActiveren.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSimulatieActiveren.setText("Klik op het scherm om de simulatie te activeren");
        getContentPane().add(lblSimulatieActiveren, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

 /**
* Veranderd de snelheid van de simulatie bij een muisklik op de slider
*/ 
    private void sldSimulatieSnelheidMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sldSimulatieSnelheidMouseReleased
        // Snelheid word veranderd
        int i;
        i = sldSimulatieSnelheid.getValue();
        i = 1000 - i;
        if (i > Timer.getSimSnelheid()) {
            gui.OverzichtPaneel.voegActieToe("Simulatiesnelheid vertraagd");
        }
        else {
            if( i < Timer.getSimSnelheid()) {
            gui.OverzichtPaneel.voegActieToe("Simulatiesnelheid versneld");
            }
        }
        Timer.setSimSnelheid(i);
        
        
      
}//GEN-LAST:event_sldSimulatieSnelheidMouseReleased

/**
* Voegt de reiziger(s) toe aan het systeem als er op de bevestigknop is geklikt
*/ 
private void bevestigButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bevestigButtonMouseClicked
    this.voegMensenToe();
}//GEN-LAST:event_bevestigButtonMouseClicked

private void voegMensenToe() {
    int vertrek = vertrekpuntBox.getSelectedIndex();
    int bestemming = bestemmingBox.getSelectedIndex();
    int bestemmingBuitenland = 0;
    if (bestemming > 8) {
        bestemmingBuitenland = bestemming;
        bestemming = 8;
    }
    int insteltijd = ((Integer) sprVertrekTijdUren.getValue() * 3600) +  ((Integer) sprVertrekTijdMinuten.getValue() * 60);
  
    // Controle voor geldige locaties
        if(vertrek <= 0 || bestemming >= 9){
                foutmelding("Verkeerd vertrekpunt","Er is een verkeerd vertrekstation ingevoerd. \nVoer een vertrekstation in van 1 t/m 8");
                return;
            } 
            else{
                if(bestemming >= 9 || bestemming <= 0){
                  foutmelding("Verkeerde bestemming","Er is een verkeerd bestemminsstation ingevoerd. \nVoer een bestemmingsstation in van 1 t/m 8");
                  return;
               }
                else{
                    if(bestemming == vertrek){
                        foutmelding("Gelijke vertrek/bestemming","Het vertrekpunt mag niet gelijk zijn aan de bestemming. \nSelecteer een ander vertrek of bestemminsstation.");
                        return;
                    }
                    else{
                        if(insteltijd < railcab.Timer.getTijd()){
                            foutmelding("Ongeldige vertrektijd","Er is geen geldige vertrektijd ingevoerd. \n Voer een vertrektijd in de toekomst");
                            return;
                        } 
                        else {
                            String strTijd[] = tijdNaarString(insteltijd, true);
                            for(int i = 0; i < (Integer) aantalReizSpinner.getValue(); i++) {
                            main.maakReiziger(vertrek, bestemming, (new Integer(strTijd[0]) * 3600) + (new Integer(strTijd[1]) * 60), bestemmingBuitenland);
                            pnlReiziger.getModel().addRow(new Object[] { (Integer) reizigersID,
                                                                "Station " + vertrek,
                                                                "Station " + bestemming,
                                                                "",
                                                                ""+strTijd[0]+":"+strTijd[1]+":"+strTijd[2],
                                                                "niet op station",
                                                                });
                            reizigersID++;
                            }
                            if((Integer)aantalReizSpinner.getValue() > 1){
                                gui.OverzichtPaneel.voegActieToe(aantalReizSpinner.getValue() + " reizigers toegevoegd op station: " + vertrek);
                            }
                            else{
                                gui.OverzichtPaneel.voegActieToe(aantalReizSpinner.getValue() + " reiziger toegevoegd op station: " + vertrek);
                            }
                                
                        }
                        
                    }
                }
            }
}
        
/**
* Verberg de foutmelding als er op OK is geklikt
*/ 
private void btnFoutmeldingOKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFoutmeldingOKMouseClicked
   dlgFoutmelding.setVisible(false);
}//GEN-LAST:event_btnFoutmeldingOKMouseClicked


/**
* Verplaats het simulatievenster als het hoofdvenster word verplaatst
*/ 
private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
   try{
    //Verplaats het simulatieframe met het hoofdframe
    Point simulatieLocatie = this.getLocationOnScreen();
    simulatieLocatie.x = simulatieLocatie.x + 80;
    simulatieLocatie.y = simulatieLocatie.y + 60;
            
    railcab.Main.SIM.setLocation(simulatieLocatie);
   }
    catch(Exception err){
        //Soms treed er een error op tijdens het verslepen van het scherm
        //Dit is verder niet van belang en kan met rust gelaten worden
        
   }
      
}//GEN-LAST:event_formComponentMoved

/**
* Minimaliseer, of deminimaliseer het simulatievenster als het hoofdvenster minimaliseert/deminimaliseerd
*/ 
private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
    if(this.getState() == Frame.ICONIFIED ){
        //Hoofdvenster is geminimaliseerd, verberg de simulatie
         railcab.Main.SIM.setVisible(false);
    }
    else{
      //Hoofdvenster is niet geminimaliseerd, geef de simulatie weer
        railcab.Main.SIM.setVisible(true);
        railcab.Main.SIM.plaatsWissel();
    }
}//GEN-LAST:event_formWindowStateChanged

/**
* Verberg het simulatievenster als de focus niet op het hoofdvenster zit
*/ 
private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
    //Verberg het simulatievenster van de voorgrond
    railcab.Main.SIM.toBack();
}//GEN-LAST:event_formWindowLostFocus

/**
* Geef het simulatievenster weer als het hoofdvenster de focus heeft
*/ 
private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
    //Zet het simulatievenster op de voorgrond
    railcab.Main.SIM.setAlwaysOnTop(true);
}//GEN-LAST:event_formWindowGainedFocus

/**
* Voeg een trein toe als op de knop is gedrukt
*/ 

private void btnVoegTreinToeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoegTreinToeMouseClicked
    int treinnummer;
    int station = ((Integer)sprPositie.getValue()).intValue();
    if((main.StationIsLeeg(station)) && (aantaltreinen < maxtreinen)){
        //Voeg een trein toe aan de tabel
        pnlTrein.getModel().addRow(new Object[] {99 ,"0","0","0","0"});
    
        //Maak de trein aan en update de tabel
        treinnummer = main.maakTrein(station)+1;
        pnlTrein.tblTreinen.setValueAt(treinnummer, pnlTrein.findTreinRow(99), 0);
    
        aantaltreinen++;
        this.lblHuidigTreinenAantal.setText("Huidig aantal treinen: "+aantaltreinen);
    
        //Actie voltooid geef recente actie
        gui.OverzichtPaneel.voegActieToe("Trein toegevoegd op station: " + sprPositie.getValue());
    }
    else {
        foutmelding("Station is bezet", "De trein kan niet op dit station toegevoegd worden omdat dit station bezet is door een andere trein of het maximaal aantal treinen is berijkt.\n\nVoeg een trein toe op een ander station of verwijder eerst een trein.");
        System.out.println("GuiMain: trein toevoegen mislukt");
    }
    
   
}//GEN-LAST:event_btnVoegTreinToeMouseClicked

/**
* Verwijder een trein als op de knop is gedrukt
*/ 
private void btnVerwijderTreinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerwijderTreinMouseClicked
    int treinnr = 0;
    
    treinnr =    pnlTrein.findTreinRow(Integer.valueOf(sprTreinNr.getValue().toString()).intValue());
    
    int i = 0;
    
    if(treinnr != -1){
        //Verwijder trein uit tabel
        pnlTrein.getModel().removeRow(treinnr);
        
        //Hernoem de treinnummers 
        for(i = treinnr; i < pnlTrein.tblTreinen.getRowCount(); i++){
            pnlTrein.tblTreinen.setValueAt((i+1), i, 0);
        }
        
        //Verwijder trein uithet simulatie paneel
        main.SIM.resetTrein(aantaltreinen-1);
        
        //Verwijder de trein uit het systeem
        main.verwijderTrein(treinnr);

        //Update huidig treinen aantal
        aantaltreinen--;
        this.lblHuidigTreinenAantal.setText("Huidig aantal treinen: "+aantaltreinen);
        
        //Actie voltooid update recente acties
        gui.OverzichtPaneel.voegActieToe("Trein  " + sprTreinNr.getValue() + " verwijderd");
    }
    else{
       foutmelding("Trein bestaat niet","Trein: " +treinnr+ " trein bestaat niet. \nSelecteer een bestaande trein om te verwijderen.");
    }
}//GEN-LAST:event_btnVerwijderTreinMouseClicked

/**
* Start de simulatie
*/ 
private void btnStartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStartMouseClicked
    Timer.startTimer();
    gui.OverzichtPaneel.voegActieToe("Simulatie gestart");
}//GEN-LAST:event_btnStartMouseClicked

/**
* pauzeer de simulatie
*/ 

private void btnStopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStopMouseClicked
   if(btnStop.isEnabled()){
    Timer.stopTimer();
    gui.OverzichtPaneel.voegActieToe("Simulatie gestaakt");
   }
}//GEN-LAST:event_btnStopMouseClicked

/**
* Roep de afsluit methode aan in main, om de baan te resetten
*/ 

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    main.sluitAf();
}//GEN-LAST:event_formWindowClosing

/**
* Wijzig het aantal passagiers per trein
*/ 

private void btnSlaOpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSlaOpMouseClicked
    //Verander het aantal passagiers per trein
    main.setPassagiersaantal(((Integer)sprPassagiersPerTrein.getValue()).intValue());
    gui.OverzichtPaneel.voegActieToe("Maximum aantal passagiers per trein veranderd naar: " + ((Integer)sprPassagiersPerTrein.getValue()).intValue());
    
}//GEN-LAST:event_btnSlaOpMouseClicked


/**
* Geeft data door aan de GUI om weer te geven
* @param subject = trein | reiziger | string
* @param id is het nummer van de objecten (bv wissel nr 5)
* @param status geeft de nieuwe status van het object aan.
*/
public void updateGui(String subject, int[] id, int[] status) {
    
    if(subject.equals("trein")) {
    // update trein locatie. status is nieuwe locatie van de trein
        for(int i = 0; i < id.length; i++) {
            railcab.Main.SIM.rijTrein(id[i], status[i]);
            int rownummer = pnlTrein.findTreinRow((id[i]+1));
            if(rownummer != -1){
            pnlTrein.tblTreinen.setValueAt(status[i], rownummer, 2);
            }
          }
    } else {
    if(subject.equals("wissel")) {
        // status geeft open (1) of dicht (0) aan
            for(int i = 0; i < id.length; i++) {
                railcab.Main.SIM.zetWissel(id[i], status[0]);
            }
    } else {
    if(subject.equals("reiziger")) {
        for(int i = 0; i < (id.length); i++) {
            pnlReiziger.setReizigerStatus((id[i]-1), status[0]);
        }
    }else{
    if(subject.equals("treindata")){
        //Set het vertrekpunt van trein id
        // 0 = aantal passagiers
        // 1 = bestemming
        // 2 = vertrekpunt
        
        
        if(status[0] == 0){
            sim.setLeeg(id[0]);
        }else{
            sim.setVol(id[0]);
        }
        int rownummer = pnlTrein.findTreinRow(id[0]+1);
        if(rownummer != -1){
            pnlTrein.tblTreinen.setValueAt(status[0], rownummer, 1);
            pnlTrein.tblTreinen.setValueAt(status[1], rownummer, 4);
            pnlTrein.tblTreinen.setValueAt(status[2], rownummer, 3);
                            }
                                    }
        }
            }
            }
}
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aantalLabel;
    private javax.swing.JSpinner aantalReizSpinner;
    private javax.swing.JComboBox bestemmingBox;
    private javax.swing.JButton bevestigButton;
    private static javax.swing.JButton btnFoutmeldingOK;
    private javax.swing.JButton btnSlaOp;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnVerwijderTrein;
    private javax.swing.JButton btnVoegTreinToe;
    private static javax.swing.JDialog dlgFoutmelding;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private static javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblControle;
    private static javax.swing.JLabel lblErrorTitel;
    private javax.swing.JLabel lblHuidigTreinenAantal;
    private javax.swing.JLabel lblMaxAantalTreinen;
    private javax.swing.JLabel lblPassagiersPerTrein;
    private javax.swing.JLabel lblPositie;
    private javax.swing.JLabel lblSimulatieActiveren;
    private javax.swing.JLabel lblSimulatieUitgeschakeld;
    private javax.swing.JLabel lblSnelheid;
    private javax.swing.JLabel lblTijd;
    private javax.swing.JLabel lblTreinnr;
    private javax.swing.JPanel pnlReizigerToevoegen;
    private javax.swing.JPanel pnlSimulatieSnelheid;
    private javax.swing.JPanel pnlTreinAantal;
    private javax.swing.JSlider sldSimulatieSnelheid;
    private javax.swing.JSpinner sprPassagiersPerTrein;
    private javax.swing.JSpinner sprPositie;
    private javax.swing.JSpinner sprTreinNr;
    private javax.swing.JSpinner sprVertrekTijdMinuten;
    private javax.swing.JSpinner sprVertrekTijdUren;
    private javax.swing.JTabbedPane tbcTabbladen;
    private static javax.swing.JTextArea txtFoutmelding;
    private javax.swing.JLabel vertrekTijdLabel;
    private javax.swing.JComboBox vertrekpuntBox;
    // End of variables declaration//GEN-END:variables
    private static int reizigersID;
}
