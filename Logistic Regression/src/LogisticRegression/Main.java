package LogisticRegression;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.Dimension;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Main extends ApplicationFrame {

	private final int Fnumber = 10;
	Map<Integer,Integer> Data = new HashMap<>();
	private final int interator = 100;
	private final int point = 5;
	
	public static void main(final String[] args) {
		
		   Main demo = new Main("K-nearest Neighbors");
		
		}

		public Main(String title) {
		
		   super(title);

		   XYPlot plot = new XYPlot(); 
		   XYDataset scatterPlotDataset = MakingDataset();
		   
		   
	       plot.setDataset(0, scatterPlotDataset);
	       plot.setRenderer(0, new XYLineAndShapeRenderer(false, true));
	       plot.setDomainAxis(0, new NumberAxis("Age"));
	       plot.setRangeAxis(0, new NumberAxis("Number of Thumbs up"));
	       plot.mapDatasetToDomainAxis(0, 0);
	       plot.mapDatasetToRangeAxis(0, 0);

	
	       JFreeChart chart = new JFreeChart("Function of Y over scatter plot", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	       ChartPanel chartPanel = new ChartPanel(chart);
	       chartPanel.setPreferredSize(new Dimension(500, 270));
	       ApplicationFrame frame = new ApplicationFrame("Example");
	       frame.setContentPane(chartPanel);	   
	       frame.pack();
	       frame.setVisible(true);
		}
		
		public XYDataset MakingDataset() {
			XYSeriesCollection data = new XYSeriesCollection();
			
			XYSeries dataset = new XYSeries("Hate");
			XYSeries dataset1 = new XYSeries("Like");
			XYSeries dataset2 = new XYSeries("Sample");
			
			
			for(int index =0 ; index < Fnumber ; index++)
			   {
				   int s = index;
				   int j = 0;
				  
				   Data.put(s,j);
				   dataset.add(s,j);
			   }
			data.addSeries(dataset);
			
			for(int index =10 ; index < Fnumber+10 ; index++)
			   {
				   int s = index;
				   int j = 1;
				
				   Data.put(s,j);
				   dataset1.add(s,j);
			   }
			data.addSeries(dataset1);
			
			dataset2.add(point,Fomula(Data,point));
			data.addSeries(dataset2);
			return data;
		}			
		
		public double Sigmoid(double value)
		{
			double result = 1/(1+Math.exp(-value));
			return result;
		}
		
		public double Fomula(Map<Integer, Integer> Data, int k){
				double a = 0;
				double b = 0;
				int number = 20;
				
				double F_result = Sigmoid()
				double sum =0;
				double da = 0;
				double db = 0;
				double logit = .0;
				
				for (int i=0; i<weights.length;i++)  {
					logit += weights[i] * Data.get(i);
				}
				double result = (1/(1+Math.exp(logit)));
				
				for(int i = 0; i < interator ; i++)
				{
						
					for(Entry<Integer,Integer> drivate : Data.entrySet())
					{
						
						for (int j=0; j<weights.length; j++) {
							weights[j] = weights[j] + 0.0001 * (drivate.getValue() - result) * drivate.getKey();
						}
					}
						
				}
				
				return (1/(1+Math.exp(-(a*k+b))));
		}
}
