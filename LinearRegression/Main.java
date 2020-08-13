package LinearRegression;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Main extends ApplicationFrame {

	int index = 0;
	int Fnumber = 100;
	int x;
	int y;
	public double W;
	public double b;
	
	public static void main(final String[] args) {
		
		   Main demo = new Main("Linear Regression"); 
		
		}

		public Main(String title) {
		
		   super(title);
		   
		   XYSeriesCollection data = new XYSeriesCollection();
		   XYSeries dataset = new XYSeries("Data");
		   XYSeriesCollection linear = new XYSeriesCollection();
		   XYSeries Line = new XYSeries("Linear Regression Line");
		   
		   Map<Integer, Integer> Data = new HashMap<>(); // Define X, Y collidination
		   
		   for(index =0 ; index < Fnumber ; index++) // collect the random data
		   {
			   x = (int)(Math.random()*100);
			   y = (int)(Math.random()*100);
			   Data.put(x,y);
			   dataset.add(x,y);
		   }
		   
		   data.addSeries(dataset);
		 
		   
		   Fomula(Data,Fnumber); 		// Create the method for making the Linear Regression Fomula
		   
		   double Lpint = W*100+b;
		   
		   Line.add(0,b);			// Add Initail point 
		   Line.add(100,Lpint);			// Add Final point.
		   
		   linear.addSeries(Line);
		   
		   XYPlot plot = new XYPlot(); 
 
		   XYDataset scatterPlotDataset = data;
	           plot.setDataset(0, scatterPlotDataset);
	           plot.setRenderer(0, new XYLineAndShapeRenderer(false, true));
	           plot.setDomainAxis(0, new NumberAxis("Y"));
	           plot.setRangeAxis(0, new NumberAxis("X"));
	           plot.mapDatasetToDomainAxis(0, 0);
	           plot.mapDatasetToRangeAxis(0, 0);


	           XYDataset functionDataset = linear;
	           plot.setDataset(1, functionDataset);
	           plot.setRenderer(1, new XYLineAndShapeRenderer(true, false));

	           JFreeChart chart = new JFreeChart("Function of Y over scatter plot", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	           ChartPanel chartPanel = new ChartPanel(chart);
	           chartPanel.setPreferredSize(new Dimension(500, 270));
	           ApplicationFrame frame = new ApplicationFrame("Example");
	           frame.setContentPane(chartPanel);	   
	           frame.pack();
		   frame.setVisible(true);
		}
		
		public void Fomula(Map<Integer,Integer> Data, int Fnumber){		//Define the Linear Regression Fomula.
			int SUMa = 0;
			int SUMb = 0;
			int SUMc = 0;
			int SUMd = 0;

			int Xx = 0;
			int Yy = 0;
			
			Iterator<Integer> keys = Data.keySet().iterator();
			while(keys.hasNext())
			{
				int key = keys.next();
				SUMa += key*Data.get(key); 			
				SUMb += Math.pow(key,2);
			}
			
			for(Entry<Integer, Integer> Fdata : Data.entrySet())
			{
				Xx += Fdata.getKey();
				Yy += Fdata.getValue();
			}
			
			SUMc =  (int)Math.pow(Xx,2);
			SUMc = Xx * Yy;
			
			W = (Fnumber*SUMa - SUMc) / (Fnumber*SUMb - SUMd);
			b = (Yy -Xx * W)/Fnumber;
			System.out.println(W + "" +b);
		}
		

}
