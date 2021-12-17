package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import entity.AirAttendant;
import entity.AirPlane;
import entity.AirPort;
import entity.Flight;
import entity.FlightSeat;
import entity.GroundAttendant;
import entity.Pilot;
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
					
					String flightID = rs.getString(i++);
					LocalDateTime depTime = rs.getTimestamp(i++).toInstant()
						      .atZone(ZoneId.systemDefault())
						      .toLocalDateTime();
					LocalDateTime arrTime = rs.getTimestamp(i++).toInstant()
						      .atZone(ZoneId.systemDefault())
						      .toLocalDateTime();
					
					
					results.add(new Flight(flightID, depTime, arrTime, rs.getString(9), new AirPort(rs.getInt(i++)), new AirPort(rs.getInt(i++)),
								new AirPlane(rs.getString(i++)), rs.getString(i++), rs.getString(i++),rs.getString(10)));
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
					
					results.add(new AirPort(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++),rs.getBoolean(i++)));
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
	
	/**
	 * fetches all air attendants from DB file.
	 * @return ArrayList of air attendants.
	 */
	public ArrayList<AirAttendant> getAirAttendants() {
		ArrayList<AirAttendant> results = new ArrayList<AirAttendant>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_AIRATTENDANTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					java.sql.Date contractFinishDate =  rs.getDate(5);
					LocalDate contractFinishLocalDate = null;
					if(contractFinishDate != null)
						contractFinishLocalDate = contractFinishDate.toLocalDate();
					results.add(new AirAttendant(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(),contractFinishLocalDate));
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
	 * fetches all ground attendants from DB file.
	 * @return ArrayList of ground attendants.
	 */
	public ArrayList<GroundAttendant> getGroundAttendants() {
		ArrayList<GroundAttendant> results = new ArrayList<GroundAttendant>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_GROUNDATTENDANTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					java.sql.Date contractFinishDate =  rs.getDate(5);
					LocalDate contractFinishLocalDate = null;
					if(contractFinishDate != null)
						contractFinishLocalDate = contractFinishDate.toLocalDate();
					results.add(new GroundAttendant(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(),contractFinishLocalDate));
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
	 * fetches all pilots from DB file.
	 * @return ArrayList of pilots.
	 */
	public ArrayList<Pilot> getPilots() {
		ArrayList<Pilot> results = new ArrayList<Pilot>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_PILOTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					java.sql.Date contractFinishDate =  rs.getDate(5);
					LocalDate contractFinishLocalDate = null;
					if(contractFinishDate != null)
						contractFinishLocalDate = contractFinishDate.toLocalDate();
					results.add(new Pilot(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(),contractFinishLocalDate,rs.getString(6), rs.getDate(7).toLocalDate()));
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
