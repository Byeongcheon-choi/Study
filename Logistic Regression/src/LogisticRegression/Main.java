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
	private final int k = 3;
	Map<Integer, Integer> Data = new HashMap<>();
	private int point = 80;
	
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
			
			XYSeries dataset = new XYSeries("Love");
			XYSeries dataset1 = new XYSeries("Hate");		
			XYSeries dataset2 = new XYSeries("result");
			
			for(int index =0 ; index < Fnumber ; index++)
			   {
				   int s = (int)(Math.random()*100);
				   int j = 1;
				   Data.put(s,j);
				   dataset.add(s,j);
			   }
			data.addSeries(dataset);
			
			for(int index =0 ; index < Fnumber ; index++)
			   {
				   int s = (int)(Math.random()*100);
				   int j = 0;		 
				   Data.put(s,j);
				   dataset1.add(s,j);
			   }
			data.addSeries(dataset1);
			
			Fomula(Data,point); 
			
			dataset2.add(point,Fomula(Data,Fnumber));
			data.addSeries(dataset2);
			return data;
		}			
	
		public int Fomula(Map<Integer,Integer> Data, int point){
				double a = 0;
				double b = 0;
				double error = 0.1;
				double cost = 0;
				
				double CostA = 0;
				double CostB = 0;		
				
			    Iterator<Integer> keys = Data.keySet().iterator();
			    while(keys.hasNext())
				{
			    	 int key = keys.next();
					 
			    	 CostA = Data.get(key)*Math.pow(Math.E, b-a*key)/((Math.pow(Math.pow(Math.E, b-a*key)+1, 2))*(1-(1/(1-(1/(Math.pow(Math.E, b-a*key)))))))- Data.get(key)*Math.pow(Math.E, b-a*key)/(Math.pow(Math.E, b-a*key)+1);
					 CostB =(key*(( Data.get(key)-1)*Math.pow(Math.E, a*key)+Math.pow(Math.E,b)* Data.get(key)))/(Math.pow(Math.E,a*key)+Math.pow(Math.E, b));
				     
				     
				     cost = Data.get(key)*Math.log((1/(1+Math.pow(Math.E, -(a*key-b))))) - (1-Data.get(key))*Math.log((1-(1/(1+Math.pow(Math.E, -(a*key-b))))));			     
				     if(cost == 0)
				     {
				    	 System.out.print(cost);
				    	 break;
				     }
				     else {
				    	 a =  a - error*CostA;
					     b =  b - error*CostB;
					     System.out.println( a + "" + b);
				    	 
				     }
				     
				}
				
				double result  = (1/(1+Math.pow(Math.E, -(a*point-b))));
				
				if(result >= 0.5) Systme.out.println("Love")
				else System.out.println("Hate");
			return result;
		}
	

}
