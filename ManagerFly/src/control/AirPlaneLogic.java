package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.AirPlane;
import entity.FlightSeat;
import util.Consts;

public class AirPlaneLogic {

	private static AirPlaneLogic _instance;

	private AirPlaneLogic() {
	}

	public static AirPlaneLogic getInstance() {
		if (_instance == null)
			_instance = new AirPlaneLogic();
		return _instance;
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
	 * add a new airplane to the data base
	 * @param tailNum = the id of the airplane example: A-345X
	 * @param attendantsNum = the amount of necessary air attendants for the flight 
	 * @return true if added successfully
	 */
	public boolean addAirPlane(String tailNum, int attendantsNum) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_AIRPLANE)){			
				int i = 1;
				
				stmt.setString(i++, tailNum);
				stmt.setInt(i++, attendantsNum);
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
