import java.awt.*;
import javax.swing.*;

public class Train extends JPanel {
	private static JLabel treinID;
	private static JLabel snelHeid;
	private static JLabel positie;
	private static JLabel reisID;
	
	public static int posi, ID, sp, rID;
	Font f1 = new Font("SansSerif", Font.BOLD, 26);

	public Train(int ID) {
		setLayout(null);

		int sp = 5;
		int rID = ID;
		int pos = 3;

		treinID = new JLabel();
		treinID.setBounds(10, 10, 200, 30);
		treinID.setFont(f1);
		treinID.setText("Cab #" + ID);

		snelHeid = new JLabel();
		snelHeid.setBounds(10, 45, 200, 10);
		snelHeid.setText("Snelheid: " + sp);

		positie = new JLabel();
		positie.setBounds(10, 60, 200, 10);
		positie.setText("Positie: " + posi);

		reisID = new JLabel();
		reisID.setBounds(10, 75, 200, 10);
		reisID.setText("Reis ID: " + rID);

		add(treinID);
		add(snelHeid);
		add(positie);
		add(reisID);
	}

	public Train() {
		setLayout(null);

		treinID = new JLabel();
		treinID.setBounds(10, 10, 200, 30);
		treinID.setFont(f1);
		treinID.setText("Cab #");

		snelHeid = new JLabel();
		snelHeid.setBounds(10, 45, 200, 10);
		snelHeid.setText("Snelheid: ");

		positie = new JLabel();
		positie.setBounds(10, 60, 200, 10);
		positie.setText("Positie: ");

		reisID = new JLabel();
		reisID.setBounds(10, 75, 200, 10);
		reisID.setText("Reis ID: ");

		add(treinID);
		add(snelHeid);
		add(positie);
		add(reisID);

	}

	public void setTreinID(int ID) {
		this.ID = ID;
		treinID.setText("Cab #" + ID);
	}

	public static void setPositie(double pos) {
		posi = ((int)pos / 1);
		positie.setText("Positie: " + pos);
	}

	public void setReisID(int rID) {
		this.rID = rID;
		reisID.setText("Reis ID: " + rID);
	}

	public void setSnelheid(String string) {
		snelHeid.setText("Snelheid: " + string);
	}
}
