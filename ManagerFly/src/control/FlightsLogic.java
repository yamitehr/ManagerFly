package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

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
	 * fetches all employees from DB file.
	 * @return ArrayList of employees.
	 */
	/*int flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, String flightStatus, int depatureAirportID,
			int destinationAirportID, String airPlaneTailNum, String cheifPilotID, String coPilotID,
			String orderStatus*/
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
					LocalDateTime depTime = rs.getDate(i++).toInstant()
						      .atZone(ZoneId.systemDefault())
						      .toLocalDateTime();
					LocalDateTime arrTime = rs.getDate(i++).toInstant()
						      .atZone(ZoneId.systemDefault())
						      .toLocalDateTime();
					
					
					results.add(new Flight(flightID, depTime, arrTime, rs.getInt(i++), rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/*----------------------------------------- ADD / REMOVE / UPDATE EMPLOYEE METHODS --------------------------------------------*/

	/**
	 * Adding a new Employee with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addFlight(int flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, int depatureAirportID,
			int destinationAirportID, String airPlaneTailNum, String cheifPilotID, String coPilotID) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHT)) {
				
				int i = 1;
				Timestamp depatureTimeStamp = Timestamp.valueOf(depatureTime);
				Timestamp landingTimeStamp = Timestamp.valueOf(landingTime);
				
				stmt.setInt(i++, flightNum); // can't be null
				stmt.setTimestamp(i++, depatureTimeStamp);
				stmt.setTimestamp(i++, landingTimeStamp);
				stmt.setInt(i++, depatureAirportID);
				stmt.setInt(i++, destinationAirportID);
				stmt.setString(i++, airPlaneTailNum);
				stmt.setString(i++, cheifPilotID);
				stmt.setString(i++, coPilotID);
				
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
			int destinationAirportID, String airPlaneTailNum, String cheifPilotID, String coPilotID) {
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
				stmt.setString(i++, cheifPilotID);
				stmt.setString(i++, coPilotID);
				
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
