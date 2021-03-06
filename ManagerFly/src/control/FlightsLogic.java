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
import java.util.Locale;

import boundery.FlightManagmentFrm;
import entity.AirPlane;
import entity.AirPort;
import entity.Flight;
import exceptions.InvalidInputException;
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
 * Validation on Plane overlappse
 * @param airplane
 * @param startDate
 * @param endDate
 * @param currFlightID
 * @return
 */
	
		public boolean isPlaneOverlapping(AirPlane airplane, LocalDateTime startDate, LocalDateTime endDate){
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			
			String depatureDateStr = sdf.format(Timestamp.valueOf(startDate));
			String landingDateStr = sdf.format(Timestamp.valueOf(endDate));
			
			String query = "SELECT SerialNum FROM FlightTbl WHERE (((FlightTbl.AirPlaneTailNum)='"
							+ airplane.getTailNum() + "') AND ((DateValue(FlightTbl.DepatureTime))<=#"
							+landingDateStr+ "#) AND ((DateValue(FlightTbl.LandingTime))>=#"+depatureDateStr+"#))";
			ArrayList<String> results = new ArrayList<String>();
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(query);
						ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int i = 1;
						
						String flightID = rs.getString(i++);
						if(FlightManagmentFrm.getCurrentFlight() == null || !FlightManagmentFrm.getCurrentFlight().getFlightNum().equals(flightID))
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
	
		/**
		 * Validation on Airports overlappse
		 * @param airport
		 * @param dateTime
		 * @param isDeparture
		 * @param currFlightID
		 * @return
		 */
		public boolean isAirportsOverlapping(AirPort airport, LocalDateTime dateTime, boolean isDeparture){
			
			String airportType;
			String timeType;
			if(isDeparture) {
				airportType = "FlightTbl.DepatureAirportID";
				timeType = "FlightTbl.DepatureTime";
			} else {
				airportType = "FlightTbl.DestinationAirportID";
				timeType = "FlightTbl.LandingTime";
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy KK:mm:ss a", Locale.ENGLISH);
			
			String timeStampPlusHalfHour = sdf.format(Timestamp.valueOf(dateTime.plusMinutes(30)));
			String timeStampMinusHalfHour = sdf.format(Timestamp.valueOf(dateTime.minusMinutes(30)));
			
			
			String query = "SELECT FlightTbl.SerialNum "
					+ "FROM FlightTbl "
					+ "WHERE (((" + airportType + ")="
					+ airport.getAirportCode() +") "
					+ "AND ((" + timeType + ")>=#" + timeStampMinusHalfHour + "#) "
					+ "AND ((" + timeType + ")<=#" + timeStampPlusHalfHour + "#));";
			ArrayList<String> results = new ArrayList<String>();
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(query);
						ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int i = 1;
						
						String flightID = rs.getString(i++);
						if(FlightManagmentFrm.getCurrentFlight() == null || !FlightManagmentFrm.getCurrentFlight().getFlightNum().equals(flightID))
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

	/**
	 * Adding a new Employee with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 * @throws InvalidInputException 
	 */
	public boolean addFlight(String flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, AirPort depatureAirport,
			AirPort destinationAirport, AirPlane airplane, String cheifPilotID, String coPilotID, String flightStatus) throws InvalidInputException {
		
		if(isAirportsOverlapping(depatureAirport, depatureTime, true)) {
			if(isAirportsOverlapping(destinationAirport, landingTime, false)) {
				if(isPlaneOverlapping(airplane, depatureTime, landingTime)) {
					try {
						Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
						try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
								CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHT)){
							
							int i = 1;
							Timestamp depatureTimeStamp = Timestamp.valueOf(depatureTime);
							Timestamp landingTimeStamp = Timestamp.valueOf(landingTime);
							
							stmt.setString(i++, flightNum); // can't be null
							stmt.setTimestamp(i++, depatureTimeStamp);
							stmt.setTimestamp(i++, landingTimeStamp);
							stmt.setInt(i++, depatureAirport.getAirportCode());
							stmt.setInt(i++, destinationAirport.getAirportCode());
							stmt.setString(i++, airplane.getTailNum());
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
							stmt.setString(i++, "Init");
							stmt.executeUpdate();
							return true;
							
						} catch (SQLException e) {
						}
					} catch (ClassNotFoundException e) {
					}
				} else {
					throw new InvalidInputException("Airplane is already taken by another flight");
				}
			} else {
				throw new InvalidInputException("Please select a different Landing airport - flights collison");
			}
		} else {
			throw new InvalidInputException("Please select a different Departue airport - flights collison");
		}
		
		return false;
	}

	/**
	 * add a new airport to the data base
	 * @param airPortCode = primary key of the airport
	 * @param city = city which the airport is locate at
	 * @param country = country which the airport is locate at
	 * @param timeZone = time zone of the place according to GMT {in range of -12 -> 12}
	 * @return true if added successfully 
	 */
		public boolean addAirPort(int id, String city, String country, int GMT ) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_AIRPORT)){			
					int i = 1;
					
					stmt.setInt(i++, id); // can't be null
					stmt.setString(i++, city);
					stmt.setString(i++, country);
					stmt.setInt(i++, GMT);
					stmt.setBoolean(i++, true);
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
	 * Editing a exist employee with the parameters received from the form.
	 * return true if the update was successful, else - return false
     * @return 
	 */
	public boolean editFlight(String flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, String airPlaneTailNum) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_FLIGHT)) {
				int i = 1;

				Timestamp depatureTimeStamp = Timestamp.valueOf(depatureTime);
				Timestamp landingTimeStamp = Timestamp.valueOf(landingTime);
				
				// can't be null
				stmt.setTimestamp(i++, depatureTimeStamp);
				stmt.setTimestamp(i++, landingTimeStamp);
				stmt.setString(i++, airPlaneTailNum);
				stmt.setString(i++, flightNum);
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
	 * update airport status
	 * @param isOpen
	 * @param airPortId
	 * @return
	 */
	public boolean editAirPortStatus(boolean isOpen, int airPortId) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_AIRPORT_STATUS)) {
				int i = 1;

				stmt.setBoolean(i++, isOpen);
				stmt.setInt(i++, airPortId);
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
	
	public boolean editFlightStatus(String status, int airportCode) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_FLIGHT_STATUS)) {
				int i = 1;

				stmt.setString(i++, status);
				stmt.setInt(i++, airportCode);
				stmt.setInt(i++, airportCode);
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
