package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.Timer;

public class Statistiek extends JPanel {

	public static JComboBox cmbx, cmby, cmbGrap;
	public static JLabel lblx, lbly, lblGrap;
	public static JButton oke;
	// public static Grafiek1 stat1;
	public static Grafiek3 stat2;
	public static DefaultCategoryDataset dataset;
	public static XYSeriesCollection form;

	public Statistiek() {
		setLayout(null);
		form = new XYSeriesCollection();
		stat2 = new Grafiek3(form, "Tijd", "Aantal", "Voortgang");
		stat2.setBounds(0, 0, 980, 280);
		stat2.setVisible(true);
		add(stat2);
	}

	public static void refresh() {
		form = stat2.createDataset(form);
		stat2.dataset = form;
		stat2.setBounds(0, 0, 980, 280);
	}

}
