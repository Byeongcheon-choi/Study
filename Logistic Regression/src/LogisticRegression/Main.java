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
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Main extends ApplicationFrame {

	private final int Fnumber = 10;
	ArrayList<Double> Xdata = new ArrayList<Double>();
	ArrayList<Double> Ydata = new ArrayList<Double>();
	private final double error = 0.01;
	private final int interator = 100;
	private final int point = 18;
	
	public static void main(final String[] args) {
		
		   Main Testing = new Main("Logistic Regression");
		
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
			
			
			for(int index =-10 ; index < 0 ; index++)
			   {
				   double s = index;
				   double j = -1;
				  
				   Xdata.add(s);
				   Ydata.add(j);
				   dataset.add(s,j);
			   }
			data.addSeries(dataset);
			
			for(int index =10 ; index < Fnumber+10 ; index++)
			   {
				double s = index;
				double j = 1;
				
				Xdata.add(s);
				Ydata.add(j);
				dataset1.add(s,j);
			   }
			data.addSeries(dataset1);
			
			dataset2.add(point,result(point));
			data.addSeries(dataset2);
			return data;
		}			
		
		public double Sigmoid(double value)
		{
			double result = 1/(1+Math.exp(-value));
			return result;
		}
		
		
//		public double Dotproduct(double[] a, double[] b)
//		{
//			double sum = 0;
//			for(int i = 0; i< a.length; i++)
//			{
//				sum += a[i] * b[i];
//			}
//			return sum;
//		}
//
//		public double Dotproduct(double a, double[] b)
//		{
//			double sum = 0;
//			for(int i = 0; i< b.length; i++)
//			{
//				sum += a * b[i];
//			}
//			return sum;
//		}
		
		public double Dotproduct(double a, double b)
		{
			return a*b;
		}
		double[] a = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};  //Initialize the weight.
		double[] b = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		public double Fomula(double[] x, double[] y){
			double cost = 0;
			double sum = 0;
			double dax =0; 
			for (int n = 0; n < interator; n++) {
			
				for(int i =0 ; i< x.length ; i++)
				{
					sum =0;
					cost = 0;
					dax = Sigmoid(Dotproduct(x[i],a[i]))+b[i];
					
					double diff = y[i] - dax;
					
					a[i] = a[i] + error *x[i]*diff;
					cost += y[i]*Math.log(dax) +(1-y[i])*Math.log(1-dax);

					System.out.println(cost);

				}
				
				
			}
			double result = 0;
			for(int k =0; k < x.length ; k++)
			{
				result += a[k]*point;
			}
			return result; 
			
		}
		
		public double result(double point) {
			double[] x = new double[20];
			double[] y = new double[20];
			int k = 0;
			int L = 0;
			
			for(double i : Xdata)
			{
				x[k] = i;	
				k++;
			
			}
			for(double i : Ydata)
			{
				y[L] = i;	
				L++;
			
			}
			
			double F_re = Fomula(x,y);
			
			if(F_re > 0.5) System.out.println(F_re + "Like");
			else System.out.println(F_re + "Hate");
			return F_re;
		}
		
}
