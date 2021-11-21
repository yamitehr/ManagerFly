package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import entity.AirPlane;
import entity.AirPort;
import entity.Flight;
import util.Consts;


public class FlightsLogic {
	private static FlightsLogic _instance;

	private FlightsLogic() {
	}

	public static FlightsLogic getInstance() {
		if (_instance == null)
			_instance = new FlightsLogic();
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
					
					
					results.add(new Flight(flightID, depTime, arrTime, new AirPort(rs.getInt(i++)), new AirPort(rs.getInt(i++)),
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
	
	//TODO: should we separate this to different controller?
	
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
					
					results.add(new AirPort(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++), rs.getBoolean(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	//TODO: should we separate this to different controller?
	
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
		
		public boolean isPlaneOverlapping(AirPlane airplane, LocalDateTime startDate, LocalDateTime endDate){
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			
			String depatureTimeStampStr = sdf.format(Timestamp.valueOf(startDate));
			String landingTimeStampStr = sdf.format(Timestamp.valueOf(endDate));
			
			String query = "SELECT SerialNum FROM FlightTbl WHERE (((FlightTbl.AirPlaneTailNum)='"
							+ airplane.getTailNum() + "') AND ((FlightTbl.DepatureTime)<=#"
							+landingTimeStampStr+ "#) AND ((FlightTbl.LandingTime)>=#"+depatureTimeStampStr+"#))";
			ArrayList<Integer> results = new ArrayList<Integer>();
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(query);
						ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int i = 1;
						
						int flightID = rs.getInt(i++);
						results.add(flightID);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(results.isEmpty()) {
				return true;
			}
			return false;
		}
	
	/*----------------------------------------- ADD / REMOVE / UPDATE FLIGHT METHODS --------------------------------------------*/

	/**
	 * Adding a new Employee with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addFlight(int flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, int depatureAirportID,
			int destinationAirportID, String airPlaneTailNum, String cheifPilotID, String coPilotID, String flightStatus, String orderStatus) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHT)){
				
				int i = 1;
				Timestamp depatureTimeStamp = Timestamp.valueOf(depatureTime);
				Timestamp landingTimeStamp = Timestamp.valueOf(landingTime);
				
				stmt.setInt(i++, flightNum); // can't be null
				stmt.setTimestamp(i++, depatureTimeStamp);
				stmt.setTimestamp(i++, landingTimeStamp);
				stmt.setInt(i++, depatureAirportID);
				stmt.setInt(i++, destinationAirportID);
				stmt.setString(i++, airPlaneTailNum);
				if (cheifPilotID != null)
					stmt.setString(i++, cheifPilotID);
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				if (coPilotID != null)
					stmt.setString(i++, coPilotID);
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				if (flightStatus != null)
					stmt.setString(i++, flightStatus);
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				if (orderStatus != null)
					stmt.setString(i++, orderStatus);
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				
				stmt.executeUpdate();
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Delete the selected employee in form.
	 * return true if the deletion was successful, else - return false
	 * @param employeeID - the employee to delete from DB
     * @return 
	 */
	public boolean removeFlight(int flightNum) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_DEL_FLIGHT)) {
				
				stmt.setLong(1, flightNum);
				stmt.executeUpdate();
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Editing a exist employee with the parameters received from the form.
	 * return true if the update was successful, else - return false
     * @return 
	 */
	public boolean editFlight(int flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, int depatureAirportID,
			int destinationAirportID, String airPlaneTailNum, String cheifPilotID, String coPilotID, String flightStatus, String orderStatus) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_FLIGHT)) {
				int i = 1;

				Timestamp depatureTimeStamp = Timestamp.valueOf(depatureTime);
				Timestamp landingTimeStamp = Timestamp.valueOf(landingTime);
				
				stmt.setInt(i++, flightNum); // can't be null
				stmt.setTimestamp(i++, depatureTimeStamp);
				stmt.setTimestamp(i++, landingTimeStamp);
				stmt.setInt(i++, depatureAirportID);
				stmt.setInt(i++, destinationAirportID);
				stmt.setString(i++, airPlaneTailNum);
				if (cheifPilotID != null)
					stmt.setString(i++, cheifPilotID);
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				if (coPilotID != null)
					stmt.setString(i++, coPilotID);
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				if (flightStatus != null)
					stmt.setString(i++, flightStatus);
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				if (orderStatus != null)
					stmt.setString(i++, orderStatus);
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				
				stmt.executeUpdate();
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}
