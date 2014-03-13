package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.util.ArrayList;
import java.util.Date;

public class Station2 extends JPanel {

	private static final long serialVersionUID = 1L;

	// maak de objecten aan.
	private static JLabel tekst, t1, t2, t3, t4;
	private static JComboBox bestemming, vertrek, tijdu, tijdm, personen;
	private static JButton oke, annul, verwijder, random;
	public static JCheckBox ranj, nuu;
	private static Tabel2 tabell;
	public static Statistiek statt;
	private static int zicht;

	int[] trix = { 10, 220, 450, 680, 10, 220, 450, 680 };
	int[] triy = { 10, 10, 10, 10, 120, 120, 120, 120 };
	private static ArrayList<Train> treinen = new ArrayList<Train>();

	Font f1 = new Font("SansSerif", Font.BOLD, 26);
	Font f2 = new Font("SansSerif", Font.BOLD, 14);
	Font f3 = new Font("SansSerif", Font.BOLD, 12);
	Font f4 = new Font("SansSerif", Font.PLAIN, 12);
	final Border selectedBorder = new EtchedBorder();

	public Station2() {
		setBackground(Color.WHITE);
		setLayout(null);

		// stel tekst van paneel in.
		tekst = new JLabel();
		tekst.setFont(f1);
		tekst.setBounds(30, 10, 600, 40);

		// stel label bestemming in
		t1 = new JLabel("Bestemming:");
		t1.setFont(f2);
		t1.setBounds(40, 80, 100, 20);

		// stel combobox bestemming in
		bestemming = new JComboBox();
		bestemming.setFont(f2);
		bestemming.addItem(makeObj("Selecteer Station"));
		for (int x = 1; x < 9; x++) {
			bestemming.addItem(makeObj("Station " + x));
		}
		bestemming.setEditable(true);
		bestemming.setBounds(150, 80, 300, 20);

		// stel label vertrekstation in
		t2 = new JLabel("Vertrek: ");
		t2.setFont(f2);
		t2.setBounds(40, 50, 100, 20);

		// stel combobox vertrekstation in
		vertrek = new JComboBox();
		vertrek.setFont(f2);
		vertrek.addItem(makeObj("Selecteer Station"));
		for (int x = 1; x < 9; x++) {
			vertrek.addItem(makeObj("Station " + x));
		}
		vertrek.setBounds(150, 50, 300, 20);
		vertrek.setEditable(true);
		vertrek.setEnabled(true);

		// stel label tijd in
		t3 = new JLabel("Tijd: ");
		t3.setFont(f2);
		t3.setBounds(40, 110, 100, 20);

		// stel combobox tijdu(uur) in
		tijdu = new JComboBox();
		tijdu.setFont(f2);
		tijdu.setBounds(150, 110, 80, 20);
		tijdu.addItem(makeObj("Uur"));
		for (int i = 00; i < 24; i++) {
			String uur;
			if (i < 10) {
				uur = ("0" + i);
			} else {
				uur = ("" + i);
			}
			tijdu.addItem(makeObj("" + uur));
		}
		tijdu.setEditable(true);

		// stel combobox tijdm(minuten) in
		tijdm = new JComboBox();
		tijdm.setFont(f2);
		tijdm.setBounds(240, 110, 80, 20);
		tijdm.addItem(makeObj("Min"));
		for (int j = 00; j < 60; j++) {
			String min;
			if (j < 10) {
				min = ("0" + j);
			} else {
				min = ("" + j);
			}
			tijdm.addItem(makeObj("" + min));
		}
		tijdm.setEditable(true);

		// stel label passagiers in
		t4 = new JLabel("Passagiers");
		t4.setFont(f2);
		t4.setBounds(40, 140, 100, 20);

		// stel combobox personen in
		personen = new JComboBox();
		personen.setFont(f2);
		personen.setBounds(150, 140, 300, 20);
		personen.addItem(makeObj("Aantal"));
		for (int i = 1; i <= 8; i++) {
			personen.addItem(makeObj("" + i));
		}
		personen.setEditable(true);

		// stel knop oke in
		oke = new JButton();
		oke.setFont(f2);
		oke.setBounds(800, 40, 170, 30);
		oke.setText("OK");
		oke.addActionListener(new knopOke());

		// stel knop annuleren in
		annul = new JButton();
		annul.setFont(f2);
		annul.setBounds(800, 80, 170, 30);
		annul.setText("Annuleren");
		annul.addActionListener(new knopAnn());

		// stel knop verwijder in
		verwijder = new JButton();
		verwijder.setFont(f2);
		verwijder.setBounds(800, 120, 170, 30);
		verwijder.setText("Verwijderen");
		verwijder.setVisible(false);
		verwijder.addActionListener(new knopVerw());

		// stel knop random in
		random = new JButton();
		random.setFont(f2);
		random.setBounds(40, 190, 100, 30);
		random.setText("Random");
		random.addActionListener(new knopRand());

		// Checkbox voor random vanaf dit station instellen
		ranj = new JCheckBox("Random vanaf dit station?");
		ranj.setToolTipText("dat is : " + Station.stationNaam.getText());
		ranj.setSelected(true);
		ranj.setBounds(40, 225, 200, 20);

		// Checkbox voor tijd nu instellen
		nuu = new JCheckBox("Nu meteen vertrekken!");
		nuu.setToolTipText("(NB, dit is over 5 min!)");
		nuu.setSelected(false);
		nuu.setBounds(40, 245, 300, 30);

		tabell = new Tabel2();
		tabell.setBounds(10, 50, 900, 230);
		tabell.setBorder(selectedBorder);
		tabell.setFont(f4);

		statt = new Statistiek();
		statt.setBounds(0, 0, 980, 280);

		for (int i = 0; i < 8; i++) {
			Train init = new Train(i);
			treinen.add(init);
			init.setBounds(trix[i], triy[i], 200, 100);
			add(init);
		}

		// plaats de objecten op de panel
		add(tekst);
		add(t1);
		add(bestemming);
		add(t2);
		add(vertrek);
		add(t3);
		add(tijdu);
		add(tijdm);
		add(t4);
		add(personen);
		add(oke);
		add(annul);
		add(verwijder);
		add(random);
		add(ranj);
		add(nuu);
		add(tabell);
		add(statt);
		maakZichtbaar(5);
		zicht = 4;
	}

