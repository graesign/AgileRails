package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SchermTwee extends JPanel {
	private HoofdMenu men;
	private JLabel tekst;

	public SchermTwee() {
		setLayout(null);
		men = new HoofdMenu();
		tekst = new JLabel("HOI");
		tekst.setBounds(0, 0, 120, 20);

		add(tekst);

	}

}
