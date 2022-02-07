package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import entity.Flight;
import entity.FlightSeat;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExportControl {

	private static ExportControl instance;

	public static ExportControl getInstance() {
		if (instance == null)
			instance = new ExportControl();
		return instance;
	}
	
	//This method gets date and do export to JSON for Customer-Fly System
//		public static void exportToJSON(java.sql.Date today) 
//		{
//			try {
//				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
//						CallableStatement stmt = conn.prepareCall(
//								Consts.SQL_EXPORT_DATA_TEST)){
//						stmt.setDate(1, today);
//						ResultSet rs = stmt.executeQuery(); {
//					JsonArray updatedFlights = new JsonArray();
//					while (rs.next()) {
//						JsonObject updatedFlight = new JsonObject();
//
//						for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
//							updatedFlight.put(rs.getMetaData().getColumnName(i), rs.getString(i));
//
//
//						updatedFlights.add(updatedFlight);
//					}
//
//					JsonObject doc = new JsonObject();
//					doc.put("flights", updatedFlights);
//
//					File file = new File("json/flights.json");
//					file.getParentFile().mkdir();
//
//					try (FileWriter writer = new FileWriter(file)) {
//						writer.write(Jsoner.prettyPrint(doc.toJson()));
//						writer.flush();
//						 Alert alert = new Alert(AlertType.INFORMATION, "Flights data exported successfully!");
//						 alert.setHeaderText("Success");
//						 alert.setTitle("Success Data Export");
//						 alert.showAndWait();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//						}
//				} catch (SQLException | NullPointerException e) {
//					e.printStackTrace();
//				}
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}	
//		}
	
	    //exports flights and seats from db to json
		public void exportFlightsToJSON() {
	    	   try {
	    		   
	                ArrayList<Flight> flights = Getters.getInstance().getFlights();
	                JsonArray data = new JsonArray();
	                for(int i=0; i<flights.size(); i++) {
	                	JsonObject flight = new JsonObject();
	                	flight.put("FlightID", flights.get(i).getFlightNum().toString()); 
	                	flight.put("DepartureAirportCode", flights.get(i).getDepatureAirportID().toString());    
	                	flight.put("DepartureDateTime", flights.get(i).getDepatureTime().toString()); 
	                	flight.put("DestinationAirportCode", flights.get(i).getDestinationAirportID().toString());  
	                	flight.put("DestinationDateTime", flights.get(i).getLandingTime().toString());  
	                	flight.put("Status", flights.get(i).getFlightStatus());  
	                	flight.put("AirplaneID", flights.get(i).getAirPlaneTailNum().toString());
	    				flight.put("DepartureCountry", flights.get(i).getDepatureAirportID().getCountry());
	    				flight.put("DepartureCity", flights.get(i).getDepatureAirportID().getCity());
	    				flight.put("DestinationCountry", flights.get(i).getDestinationAirportID().getCountry());
	    				flight.put("DestinationCity", flights.get(i).getDestinationAirportID().getCity());
	    				
	                	JsonArray seats = new JsonArray();
	                	ArrayList<FlightSeat> seatList =Getters.getInstance().getFlightSeats();
	                	for(int j=0; j<seatList.size(); j++) {
	                		JsonObject seat = new JsonObject();
	                		seat.put("Row", seatList.get(j).getRowNum());
	                		seat.put("Seat", seatList.get(j).getColNum());
	                		seat.put("Class", seatList.get(j).getSeatType());
	                		seats.add(seat);
	                	}
	                	
	                	flight.put("Seats", seats);
	                	data.add(flight);
	                 }
	            	 JsonObject doc = new JsonObject();
	    		   	 doc.put("flights", data);
	    		   	 
	                 File file = new File("json/flights.json");
	                 file.getParentFile().mkdir();
	                   
	                 try (FileWriter writer = new FileWriter(file)) {
	                	  writer.write(Jsoner.prettyPrint(doc.toJson()));
	                	  writer.flush();
	                	  Alert alert = new Alert(AlertType.INFORMATION, "Flights data exported successfully!");
	                	  alert.setHeaderText("Success");
	                	  alert.setTitle("Success Data Export");
	                	  alert.showAndWait();
	                 } catch (IOException e) {
	                	   e.printStackTrace();
	                 }
	           } catch (Exception e) {
	               e.printStackTrace();
	           }	
	    }
}
