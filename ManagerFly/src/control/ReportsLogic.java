package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import entity.AirPlane;
import entity.AirPort;
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
	/*
	public static HashMap<Flight, ArrayList<String>> BiggestFlightsReport(int seatsNum, Date from, Date until){
		
			HashMap<Flight, ArrayList<String>> toReturn = new HashMap<Flight, ArrayList<String>>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_SEL_BIGGEST_FLIGHTS)) {
						int i = 1;
						/*Timestamp depatureTimeStamp = Timestamp.valueOf(from);
						Timestamp landingTimeStamp = Timestamp.valueOf(until);
						stmt.setTimestamp(i++, depatureTimeStamp);
						stmt.setTimestamp(i++, landingTimeStamp);*/
						/*stmt.setDate(i++, new java.sql.Date(from.getTime()));
						stmt.setDate(i++, new java.sql.Date(until.getTime()));
						stmt.setInt(i++,seatsNum);
						ResultSet rs = stmt.executeQuery();{	
							while (rs.next()) 
							{
								i=1;
								int flightID = rs.getInt(i++);
								ArrayList<String> ar = new ArrayList<String>();
								String deptCounty = rs.getString(i++);
								String deptCity = rs.getString(i++);
								String destCounty = rs.getString(i++);
								String destCity = rs.getString(i++);
								LocalDateTime depTime = rs.getDate(i++).toInstant()
									      .atZone(ZoneId.systemDefault())
									      .toLocalDateTime();
								LocalDateTime arrTime = rs.getDate(i++).toInstant()
									      .atZone(ZoneId.systemDefault())
									      .toLocalDateTime();
								String flightStatus = rs.getString(i++);
								ar.add(deptCounty);ar.add(deptCity);
								ar.add(destCounty);ar.add(destCity);
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
	}*/
	/**
	 * return a HashMap of planes and the number of tourists seats in them
	 * @return
	 */
	public static HashMap<AirPlane,Integer> getSeatCntByPlane() {
		
		HashMap<AirPlane,Integer> results = new HashMap<AirPlane,Integer>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_CNT_TSEAT_BY_PLANE);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					AirPlane  aP = new AirPlane(rs.getString(i++));
					int count = rs.getInt(i++);
					results.put(aP, count);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * return a list of flight which occurred in a range of dated given in the parameters
	 * @param from = the date which  the method start collect flights.
	 * @param until = the date which  the method end collect flights.
	 * @return array list of flight sorted by the country and city of the destination and by dates
	 */
	public static ArrayList<Flight> getFlightsInRange(LocalDate from, LocalDate until){
		
		ArrayList<Flight> toReturn = new ArrayList<Flight>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					Statement stmt = conn.createStatement()) {
						int i = 1;	
						String date1 = "#" + from.getMonthValue() + "/" + from.getDayOfMonth() + "/" + from.getYear() + "#";
						String date2 = "#" + until.getMonthValue() + "/" + until.getDayOfMonth() + "/" + until.getYear() + "#";
						/*stmt.setDate(i++, new java.sql.Date(from.getTime()));
						stmt.setDate(i++, new java.sql.Date(until.getTime()));*/
						String query = "SELECT FlightTbl.SerialNum, FlightTbl.AirPlaneTailNum, AirPortTbl.Country, AirPortTbl.City, AirPortTbl_1.Country, AirPortTbl_1.City, FlightTbl.DepatureTime, FlightTbl.LandingTime, FlightTbl.Status, AirPortTbl.airportCode, AirPortTbl_1.airportCode\r\n"
								+ "FROM AirPortTbl INNER JOIN (AirPortTbl AS AirPortTbl_1 INNER JOIN FlightTbl ON AirPortTbl_1.airportCode = FlightTbl.DestinationAirportID) ON AirPortTbl.airportCode = FlightTbl.DepatureAirportID\r\n"
								+ "WHERE (((FlightTbl.DepatureTime)>= " + date1 + "  And (FlightTbl.DepatureTime)<= " + date2 + ") AND ((FlightTbl.LandingTime)>= " + date1 + " And (FlightTbl.LandingTime)<= " + date2 + "))\r\n"
								+ "GROUP BY FlightTbl.SerialNum, FlightTbl.AirPlaneTailNum, AirPortTbl.Country, AirPortTbl.City, AirPortTbl_1.Country, AirPortTbl_1.City, FlightTbl.DepatureTime, FlightTbl.LandingTime, FlightTbl.Status, AirPortTbl.airportCode, AirPortTbl_1.airportCode\r\n"
								+ "ORDER BY AirPortTbl_1.Country DESC , AirPortTbl_1.City DESC , FlightTbl.DepatureTime DESC , FlightTbl.LandingTime DESC;";
						ResultSet rs = stmt.executeQuery(query);{	
							while (rs.next()) 
							{
								i=1;
								int flightID = rs.getInt(i++);
								String tailNum = rs.getString(i++);
								ArrayList<String> ar = new ArrayList<String>();
								String deptCounty = rs.getString(i++);
								String deptCity = rs.getString(i++);
								String destCounty = rs.getString(i++);
								String destCity = rs.getString(i++);
								LocalDateTime depTime = rs.getTimestamp(i++).toInstant()
									      .atZone(ZoneId.systemDefault())
									      .toLocalDateTime();
								LocalDateTime arrTime = rs.getTimestamp(i++).toInstant()
									      .atZone(ZoneId.systemDefault())
									      .toLocalDateTime();
								String flightStatus = rs.getString(i++);
								int deptID = rs.getInt(i++);
								int destID = rs.getInt(i++);
								ar.add(deptCounty);ar.add(deptCity);
								ar.add(destCounty);ar.add(destCity);
								Flight f = new Flight(flightID, depTime, arrTime, flightStatus,new AirPlane(tailNum), new AirPort(deptID,deptCity,deptCounty), new AirPort(destID,destCity,destCounty));
								toReturn.add(f);
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
	
	public static ArrayList<Flight> BiggestFlights(int attenNum, LocalDate from, LocalDate until) {
		
		ArrayList<Flight> inRange = getFlightsInRange(from,until);
		HashMap<AirPlane,Integer> pln = getSeatCntByPlane();
		ArrayList<Flight> toReturn = new ArrayList<Flight>();
		for(Flight f: inRange) {
			if(pln.get(f.getAirPlane()) >= attenNum) {
				toReturn.add(f);
			}
		}
		return toReturn;
	}
	///Testing method
	public static void main(String args[]){
		 
		LocalDate from = LocalDate.of(2022, 2, 9);
		LocalDate until = LocalDate.of(2022, 2, 14);
		int attNum = 2;
		HashMap<AirPlane,Integer> planeCntMap = getSeatCntByPlane();
		for (Entry<AirPlane,Integer> entry : planeCntMap.entrySet()) {
	         AirPlane f = entry.getKey();
	         int count = entry.getValue();
			 System.out.println("plane = " + f.getTailNum() + " seat count = " + count);
		 }
		 ArrayList<Flight> toReturn = BiggestFlights(5,from, until);
		 for(Flight f: toReturn) {
			 System.out.println("flight num = " + f.getFlightNum() +  " from = " + f.getDepatureAirport().getCountry() + " " + f.getDepatureAirport().getCity() + " to = " + f.getDestinationAirport().getCountry() + " " + f.getDestinationAirport().getCity() + " ");
		 }
		 
    }
	
	
	
}
	
