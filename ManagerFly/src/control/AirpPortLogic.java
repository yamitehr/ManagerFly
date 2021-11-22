package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.AirPort;
import util.Consts;

public class AirpPortLogic {

	private static AirpPortLogic _instance;

	private AirpPortLogic() {
	}

	public static AirpPortLogic getInstance() {
		if (_instance == null)
			_instance = new AirpPortLogic();
		return _instance;
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
}
