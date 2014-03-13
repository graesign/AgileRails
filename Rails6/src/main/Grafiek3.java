package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities; //import org.jfree.ui.Spacer;
import javax.swing.*;

/**
 * A simple demonstration application showing how to create a line chart using
 * data from an {@link XYDataset}.
 * 
 */
public class Grafiek3 extends JPanel {
	public XYDataset dataset;
	public JFreeChart chart;
	public ChartPanel chartPanel;
	private ReisBeheer rb;
	private TreinBeheer tb;
	private int teller = 0;
	XYSeries series1, series2, series3, series4;

	/**
	 * Creates a new demo.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public Grafiek3(XYDataset data, String x, String y, String name) {

		dataset = data;
		chart = createChart(dataset, x, y, name);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(980, 280));
		add(chartPanel);
		series1 = new XYSeries("reizen");
		series2 = new XYSeries("treinen");
		series3 = new XYSeries("gemiddelde wachttijd");
		series4 = new XYSeries("gemiddelde reistijd");

	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return a sample dataset.
	 * 
	 */

	public XYSeriesCollection createDataset(XYSeriesCollection dataset) {

		series1.add(teller, rb.getActieveReizen().size());
		series2.add(teller, tb.treinen.size());
		series3.add(teller, rb.gemWachttijd());
		series4.add(teller, rb.gemReisttijd());

		teller++;
		dataset.removeAllSeries();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		dataset.addSeries(series4);
		return dataset;

	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            the data for the chart.
	 * 
	 * @return a chart.
	 */
	private JFreeChart createChart(final XYDataset dataset, String x, String y,
			String name) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createXYLineChart(name, // chart
																		// title
				x, // x axis label
				y, // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.white);

		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);

		// get a reference to the plot for further customisation...
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.white);
		// plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.red);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(1, false);
		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}

}
