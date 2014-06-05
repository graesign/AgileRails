package main;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class RailScherm extends JPanel {
	/**
	 * Het scherm waarin alle cabs en de baan worden getekend
	 */
	private static final long serialVersionUID = 1L;

	// Achtergrond afbeelding
	private ImageIcon back = new ImageIcon("./railsscherm.gif");
	
	private static JLabel klok = new JLabel("",JLabel.CENTER);
	

	// TreinBeheer referentie
	private TreinBeheer treinBeheer;
	private ReisBeheer reisBeheer;

	public int uur;
	public int min;
	
	// Object Lists
	public static ArrayList<JButton> stat = new ArrayList<JButton>(); // Station
	// Buttons
	public static ArrayList<JLabel> cinfo = new ArrayList<JLabel>(); // CabAnimation info
	// labels
	public static ArrayList<JLabel> sinfo = new ArrayList<JLabel>(); // Station
	// infolabels
	private ArrayList<ImageIcon> switchicons = new ArrayList<ImageIcon>(); // WisselIcons
	private ArrayList<ImageIcon> sensoricons = new ArrayList<ImageIcon>(); // SensorIcons

	// Label Color
	Color lcol = new Color(255, 255, 255, 125);

	// Status
	public boolean running = false; // Running status van de simulatie

	// Station JButton posities
	private int[] stax = { 220, 580, 940, 960, 940, 580, 220, 200 };
	private int[] stay = { 130, 130, 130, 230, 330, 330, 330, 230 };
	// Sensor icon posities
	private int[] senx = { 153, 257, 295, 397, 503, 607, 645, 747, 853, 957,
			995, 1097, 1139, 1096, 1096, 1139, 1096, 995, 957, 853, 746, 645,
			607, 503, 396, 295, 257, 153, 111, 153, 153, 111 };
	private int[] seny = { 56, 98, 98, 56, 56, 98, 98, 56, 56, 98, 98, 56, 90,
			195, 232, 335, 435, 477, 477, 435, 435, 477, 477, 435, 435, 477,
			477, 435, 338, 232, 195, 92 };
	// Wissel icon posities
	private int[] wisx = { 179, 529, 879, 1139, 1070, 720, 370, 111 };
	private int[] wisy = { 56, 56, 56, 116, 435, 435, 435, 312 };

	public RailScherm(TreinBeheer treinBeheer, ReisBeheer reisBeheer) {
		this.treinBeheer = treinBeheer;
		this.reisBeheer = reisBeheer;
		klok.setBounds(1180, 470, 60, 30);
		klok.setBackground(Color.white);
		klok.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		klok.setText("0:00");
		klok.setOpaque(true);
		add(klok);
		
		// Voorgrond componenten aanmaken in een for lus
		for (int i = 0; i < 16; i++) {
			// CabAnimation labels aanmaken
			cinfo.add(new JLabel("Cab #" + (i)));
			add(cinfo.get(i));
		}
		// Achtergrond componenten aanmaken in een for lus
		for (int i = 0; i <= 7; i++) {
			// Stat buttons aanmaken
			stat.add(new JButton("Station " + (i + 1)));
			sinfo.add(new JLabel("Wachtenden: 0\nLngst Wachtend:"));

			JButton init = stat.get(i);
			init.setBounds(stax[i], stay[i], 120, 20);
			init.addActionListener(new statHandler("Station " + (i + 1)));
			add(init);
			JLabel init2 = sinfo.get(i);
			init2.setBounds(stax[i], stay[i] + 20, 120, 40);
			init2.setBackground(Color.white);
			init2.setOpaque(true);
			init2.setBorder(BorderFactory.createEtchedBorder());
			init2.setUI(new MultiLineLabelUI());
			add(init2);

			// Switchicons aanmaken, boven, onder, links en recht allemaal met
			// een eigen
			// afbeelding
			if (i <= 2) {
				switchicons.add(new ImageIcon("./w_open_b.gif"));
			} else if (i == 3) {
				switchicons.add(new ImageIcon("./w_open_r.gif"));
			} else if (i >= 4 && i <= 6) {
				switchicons.add(new ImageIcon("./w_open_o.gif"));
			} else if (i == 7) {
				switchicons.add(new ImageIcon("./w_open_l.gif"));
			}
		}

		// Alle sensor icons aanmaken
		for (int i = 0; i <= 32; i++) {
			sensoricons.add(new ImageIcon("./s_aan.gif"));
		}

		// Layout initialiseren
		setLayout(null);
		// Simulatie starten zolang running == true
		running = true;
		repaint();
	}

	// Teken het scherm
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Achtergrond afbeelding weergeven
		back.paintIcon(this, g, 0, 0);
		int ti = 0;
		for (CabAnimation ucab : TreinBeheer.alleTreinen) {
			// Teken zichtbare treinen
			if (ucab.zichtbaar) {
				cinfo.get(ti).setVisible(true);
				ucab.paintIcon(this, g, ucab.x, ucab.y);
				// Teken CabAnimation info labels
				JLabel lab = cinfo.get(ti);
				lab.setBounds(ucab.x - 70, ucab.y + 25, 100, 40);
				lab.setBackground(lcol);
				lab.setOpaque(true);
				lab.setText("Cab #" + ti + "\nBestemming: -");
				lab.setUI(new MultiLineLabelUI());
				lab.setBorder(new LineBorder(Color.gray, 1));
			}else{
				cinfo.get(ti).setVisible(false);
			}

			ti++;
         }// Einde for-each cab lus

		// Teken wissels
		for (int i = 0; i <= 7; i++) {
			if (ReisBeheer.haltes.get(i).wissel.status()) {
				switchicons.get(i).paintIcon(this, g, wisx[i], wisy[i]);
			}
		}
		// Teken sensors
		for (CabAnimation cab : TreinBeheer.treinen) {
			int i = cab.sensor.check() - 1;
			if (i != -1 && cab.zichtbaar) {
				sensoricons.get(i).paintIcon(this, g, senx[i], seny[i]);
			}
		}
		setClock();
		setTreinText();
		setLabelText();
		Tabel2.reload();
	}// Einde repaint
	public static void setClock() {
		String add;
		if (RailsApp.min < 10){
			add = "0";
		}else{
			add = "";
		}
		if (RailsApp.min%2 == 1){
			klok.setText(RailsApp.uur + ":" + add + RailsApp.min);		
		}else{
			klok.setText(RailsApp.uur + " " + add + RailsApp.min);
		}
		
	}
	public static void setLabelText() {
		for (int i =0 ; i <= 7; i++) {
		int rsize = ReisBeheer.haltes.get(i).getAantalWachtenden();
		String lw;
		if (rsize > 0){
			lw = "" + ReisBeheer.haltes.get(i).reizen.getLast().wachttijd;
		}else{
			lw = "-";
		}
			sinfo.get(i).setText(
					"Wachtenden: " + rsize
							+ "\nLngst Wachtend: " + lw);
		}
	}
	public static void setTreinText(){
		int j = 0;
		for (Trein tr: TreinBeheer.treinen) {
			if (tr.bestemming == 0){
				cinfo.get(j).setText("Cab #" + j + "\nBestemming: -");	
			}else{
			cinfo.get(j).setText("Cab #" + j + "\nBestemming: #" + tr.bestemming);
			}
			j++;
		}
	}

	// ActionListeners van de Station Knoppen
	class statHandler implements ActionListener {
		private String station;

		public statHandler(String naam) {
			station = naam;
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("1");
			Station.setStation(station, 0, 0);
		}
	}

}