	public static void maakZichtbaar(int waar) {
		switch (waar) {
		case 1: {
			// scherm toevoegen zichtbaar
			zicht = 1;
			tekst.setText("reis toevoegen");
			tekst.setVisible(true);
			t1.setVisible(true);
			bestemming.setVisible(true);
			t2.setVisible(true);
			vertrek.setVisible(true);
			t3.setVisible(true);
			tijdu.setVisible(true);
			tijdm.setVisible(true);
			t4.setVisible(true);
			personen.setVisible(true);
			oke.setVisible(true);
			annul.setVisible(true);
			verwijder.setVisible(false);
			random.setVisible(true);
			ranj.setVisible(true);
			nuu.setVisible(true);
			tabell.setVisible(false);
			for (Train trein : treinen) {
				trein.setVisible(false);
			}
			statt.setVisible(false);
			break;
		}

		case 2: {
			// leeg
			zicht = 2;
			tekst.setVisible(false);
			t1.setVisible(false);
			bestemming.setVisible(false);
			t2.setVisible(false);
			vertrek.setVisible(false);
			t3.setVisible(false);
			tijdu.setVisible(false);
			tijdm.setVisible(false);
			t4.setVisible(false);
			personen.setVisible(false);
			oke.setVisible(false);
			annul.setVisible(false);
			verwijder.setVisible(false);
			random.setVisible(false);
			ranj.setVisible(false);
			nuu.setVisible(false);
			tabell.setVisible(false);
			for (Train trein : treinen) {
				trein.setVisible(false);
			}
			statt.setVisible(false);
			break;
		}

		case 3: {
			// reistabel zichtbaar
			zicht = 3;
			tekst.setText("Reizen Weergeven");
			tekst.setVisible(true);
			t1.setVisible(false);
			bestemming.setVisible(false);
			t2.setVisible(false);
			vertrek.setVisible(false);
			t3.setVisible(false);
			tijdu.setVisible(false);
			tijdm.setVisible(false);
			t4.setVisible(false);
			personen.setVisible(false);
			oke.setVisible(false);
			annul.setVisible(false);
			verwijder.setVisible(false);
			random.setVisible(false);
			ranj.setVisible(false);
			nuu.setVisible(false);
			tabell.setVisible(true);
			for (Train trein : treinen) {
				trein.setVisible(false);
			}
			statt.setVisible(false);
			break;
		}

		case 4: {
			// Treinen Zichtbaar
			zicht = 4;
			tekst.setText("");
			tekst.setVisible(false);
			t1.setVisible(false);
			bestemming.setVisible(false);
			t2.setVisible(false);
			vertrek.setVisible(false);
			t3.setVisible(false);
			tijdu.setVisible(false);
			tijdm.setVisible(false);
			t4.setVisible(false);
			personen.setVisible(false);
			oke.setVisible(false);
			annul.setVisible(false);
			verwijder.setVisible(false);
			random.setVisible(false);
			ranj.setVisible(false);
			nuu.setVisible(false);
			tabell.setVisible(false);
			for (Train trein : treinen) {
				trein.setVisible(true);
			}
			statt.setVisible(false);
			break;
		}
		case 5: {
			// Treinen Zichtbaar
			zicht = 5;
			tekst.setText("");
			tekst.setVisible(false);
			t1.setVisible(false);
			bestemming.setVisible(false);
			t2.setVisible(false);
			vertrek.setVisible(false);
			t3.setVisible(false);
			tijdu.setVisible(false);
			tijdm.setVisible(false);
			t4.setVisible(false);
			personen.setVisible(false);
			oke.setVisible(false);
			annul.setVisible(false);
			verwijder.setVisible(false);
			random.setVisible(false);
			ranj.setVisible(false);
			nuu.setVisible(false);
			tabell.setVisible(false);
			for (Train trein : treinen) {
				trein.setVisible(false);
			}
			statt.setVisible(true);
			break;
		}

		}
	}

