package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import entity.Flight;
import util.Consts;

public class ReportsLogic {

	private static ReportsLogic _instance;

	private ReportsLogic() {
	}

	public static ReportsLogic getInstance() {
		if (_instance == null)
			_instance = new ReportsLogic();
		return _instance;
	}
	
	/**
	 * *****************************This is a test method***********************************<br>
	 * The method select flights that occurred between 2 dates that the user gave, and contains at least
	 * a given number of tourists seats.
	 * @param seatsNum = the lower bound of tourists seats that the flight should contain. 
	 * @param from	= the date which  the method start collect flights.
	 * @param until = the date which  the method end collect flights.
	 * @return a HashMap of flights as key ,and string List of city and country of the departure airport, 
	 * and the landing airport.
	 */
	public static HashMap<Flight, ArrayList<String>> BiggestFlightsReport(int seatsNum, LocalDateTime from, LocalDateTime until){
		
			HashMap<Flight, ArrayList<String>> toReturn = new HashMap<Flight, ArrayList<String>>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_SEL_BIGGEST_FLIGHTS)) {
						int i = 1;
						stmt.setInt(i++,seatsNum);
						Timestamp depatureTimeStamp = Timestamp.valueOf(from);
						Timestamp landingTimeStamp = Timestamp.valueOf(until);
						stmt.setTimestamp(i++, depatureTimeStamp);
						stmt.setTimestamp(i++, landingTimeStamp);
				
						ResultSet rs = stmt.executeQuery();{	
							while (rs.next()) 
							{
								i=1;
								int flightID = rs.getInt(i++);
								ArrayList<String> ar = new ArrayList<String>();
								String deptPlace = rs.getString(i++);
								String destPlace = rs.getString(i++);
								LocalDateTime depTime = rs.getDate(i++).toInstant()
									      .atZone(ZoneId.systemDefault())
									      .toLocalDateTime();
								LocalDateTime arrTime = rs.getDate(i++).toInstant()
									      .atZone(ZoneId.systemDefault())
									      .toLocalDateTime();
								String flightStatus = rs.getString(i++);
								ar.add(deptPlace);ar.add(destPlace);
								Flight f = new Flight(flightID, depTime, arrTime, flightStatus);
								toReturn.put(f, ar);
							}
						}
						
						
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	///Testing method
	public static void main(String args[]){
		 
		LocalDateTime from = LocalDateTime.of(2022, 
                Month.FEBRUARY, 9, 3, 30, 40);
		LocalDateTime until = LocalDateTime.of(2022, 
                Month.FEBRUARY, 14, 22, 30, 40);
		int attNum = 2;
		HashMap<Flight, ArrayList<String>> toReturn = BiggestFlightsReport(attNum,from,until);
		 for (Entry<Flight, ArrayList<String>> entry : toReturn.entrySet()) {
	         Flight f = entry.getKey();
	         ArrayList<String> ar = entry.getValue();
			 System.out.println("flight = " + f.getFlightNum() + " "  + f.getFlightStatus() +
	                             ", from = " + ar.get(0) + "to = " + ar.get(1));
		 }
    }
	
}
	
