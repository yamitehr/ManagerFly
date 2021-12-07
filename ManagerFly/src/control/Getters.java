package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import entity.AirPlane;
import entity.AirPort;
import entity.Flight;
import entity.FlightSeat;
import util.Consts;

public class Getters {
	private static Getters _instance;

	private Getters() {
	}

	public static Getters getInstance() {
		if (_instance == null)
			_instance = new Getters();
		return _instance;
	}

	/**
	 * fetches all flights from DB file.
	 * @return ArrayList of flights.
	 */
	public ArrayList<Flight> getFlights() {
		ArrayList<Flight> results = new ArrayList<Flight>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					int flightID = rs.getInt(i++);
					LocalDateTime depTime = rs.getTimestamp(i++).toInstant()
						      .atZone(ZoneId.systemDefault())
						      .toLocalDateTime();
					LocalDateTime arrTime = rs.getTimestamp(i++).toInstant()
						      .atZone(ZoneId.systemDefault())
						      .toLocalDateTime();
					
					
					results.add(new Flight(flightID, depTime, arrTime, rs.getString(9), new AirPort(rs.getInt(i++)), new AirPort(rs.getInt(i++)),
								new AirPlane(rs.getString(i++)), rs.getString(i++), rs.getString(i++)));
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
	 * fetches all airports from DB file.
	 * @return ArrayList of airports.
	 */
	public ArrayList<AirPort> getAirports() {
		ArrayList<AirPort> results = new ArrayList<AirPort>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_AIRPORT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					results.add(new AirPort(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++)));
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
	 * fetches all airplanes from DB file.
	 * @return ArrayList of airplanes.
	 */
	public ArrayList<AirPlane> getAirplanes() {
		ArrayList<AirPlane> results = new ArrayList<AirPlane>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_AIRPLANE);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					results.add(new AirPlane(rs.getString(i++), rs.getInt(i++)));
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
	 * fetches all flight seats from DB file.
	 * @return ArrayList of flight seats.
	 */
	public ArrayList<FlightSeat> getFlightSeats() {
		ArrayList<FlightSeat> results = new ArrayList<FlightSeat>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHTSEATS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					results.add(new FlightSeat(rs.getInt(i++), rs.getInt(i++), rs.getString(i++), rs.getString(i++), new AirPlane(rs.getString(i++))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
}
