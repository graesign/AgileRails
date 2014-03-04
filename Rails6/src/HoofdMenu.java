import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Class HoofdMenu - write a description of the class here
 * 
 * @author (your name)
 * @version (a version number)
 */
public class HoofdMenu extends JPanel {
	private JButton baan;
	private JButton overzicht;
	private JButton statistiek;
	private JButton trein;

	public HoofdMenu() {
		setBackground(Color.WHITE);
		setLayout(null);

		baan = new JButton("Baan");
		baan.setBounds(10, 200, 120, 20);
		baan.addActionListener(new knopBaan());
		baan.setVisible(false);

		overzicht = new JButton("Overzicht");
		overzicht.setBounds(10, 10, 120, 20);
		overzicht.addActionListener(new knopOverzicht());
		overzicht.setVisible(false);

		statistiek = new JButton("Statistiek");
		statistiek.setBounds(10, 230, 120, 20);
		statistiek.addActionListener(new knopStat());
		statistiek.setVisible(false);

		trein = new JButton("Trein");
		trein.setBounds(10, 40, 120, 20);
		trein.addActionListener(new knopTrein());
		trein.setVisible(false);

		add(baan);
		add(overzicht);
		add(statistiek);
		add(trein);
	}

	// Handler voor knop Baan
	class knopBaan implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Station2.maakZichtbaar(2);
			Station.maakZichtbaar(1);

			Station.setStation("Station 1", 100, 6);
			baan.setBackground(Color.GREEN);
			statistiek.setBackground(null);
		}
	}

	// handler voor knop Overzicht
	class knopOverzicht implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Station.maakZichtbaar(2);
			Station2.maakZichtbaar(2);

			overzicht.setBackground(Color.GREEN);
			trein.setBackground(null);
		}
	}

	// handler voor knop Statistiek
	class knopStat implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Station2.maakZichtbaar(5);
			Station.maakZichtbaar(2);
			baan.setBackground(null);
			statistiek.setBackground(Color.GREEN);
		}
	}

	// Handler voor knop Trein
	class knopTrein implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Station2.maakZichtbaar(4);
			Station.maakZichtbaar(3);
			Station.setStation("", 0, 0);
			Station2.update();
			trein.setBackground(Color.GREEN);
			overzicht.setBackground(null);
		}
	}
}
