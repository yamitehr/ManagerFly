package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.AirPlane;
import entity.FlightSeat;
import util.Consts;

public class FlightSeatLogic {

	private static FlightSeatLogic _instance;

	private FlightSeatLogic() {
	}

	public static FlightSeatLogic getInstance() {
		if (_instance == null)
			_instance = new FlightSeatLogic();
		return _instance;
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
	 * add a new flight seat to the data base
	 * @param id = id of the seat
	 * @param row = row index
	 * @param col = col index
	 * @param type = type of the seat {"first class", "business","tourists"}
	 * @param tailNum = the id of the airplane which the seat belongs to
	 * @return true if added successfully
	 */
	public boolean addFlightSeat(int id , int row, String col , String type ,String tailNum) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHTSEATS)){			
				int i = 1;
				stmt.setInt(i++, id);
				stmt.setInt(i++, row);
				stmt.setString(i++, col);
				stmt.setString(i++, type);
				stmt.setString(i++, tailNum);
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
	 * get current biggest flight seat id from the DB.
	 * @return id of a flight seat.
	 */
	public int getBiggestFlightSeatID() {
		
		int results = 0;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_BIGGEST_FLIGHTSEAT_ID);
					ResultSet rs = stmt.executeQuery()) {
					results  = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
}
