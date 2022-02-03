package boundery;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

import util.Consts;

/*
 * How to call the report from the code :
 * 	FlightsByDepartureCountry chart = new FlightsByDepartureCountry();
 *	chart.pack( );        
 *	RefineryUtilities.centerFrameOnScreen(chart);        
 *	chart.setVisible(true);  
 */
public class FlightsByDepartureCountry extends JFrame{
	
	private static String applicationTitle = "Flight precentage by destination country in the last month vs all time";
	private static String chartTitle = "Flight precentage by destination country in the last month vs all time";
	
	public FlightsByDepartureCountry(String country) {
	      super(applicationTitle);
	      //create bar chart
	      JFreeChart barChart = ChartFactory.createBarChart(
	         chartTitle,           
	         "Destination Airport Code",            
	         "Flights Precentage",            
	         createDataset(country),          
	         PlotOrientation.VERTICAL,           
	         true, true, false);
	      //chart setting such as colors, fonts and size
	      ChartPanel chartPanel = new ChartPanel( barChart );   
	      barChart.getPlot().setBackgroundPaint(Color.WHITE);
	      CategoryPlot plot = barChart.getCategoryPlot();
	      Font font3 = new Font("Tahoma", Font.PLAIN, 16); 
	      plot.getDomainAxis().setLabelFont(font3);
	      plot.getRangeAxis().setLabelFont(font3);
	      chartPanel.setPreferredSize(new java.awt.Dimension(700,700) );        
	      setContentPane( chartPanel ); 
	      CategoryItemRenderer renderer = ((CategoryPlot)barChart.getPlot()).getRenderer();
	      CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator(); 
	      renderer.setBaseItemLabelGenerator(generator); 
	      renderer.setBaseItemLabelFont(new Font("Tahoma", Font.BOLD, 16)); //just font change optional 
	      renderer.setBaseItemLabelsVisible(true); 
	      ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, 
	              TextAnchor.TOP_CENTER);
	      renderer.setBasePositiveItemLabelPosition(position); 
	   }
	
	   /*
	    * return the total flight percentage by departure country
	    */
	   private LinkedHashMap<Integer,Double> getTotalFlightPrecentage(String country)
		{
		   String query = "select DestinationAirportID, (Count(DestinationAirportID)* 100 / (Select Count(*) From FlightTbl)) as total\r\n"
					+ "from FlightTbl\r\n"
					+ "where DestinationAirportID in "
					+ "(select airportCode\r\n"
					+ "from AirPortTbl\r\n"
					+ "where Country like '" + country + "')\r\n"
					+ "group by DestinationAirportID;";
			LinkedHashMap<Integer,Double> res = new LinkedHashMap<Integer,Double>();
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(query);
						ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int i = 1;
						res.put(rs.getInt(i++), rs.getDouble(i++));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return res;
		}
	   
	   /*
	    * return the monthly flight percentage by departure country
	    */
	   private LinkedHashMap<Integer,Double> getMonthlyFlightPrecentage(String country)
		{
		   String query = "select DestinationAirportID, (Count(DestinationAirportID)* 100 / (Select Count(*) From FlightTbl)) as monthly\r\n"
					+ "from FlightTbl\r\n"
					+ "where Month(DepatureTime) = Month(Date()) AND Year(DepatureTime) = Year(Date())\r\n"
					+ "AND DestinationAirportID in "
					+ "(select airportCode\r\n"
					+ "from AirPortTbl\r\n"
					+ "where Country like '" + country + "')\r\n"
					+ "group by DestinationAirportID;";
			LinkedHashMap<Integer,Double> res = new LinkedHashMap<Integer,Double>();
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(query);
						ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int i = 1;
						res.put(rs.getInt(i++), rs.getDouble(i++));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return res;
		}
	   
	   /*
	    * get all the data for the chart and adds the values to the chart
	    */
	   private CategoryDataset createDataset(String country) {   
		   	final String month = "Last Month";        
		    final String all = "All Time";        
		      
		    final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  
		    LinkedHashMap<Integer,Double> totalData = getTotalFlightPrecentage(country);
		    LinkedHashMap<Integer,Double> monthlyData = getMonthlyFlightPrecentage(country);

		      ArrayList<Integer> keys = new ArrayList<Integer>(totalData.keySet());
		      for(int i=0; i<keys.size(); i++)
		      {
		    	  double monthly = 0.04; //set deafult value to see data when there are no flights to the country this month
		    	  if(monthlyData.containsKey(keys.get(i))) {
		    		  monthly = monthlyData.get(keys.get(i));
		    	  }
		    	  dataset.addValue(monthly,month,keys.get(i));
		    	  dataset.addValue(totalData.get(keys.get(i)),all,keys.get(i));
		      }          
		      return dataset; 
	   }
	   
}
