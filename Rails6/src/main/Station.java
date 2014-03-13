package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

//import javax.swing.border.EmptyBorder;

public class Station extends JPanel {
	public static JLabel stationNaam;
	public static JLabel wachtenden;
	public static JLabel grtBest;
	private static JButton rtoevoegen;
	private static JButton raanpassen;
	private static JButton rweergeven;
	public static Station2 stat;
	Font f1 = new Font("SansSerif", Font.BOLD, 26);
	final Border selectedBorder = new EtchedBorder();

	public Station() {
		setBackground(Color.WHITE);
		this.setLayout(null);

		stationNaam = new JLabel();
		stationNaam.setFont(f1);
		stationNaam.setBorder(selectedBorder);
		stationNaam.setBounds(0, 0, 150, 40);
		stationNaam.setText("Station 1");

		wachtenden = new JLabel();
		wachtenden.setText("Aantal wachtenden: ");
		wachtenden.setBounds(0, 45, 150, 20);

		grtBest = new JLabel();
		grtBest.setText("Grootste bestemming: ");
		grtBest.setBounds(0, 65, 150, 20);

		rtoevoegen = new JButton("Reis toevoegen");
		rtoevoegen.setBounds(5, 140, 140, 20);
		rtoevoegen.addActionListener(new reisToevoegen());

		raanpassen = new JButton("Statistieken");
		raanpassen.setBounds(5, 180, 140, 20);
		raanpassen.addActionListener(new reisAanpassen());
		raanpassen.setEnabled(true);

		rweergeven = new JButton("Reizen weergeven");
		rweergeven.setBounds(5, 160, 140, 20);
		rweergeven.addActionListener(new reizenWeergeven());

		add(stationNaam);
		add(wachtenden);
		add(grtBest);
		add(rtoevoegen);
		add(raanpassen);
		add(rweergeven);

		maakZichtbaar(1);
	}

	public void setNaam(int i) {
		switch (i) {
		case 1:
			stationNaam.setText("Station 1");
		case 2:
			stationNaam.setText("Station 2");
		case 3:
			stationNaam.setText("Station 3");
		case 4:
			stationNaam.setText("Station 4");
		case 5:
			stationNaam.setText("Station 5");
		case 6:
			stationNaam.setText("Station 6");
		case 7:
			stationNaam.setText("Station 7");
		case 8:
			stationNaam.setText("Station 8");
		default:
			stationNaam.setText("Station 1");
		}
	}

	public static void setStation(String naam, int wacht, int best) {
		stationNaam.setText(naam);
		wachtenden.setText("Aantal wachtenden: " + wacht);
		grtBest.setText("Grootste bestemming: " + best);
		System.out.println("Stationnaam = " + stationNaam.getText());
		Station2.ranj.setToolTipText("dat is : "
				+ Station.stationNaam.getText());
	}

	class reisToevoegen implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Station2.maakZichtbaar(1);
			raanpassen.setEnabled(true);
		}
	}

	class reisAanpassen implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Station2.maakZichtbaar(5);
		}
	}

	class reizenWeergeven implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Station2.maakZichtbaar(3);
			raanpassen.setEnabled(true);
		}
	}

	public static void maakZichtbaar(int waar) {
		switch (waar) {
		case 1: {
			// Station scherm zichtbaar
			stationNaam.setVisible(true);
			wachtenden.setVisible(true);
			grtBest.setVisible(true);
			rtoevoegen.setVisible(true);
			raanpassen.setVisible(true);
			rweergeven.setVisible(true);
		}
			break;
		case 2: {
			// leeg
			stationNaam.setVisible(false);
			wachtenden.setVisible(false);
			grtBest.setVisible(false);
			rtoevoegen.setVisible(false);
			raanpassen.setVisible(true);
			rweergeven.setVisible(false);

		}
			break;
		case 3: {
			// trein scherm zichtbaar
			stationNaam.setVisible(true);
			wachtenden.setVisible(false);
			grtBest.setVisible(false);
			rtoevoegen.setVisible(false);
			raanpassen.setVisible(false);
			rweergeven.setVisible(false);
		}
			break;
		}
	}
}
