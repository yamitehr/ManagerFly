package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import entity.AirAttendant;
import entity.Flight;
import entity.GroundAttendant;
import entity.GroundAttendantInShift;
import entity.Shift;
import util.Consts;

public class AssignLogic {

	private static AssignLogic _instance;

	private AssignLogic() {
	}

	public static AssignLogic getInstance() {
		if (_instance == null)
			_instance = new AssignLogic();
		return _instance;
	}
	
	/**
	 * add a new air attendant to the data base
	 * @param id
	 * @param fName
	 * @param lName
	 * @param contractStart
	 * @param contractFinish
	 * @return true if added successfully 
	 */
		public boolean addAirAttendant(String id, String fName, String lName, LocalDate contractStart, LocalDate contractFinish) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_AIRATTENDANT)){			
					int i = 1;
					java.sql.Date start = java.sql.Date.valueOf( contractStart );
					stmt.setString(i++, id);
					stmt.setString(i++, fName);
					stmt.setString(i++, lName);
					stmt.setDate(i++, start);
					java.sql.Date finish = null;
					if(contractFinish != null) {
						finish = java.sql.Date.valueOf(contractFinish);
						stmt.setDate(i++, finish);
					}
					else {
						stmt.setNull(i++, java.sql.Types.DATE);
					}
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
		 * add a new ground attendant to the data base
		 * @param id
		 * @param fName
		 * @param lName
		 * @param contractStart
		 * @param contractFinish
		 * @return true if added successfully 
		 */
		public boolean addGroundAttendant(String id, String fName, String lName, LocalDate contractStart, LocalDate contractFinish) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_GROUNDATTENDANT)){			
					int i = 1;
					java.sql.Date start = java.sql.Date.valueOf( contractStart );
					stmt.setString(i++, id);
					stmt.setString(i++, fName);
					stmt.setString(i++, lName);
					stmt.setDate(i++, start);
					java.sql.Date finish = null;
					if(contractFinish != null) {
						finish = java.sql.Date.valueOf(contractFinish);
						stmt.setDate(i++, finish);
					}
					else {
						stmt.setNull(i++, java.sql.Types.DATE);
					}
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
		 * add a new Pilot to the data base
		 * @param id
		 * @param fName
		 * @param lName
		 * @param contractStart
		 * @param contractFinish
		 * @return true if added successfully 
		 */
			public boolean addPilot(String id, String fName, String lName, LocalDate contractStart, LocalDate contractFinish, String licenceID, LocalDate issuedDate) {
				try {
					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
							CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_PILOT)){			
						int i = 1;
						java.sql.Date start = java.sql.Date.valueOf(contractStart);
						java.sql.Date issued = java.sql.Date.valueOf(issuedDate);
						stmt.setString(i++, id);
						stmt.setString(i++, fName);
						stmt.setString(i++, lName);
						stmt.setDate(i++, start);
						java.sql.Date finish = null;
						if(contractFinish != null) {
							finish = java.sql.Date.valueOf(contractFinish);
							stmt.setDate(i++, finish);
						}
						else {
							stmt.setNull(i++, java.sql.Types.DATE);
						}
						stmt.setString(i++, licenceID);
						stmt.setDate(i++, issued);
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
		 * update employee details	
		 * @param id
		 * @param fName
		 * @param lName
		 * @param contractFinish
		 * @param query
		 * @return
		 */
		public boolean editEmployee(String id, String fName, String lName, LocalDate contractFinish, String query) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(query)) {
					int i = 1;
					stmt.setString(i++, fName);
					stmt.setString(i++, lName);
					java.sql.Date finish = null;
					if(contractFinish != null) {
						finish = java.sql.Date.valueOf(contractFinish);
						stmt.setDate(i++, finish);
					}
					else {
						stmt.setNull(i++, java.sql.Types.DATE);
					}
					stmt.setString(i++, id);
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
		
		public boolean deleteAirAttendantsFromFlight(Flight flight) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_DELETE_AIRATTENDANT_BY_FLIGHT)) {
					
					stmt.setString(1, flight.getFlightNum());
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
		
		public boolean addAirAttendantToFlight(AirAttendant aa, Flight f) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INSERT_AIR_ATTENDANT_FLIGHT)) {
					int i = 1;
					stmt.setString(i++, f.getFlightNum());
					stmt.setString(i++, aa.getID());
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
		 * Update the main pilot of the flight, return true
		 * if the insertion was successful, else - return false
		 * 
		 * @return
		 */
		public boolean updateMainPilot(String pilotId, String flightNum) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(util.Consts.SQL_UPDATE_MAIN_PILOT)) {
					int i = 1;
					stmt.setString(i++, pilotId);
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
		 * Update the secondary pilot of the flight, return true
		 * if the insertion was successful, else - return false
		 * 
		 * @return
		 */
		public boolean updateSecondaryPilot(String pilotId, String flightNum) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(util.Consts.SQL_UPDATE_SECONDARY_PILOT)) {
					int i = 1;
					stmt.setString(i++, pilotId);
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
		 * Adding a new shift, return true
		 * if the insertion was successful, else - return false
		 * 
		 * @return
		 */
		public boolean addShift(Shift shift) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INSERT_SHIFT)) {
					int i = 1;
					Timestamp depatureTimeStamp = Timestamp.valueOf(shift.getStart());
					Timestamp landingTimeStamp = Timestamp.valueOf(shift.getEnd());
					
					stmt.setTimestamp(i++, depatureTimeStamp);
					stmt.setTimestamp(i++, landingTimeStamp);
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
		 * Adding a new ground attendant to shift, return true
		 * if the insertion was successful, else - return false
		 * 
		 * @return
		 */
		public boolean addGroundAttendantToShift(GroundAttendantInShift gaos) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INSERT_GROUD_ATTENDANT_SHIFT)) {
					int i = 1;
					
					Timestamp depatureTimeStamp = Timestamp.valueOf(gaos.getShift().getStart());
					Timestamp landingTimeStamp = Timestamp.valueOf(gaos.getShift().getEnd());
					
					stmt.setTimestamp(1, depatureTimeStamp);
					stmt.setTimestamp(4, landingTimeStamp);
					stmt.setString(2, gaos.getGroundAttendant().getID());
					stmt.setString(3, gaos.getRole());
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
