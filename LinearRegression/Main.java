package LinearRegression;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Main extends ApplicationFrame {

	int index = 0;
	int Fnumber = 10;
	int x;
	int y;
	public double cost;
	public double W;
	public double b;
	
	public static void main(final String[] args) {
		
		   Main demo = new Main("Linear Regression");
		   demo.pack();
		   RefineryUtilities.centerFrameOnScreen(demo);
		   demo.setVisible(true);
		
		}

		public Main(String title) {
		
		   super(title);
		   XYSeries series = new XYSeries("Data Plot");
		   Map<Integer, Integer> Data = new HashMap<>(); 
		   
		   for(index =0 ; index < Fnumber ; index++)
		   {
			   x = (int)(Math.random()*100);
			   y = (int)(Math.random()*100);
			   Data.put(x,y);
			   series.add(x,y);
		   }
		   
		   Fomula(Data,Fnumber);
		   
		   XYSeriesCollection data = new XYSeriesCollection(series);
		   JFreeChart chart = ChartFactory.createScatterPlot(
		       "Linear Regression", "X", "Y", data,
		       PlotOrientation.VERTICAL,
		       true,
		       true,
		       false
		   );
		
		   ChartPanel chartPanel = new ChartPanel(chart);
		   chartPanel.setPreferredSize(new java.awt.Dimension(1280, 720));
		   setContentPane(chartPanel);
		
		}
		
		public void Fomula(Map<Integer,Integer> Data, int Fnumber){
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
		}

}