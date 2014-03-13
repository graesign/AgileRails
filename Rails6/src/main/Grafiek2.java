package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import javax.swing.*;

/**
 * A simple demonstration application showing how to create a bar chart.
 * 
 */
public class Grafiek2 extends JPanel {
	public DefaultCategoryDataset dataset;
	public JFreeChart chart;
	public ChartPanel chartPanel;
	/**
	 * Creates a new demo instance.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public Grafiek2() {


		dataset = createDataset();
		chart = createChart(dataset,"x","y","name");
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(800, 300));
		add(chartPanel);

	}
	public Grafiek2(DefaultCategoryDataset data,String x, String y, String name) {


		dataset = data;
		chart = createChart(dataset,x,y,name);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(800, 300));
		add(chartPanel);

	}

	/**
	 * Returns a sample dataset.
	 * 
	 * @return The dataset.
	 */
	private DefaultCategoryDataset createDataset() {

		// row keys...
		final String series1 = "First";
		// final String series2 = "Second";
		// final String series3 = "Third";

		// column keys...
		final String category1 = "Station 1";
		final String category2 = "Station 2";
		final String category3 = "Station 3";
		final String category4 = "Station 4";
		final String category5 = "Station 5";
		final String category6 = "Station 6";
		final String category7 = "Station 7";
		final String category8 = "Station 8";

		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(20.0, series1, category1);
		dataset.addValue(4.0, series1, category2);
		dataset.addValue(3.0, series1, category3);
		dataset.addValue(5.0, series1, category4);
		dataset.addValue(5.0, series1, category5);
		dataset.addValue(5.0, series1, category6);
		dataset.addValue(5.0, series1, category7);
		dataset.addValue(5.0, series1, category8);
		return dataset;

	}

 
	  /** Creates a sample chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return The chart.
	 */
	private JFreeChart createChart(final CategoryDataset dataset, String x, String y, String name) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart(name, // chart
				// title
				x, // domain axis label
				y, // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		final BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// set up gradient paints for series...
		final GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
				0.0f, 0.0f, Color.lightGray);
		final GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green,
				0.0f, 0.0f, Color.lightGray);
		final GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,
				0.0f, 0.0f, Color.lightGray);
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 6.0));
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}
}

// ****************************************************************************
// * JFREECHART DEVELOPER GUIDE *
// * The JFreeChart Developer Guide, written by David Gilbert, is available *
// * to purchase from Object Refinery Limited: *
// * *
// * http://www.object-refinery.com/jfreechart/guide.html *
// * *
// * Sales are used to provide funding for the JFreeChart project - please *
// * support us so that we can continue developing free software. *
// ****************************************************************************

/**
 * Starting point for the demonstration application.
 * 
 * @param args
 *            ignored.
 */
/**
 * public static void main(final String[] args) {
 * 
 * final BarChartDemo demo = new BarChartDemo("Bar Chart Demo"); demo.pack();
 * RefineryUtilities.centerFrameOnScreen(demo); demo.setVisible(true); }
 */