	public static void update() {
		for (int x = 0; x < treinen.size(); x++) {
			treinen.get(x).setPositie(TreinBeheer.treinen.get(x).positie);
			treinen.get(x).setReisID(TreinBeheer.treinen.get(x).id);
			treinen.get(x).setSnelheid(
					("" + TreinBeheer.treinen.get(x).running));
			System.out.println("pos trein: " + (x) + " = "
					+ TreinBeheer.treinen.get(x).positie);
			System.out.println("Rid trein: " + (x) + " = "
					+ TreinBeheer.treinen.get(x).id);
			System.out.println("Status trein: " + (x) + " = "
					+ TreinBeheer.treinen.get(x).running);
		}
	}

	// geeft de bestemming in een String
	public String getBestemming() {
		String best;
		best = ("" + bestemming.getSelectedItem());
		return best;
	}

	public int getBestemmingid() {
		int best;
		best = bestemming.getSelectedIndex();
		return best;
	}

	// geeft de vertrekstation in een String
	public String getVertrek() {
		String vert;
		vert = ("" + vertrek.getSelectedItem());
		return vert;
	}

	public int getVertrekid() {
		int vert;
		vert = vertrek.getSelectedIndex();
		return vert;
	}

	// geeft de tijd in een String
	public String getTijd() {
		String tijd;
		tijd = ("" + tijdu.getSelectedItem() + ":" + tijdm.getSelectedItem());
		return tijd;
	}

	public int getUurID() {
		int uur;

		if (tijdu.getSelectedIndex() == 0) {
			uur = 100;
		} else {
			uur = tijdu.getSelectedIndex();
		}
		return uur;
	}

	public int getMinID() {
		int min;

		if (tijdm.getSelectedIndex() == 0) {
			min = 100;
		} else {
			min = tijdm.getSelectedIndex();
		}
		return min;
	}

