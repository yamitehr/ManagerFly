package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.AirPlane;
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
}
