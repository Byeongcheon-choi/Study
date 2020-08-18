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
	Map<Double,Double> Data = new HashMap<>();
	private final int interator = 5000;
	private final int point = 15;
	
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
			
			
			for(int index =0 ; index < Fnumber ; index++)
			   {
				   double s = index;
				   double j = 0;
				  
				   Data.put(s,j);
				   dataset.add(s,j);
			   }
			data.addSeries(dataset);
			
			for(int index =10 ; index < Fnumber+10 ; index++)
			   {
				double s = index;
				double j = 1;
				
				   Data.put(s,j);
				   dataset1.add(s,j);
			   }
			data.addSeries(dataset1);
			
			dataset2.add(point,result(Data,point));
			data.addSeries(dataset2);
			return data;
		}			
		
		public double Sigmoid(double value)
		{
			double result = 1/(1+Math.exp(-value));
			return result;
		}
		public double Dotproduct(double[] a, double[] b)
		{
			double sum = 0;
			for(int i = 0; i< 20; i++)
			{
				sum += a[i] * b[i];
			}
			return sum;
		}
		public double Dotproduct(double[] a, double b)
		{
			double sum = 0;
			for(int i = 0; i< 20; i++)
			{
				sum += a[i] * b;
			}
			return sum;
		}
		public double[] YtransCalculator(double a, double[] b) {
			double[] result = new double[20];
			for(int n = 0; n< 20;n++)
			{
				result[n] = a - b[n];
			}
			
			return result;
			
		}
		public double[] DrivateCalculator(double a, double[] b) {
			double[] result = new double[20];
			for(int n = 0; n< 20;n++)
			{
				result[n] = b[n] - a;
			}
			
			return result;
			
		}
		
		public double[] Fomula(double[] x, double[] y, double[] a, double b){
				int number = 20;
				double[] p = new double[20];
				double[] result = new double[3];
				double da = 0;
				double db =0;
				
				
				double F_result = Sigmoid(Dotproduct(x,a)+b);
				double cost = 0;
				double sum1 = 0;
				for(int j = 0 ; j < a.length ; j++)
				{
					sum1 += (y[j]*Math.log(F_result)+(1-y[j]*Math.log(1-F_result)));
				}
				
				cost = - sum1/number;
				System.out.println(cost);
				
				da = Dotproduct(x, YtransCalculator(F_result,y)) / number;
				p = YtransCalculator(F_result,y);
				double sum2 =0;
				for(int i=0; i< 20 ; i++)
				{
					sum2 += p[i];
				}
				db = sum2/number;
				
				result[0] = da;
				result[1] = db;
				result[2] = cost;
				return result;
						
		}
		public double[][] learningfomual(double[] x, double[] y, double[] a, double b) {	
			double value[] = new double[3];
			double da[] = new double[20];
			double db = 0;
			double F_b[] = new double[1];
			for(Entry<Double,Double> arr : Data.entrySet())
			{
				int i =0;
				x[i] = arr.getKey();
				y[i] = arr.getValue();
				i++;
			}
			
			for(int k =0; k < interator ; k++) {
				
				value = Fomula(x, y,da,db);
				
				da = DrivateCalculator(0.0001*value[0],da);
				System.out.println(a);
				db = db -(0.0001*value[1]);
				
			}
			F_b[0] = db;
			double result[][] = {a,F_b};
			
			return result;
			
		}
		
		public double result(Map<Double, Double> Data, double point) {
			double[] x = new double[20];
			double[] y = new double[20];
			double a[] = new double[20];
			double b = 0;
			
			for(Entry<Double,Double> arr : Data.entrySet())
			{
				int i =0;
				x[i] = arr.getKey();
				y[i] = arr.getValue();
				i++;
			}
			
			double[][] F_re = learningfomual(x,y,a,b);
			
			
			double Decide = 1/(1+Math.exp(-(Dotproduct(F_re[0], point +F_re[1][0]))));
			if(Decide > 0.5) System.out.println(Decide + "Like");
			else System.out.println(Decide + "Hate");
			return Decide;
		}
		
}