	// geeft het aantal personen in een String
	public String getPersonen() {
		String pers;
		pers = ("" + personen.getSelectedItem());
		return pers;
	}

	public int getPersonenID() {
		int pers = personen.getSelectedIndex();
		return pers;
	}

	// handler voor de OKE knop
	class knopOke implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Dit stukje code zorgt ervoor dat in het scherm reis toevoegen
			// alle velden worden ingevuld!
			if (zicht == 1) {
				if ((bestemming.getSelectedIndex() == 0)
						|| (vertrek.getSelectedIndex() == 0)
						|| (personen.getSelectedIndex() == 0)) {
					JOptionPane.showMessageDialog(personen,
							"Alle velden moeten ingevuld worden", "FOUT",
							JOptionPane.WARNING_MESSAGE);
				}
				if ((bestemming.getSelectedIndex()) == (vertrek
						.getSelectedIndex())) {
					JOptionPane.showMessageDialog(personen,
							"Bestemming kan niet het vertrek station zijn!",
							"FOUT", JOptionPane.WARNING_MESSAGE);
				} else {
					System.out.println(getBestemming());
					System.out.println(getVertrek());
					System.out.println(getTijd());
					System.out.println(getPersonen());

					int size = ReisBeheer.reizen.size();

					// ReisBeheer.addReis(getPersonenID(),ReisBeheer.haltes.get(getVertrekid()),ReisBeheer.haltes.get(getBestemmingid()),new
					// int[]{getUurID(),getMinID()});
					ReisBeheer.addReis(getPersonenID(), getVertrekid() - 1,
							getBestemmingid() - 1, new int[] { getUurID() - 1,
									getMinID() - 1 });

					System.out.println("Aantal wachtenden station 5: "
							+ ReisBeheer.getWacht(5));
					// System.out.println("Aantal mensen wachtend in station 5:
					// "+ ReisBeheer.haltes.get(5).getAantalWachtenden());
					RailScherm.setLabelText();
				}

				// dit stukje code word uitgevoerd als je op ok klikt in het
				// reizen weergeven scherm.
				if (zicht == 3) {
					System.out.println("Oke Opgeslagen");
				}
			}
		}
	}

	// handler voor de annuleren knop
	class knopAnn implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			maakZichtbaar(2);
			bestemming.setSelectedIndex(0);
			vertrek.setSelectedIndex(0);
			tijdu.setSelectedIndex(0);
			tijdm.setSelectedIndex(0);
			personen.setSelectedIndex(0);
		}
	}

	class knopVerw implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tabell.removeRow();
			RailScherm.setLabelText();
		}
	}

	class knopRand implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int x;
			String s = (String) JOptionPane.showInputDialog(random,
					"Hoeveel reizen wilt u aanmaken?\n", "Hoeveel?",
					JOptionPane.YES_NO_CANCEL_OPTION, null, null, null);
			try {
				x = Integer.parseInt(s, 10);
				if (x > 0 && x <= 200) {
					for (int i = 0; i < x; i++) {
						String vertre;
						String tijd;
						int ver = 1;
						int tijdmm = getCompMin(ver);
						int tijduu = getCompUur();
						String bestem = getRandomBestemming();
						if (ranj.isSelected()) {
							vertre = ("" + Station.stationNaam.getText()
									.charAt(8));
						} else {
							vertre = ("" + getRandomVertrekID());
						}
						if (vertre.equals(bestem))
							bestem = getRandomBestemming();
						if (nuu.isSelected() == true) {
							tijd = getComputerTijd(ver);
							tijdmm = 99;
							tijduu = 99;
						} else {
							tijd = (tijdu + ":" + tijdm);
							tijdmm = getRandomMin();
							tijduu = getRandomUur();
						}
						String pasa = getRandomPersonen();
						System.out.println("Bestemming = " + bestem);
						System.out.println("Vertrek = " + vertre);
						System.out.println("Tijd = " + tijd);
						System.out.println("Aantal pasagiers = " + pasa);
						System.out.println("-----------------------------");
						ReisBeheer.addReis(Integer
								.parseInt(getRandomPersonen()), Integer
								.parseInt(vertre) - 1,
								getRandomBestemmingID() - 1, new int[] {
										tijduu, tijdmm });
						RailScherm.setLabelText();
					}
				}
				if (x > 200) {
					JOptionPane.showMessageDialog(personen,
							"U mag geen getal groter dan 200 invullen",
							"Ongeldige invoer", JOptionPane.WARNING_MESSAGE);
				}
				if (x <= 0) {
					JOptionPane.showMessageDialog(personen,
							"U moet een getal groter dan 0 invoeren",
							"Ongeldige invoer", JOptionPane.WARNING_MESSAGE);
				}
			} catch (NumberFormatException nFE) {
				JOptionPane.showMessageDialog(personen,
						"U moet een getal invoeren!", "Ongeldige invoer",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public String getComputerTijd(int min) {
		Date nu = new Date();
		String tijde;
		String tijdu;
		String tijdm;
		int uur = nu.getHours();
		int minuut = nu.getMinutes() + min;
		if (minuut >= 60) {
			minuut = (minuut - 60);
			uur = (uur + 1);
			if (uur > 23) {
				uur = 0;
			}
		}
		if (minuut < 10) {
			tijdm = ("0" + minuut);
		} else {
			tijdm = ("" + minuut);
		}
		if (uur < 10) {
			tijdu = ("0" + uur);
		} else {
			tijdu = ("" + uur);
		}
		tijde = (tijdu + ":" + (tijdm));
		return tijde;
	}

	public int getCompMin(int min) {
		Date nu = new Date();
		int tijdm = (nu.getMinutes() + min);
		return tijdm;
	}

	public int getCompUur() {
		Date nu = new Date();
		int tijdu = nu.getHours();
		return tijdu;
	}

	// maakt een random bestemming aan.
	public String getRandomBestemming() {
		String bestemming = ("Station " + getRandomBestemmingID());
		return bestemming;
	}

	public int getRandomBestemmingID() {
		int bestemming = ((int) (8 * Math.random()) + 1);
		return bestemming;
	}

	public String getRandomVertrek() {
		String vertrek = ("Station " + getRandomVertrekID());
		return vertrek;
	}

	public int getRandomVertrekID() {
		int vertrek = ((int) (8 * Math.random()) + 1);
		return vertrek;
	}

	public String getRandomTijd() {
		String tijd;
		// String tijdu;
		// String tijdm;
		// int tijduu = ((int)(24*Math.random()));
		// int tijdmm = ((int)(60*Math.random()));
		// if(tijduu<10)
		// tijdu = ("0" + tijduu);
		// else
		// tijdu = (""+tijduu);
		// if(tijdmm<10)
		// tijdm = ("0" + tijdmm);
		// else
		// tijdm = (""+tijdmm);
		// tijd = (tijdu + ":" +tijdm);
		tijd = (getRandomUur() + ":" + getRandomMin());
		return tijd;
	}

	public int getRandomUur() {
		String tijdu;
		int uur;
		int tijduu = ((int) (24 * Math.random()));
		if (tijduu < 10)
			tijdu = ("0" + tijduu);
		else
			tijdu = ("" + tijduu);
		uur = (0 + Integer.parseInt(tijdu, 10));
		return uur;
	}

	public int getRandomMin() {
		int min;
		String tijdm;
		int tijdmm = ((int) (60 * Math.random()));
		if (tijdmm < 10)
			tijdm = ("0" + tijdmm);
		else
			tijdm = ("" + tijdmm);
		min = (0 + Integer.parseInt(tijdm, 10));

		return min;
	}

	public String getRandomPersonen() {
		String pasa = ("" + ((int) (8 * Math.random()) + 1));
		return pasa;
	}

	// zorgt ervoor dat er objecten in de comboboxen geplaatst kunnen worden

	private static Object makeObj(final String item) {
		return new Object() {
			public String toString() {
				return item;
			}
		};
	}

}
