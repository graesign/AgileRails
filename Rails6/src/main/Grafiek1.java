package main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Log;
import org.jfree.util.PrintStreamLogTarget;
import javax.swing.*;

/**
 * A simple demonstration application showing how to create a horizontal 3D bar
 * chart using data from a {@link CategoryDataset}.
 * 
 */
public class Grafiek1 extends JPanel {

	public static DefaultCategoryDataset form,dataset;
	public static JFreeChart chart;
	public static ChartPanel chartPanel;
	


	// ****************************************************************************
	// * JFREECHART DEVELOPER GUIDE *
	// * The JFreeChart Developer Guide, written by David Gilbert, is available
	// *
	// * to purchase from Object Refinery Limited: *
	// * *
	// * http://www.object-refinery.com/jfreechart/guide.html *
	// * *
	// * Sales are used to provide funding for the JFreeChart project - please *
	// * support us so that we can continue developing free software. *
	// ****************************************************************************

	/**
	 * Creates a new demo.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public Grafiek1() {



		// create the chart...
		dataset = new DefaultCategoryDataset();
		dataset.addValue(23.0, "Aantal Reizen 4", "Trein 1");
		dataset.addValue(0.0, "1", "1");
		dataset.addValue(14.0, "Aantal Reizen 2", "Trein 2");
		dataset.addValue(0.0, "", "");
		dataset.addValue(14.0, "Aantal Reizen 2", "Trein 3");
		dataset.addValue(0.0, "", "");
		dataset.addValue(14.0, "Aantal Reizen 2", "Trein 4");
		dataset.addValue(0.0, "", "");
		dataset.addValue(13.0, "Aantal Reizen 2", "Trein 5");
		dataset.addValue(0.0, "", "");
		dataset.addValue(19.0, "Aantal Reizen 2", "Trein 6");
		dataset.addValue(0.0, "", "");
		dataset.addValue(19.0, "Aantal Reizen 2", "Trein 7");
		dataset.addValue(0.0, "", "");
		dataset.addValue(19.0, "Aantal Reizen 2", "Trein 8");
		dataset.addValue(0.0, "", "");
		dataset.addValue(7.0, "Aantal Reizen 2", "Trein 9");
		dataset.addValue(0.0, "", "");
		dataset.addValue(9.0, "Aantal Reizen 2", "Trein 10");
		dataset.addValue(0.0, "", "");
		dataset.addValue(9.0, "Aantal Reizen 2", "Trein 11");
		dataset.addValue(0.0, "", "");
		dataset.addValue(9.0, "Aantal Reizen 2", "Trein 12");
		dataset.addValue(0.0, "", "");
		dataset.addValue(9.0, "Aantal Reizen 2", "Trein 13");
		dataset.addValue(0.0, "", "");
		dataset.addValue(9.0, "Aantal Reizen 2", "Trein 14");
		dataset.addValue(0.0, "", "");
		dataset.addValue(9.0, "Aantal Reizen 2", "Trein 15");
		dataset.addValue(0.0, "", "");
		dataset.addValue(9.0, "Aantal Reizen 2", "Trein 16");
		dataset.addValue(0.0, "", "");
		chart = createChart(dataset,"x","y","hoi");

		// add the chart to a panel...
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
		add(chartPanel);

	}

	public Grafiek1(DefaultCategoryDataset data,String x, String y, String name) {

	//	super(title);

		// create the chart...
		form = new DefaultCategoryDataset();
		form = data;

		chart = createChart(form,x,y,name);

		// add the chart to a panel...
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(800, 300));
		add(chartPanel);

	}

	public void setDataset(DefaultCategoryDataset datas) {
		dataset = datas;
	//	form.clear();
		form = dataset;
		
		
	}


	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return The chart.
	 */
	private JFreeChart createChart(final CategoryDataset dataset, String x, String y, String name) {

		final JFreeChart chart = ChartFactory.createBarChart3D(name, // chart
				// title
				x, // domain axis label
				y, // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);

		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setForegroundAlpha(1.0f);

		// left align the category labels...
		final CategoryAxis axis = plot.getDomainAxis();
		final CategoryLabelPositions p = axis.getCategoryLabelPositions();

		final CategoryLabelPosition left = new CategoryLabelPosition(
				RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT,
				TextAnchor.CENTER_LEFT, 0.0, CategoryLabelWidthType.RANGE,
				0.50f);
		axis.setCategoryLabelPositions(CategoryLabelPositions
				.replaceLeftPosition(p, left));

		return chart;

	}
}
/**
 * Starting point for the demonstration application.
 * 
 * @param args
 *            ignored.
 */
/**
 * public static void main(final String[] args) {
 * 
 * Log.getInstance().addTarget(new PrintStreamLogTarget()); final
 * BarChart3DDemo2 demo = new BarChart3DDemo2("Rails team 6"); demo.pack();
 * RefineryUtilities.centerFrameOnScreen(demo); demo.setVisible(true);
 * 
 */
// }
// }
