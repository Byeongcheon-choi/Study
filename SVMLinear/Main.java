package SVMLinear;

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
	Map<double[], Integer> Data = new HashMap<>();
	private int[] point;
	
	
	public static void main(final String[] args) {
		
		   Main demo = new Main("K-nearest Neighbors");
		
		}

		public Main(String title) {
		
	       super(title);
               XYPlot plot = new XYPlot(); 
	       XYDataset scatterPlotDataset = MakingDataset();
		   
//	       Fomula(Data,Fnumber, point);
		   
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
			
			XYSeries dataset = new XYSeries("Like Hyundai");
			XYSeries dataset1 = new XYSeries("Like Tesla");
			XYSeries dataset2 = new XYSeries("Sample");
			
			
			for(int index =0 ; index < Fnumber ; index++)
			   {
				   double s = 5 + (Math.random()*10);
				   double j = 5 + (Math.random()*10);
				   double[] f = {s,j};
				   Data.put(f,1);
				   dataset.add(s,j);
			   }
			data.addSeries(dataset);
			
			for(int index =0 ; index < Fnumber ; index++)
			   {
				 double s = Math.random()*10;
				   double j = Math.random()*10;
				   double [] f = {s,j};
				   Data.put(f,0);
				   dataset1.add(s,j);
			   }
			data.addSeries(dataset1);
			
//			dataset2.add(point[0],point[1]);
//			data.addSeries(dataset2);
			
			return data;
		}			
	
//		public void Fomula(Map<Double, Integer> Data, int K, int[] point){
//			    Map<Double, String> D = new HashMap<>();
//			    ArrayList distance = new ArrayList();
//				double d = 0;
//				int l =0;
//				int o =0;
//				for(int[] key : Data.keySet())					
//				{
//					d = Math.pow((key[0]-point[0]),2) + Math.pow((key[1]-point[1]),2);		//Distance between the point and data
//					D.put(d,Data.get(key));								//Put the distance value and string value in New HashMap 
//					distance.add(d);						//For ordering the distance, Add the distance value in Arraylist 
//				}
//				
//				Collections.sort(distance);		//ordering it.
//				for(int i = 0; i< K ; i ++)		//By using the user setting(k), Decide how many 
//				{
//					if(D.get(distance.get(i)).equals("Hyundai"))
//					{
//						l +=1;
//					}
//					else if(D.get(distance.get(i)).equals("Tesla"))
//					{
//						o +=1;
//					}
//				}
//				
//				if(l>o)						//Decision that the point is Hyundai or Tesla
//				{
//					System.out.println("Hyundai");
//				}
//				else
//				{
//					System.out.println("Tesla");
//				}
//		}
		

}
