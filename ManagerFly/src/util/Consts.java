package util;

import java.net.URLDecoder;

public class Consts {

	private Consts() {
		
		throw new AssertionError();
	}
	
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
	public static final String JDBC_STR = "net.ucanaccess.jdbc.UcanaccessDriver";
	/*----------------------------------------- CONSTS VALUES -----------------------------------------*/
	
	/**flight status's {"on time", "delayed", "canclelled"}
	 * 
	 */
	public static final String[] FLIGHT_STATUS = {"on time", "delayed", "canclelled"};		
	/**order statu's for flight {"initialize", "pre-sale","regular-sale"}
	 * 
	 */
	public static final String[] ORDER_STATUS = {"initialize", "pre-sale","regular-sale"};
	/**
	 * seat type's {"first class", "business","tourists"}
	 */
	public static final String[] SEAT_TYPES = {"first class", "business","tourists"};
	/**
	 * attendants roles for shift {"validate tickets","allocate seats","tag and send luggage"}
	 */
	public static final String[] SHIFT_ROLE = {"validate tickets","allocate seats","tag and send luggage"};	
	/**
	 * Min pilots for pre-sale
	 */
	public static final int MIN_PILOTS = 2;	
	/**
	 * Min air attendants for pre-sale
	 */
	public static final int MIN_AIR_ATTENDANTS = 2;	 
	/**
	 * max chars for col number of the seat
	 */
	public static final int MAX_COL_NUM = 1;		
	/**
	 * max chars for the city field
	 */
	public static final int MAX_CITY_CHARS = 40;		
	/**
	 * max chars for the country field
	 */
	public static final int MAX_COUNTRY_CHARS = 40;	
	/**
	 * max chars for airplane tail number
	 */
	public static final int MAX_TAIL_NUM = 15;
	
	
	/*----------------------------------------- FLIGHTS QUERIES -----------------------------------------*/
	public static final String SQL_SEL_FLIGHT = "SELECT * FROM FlightTbl";
	public static final String SQL_DEL_FLIGHT = "{ call qryDelFlight(?) }";
	public static final String SQL_INS_FLIGHT = "{ call qryInsFlight(?,?,?,?,?,?,?,?,?) }";
	public static final String SQL_UPD_FLIGHT = "{ call qryUpdFlight(?,?,?,?) }";

	/*----------------------------------------- AIRPORTS QUERIES -----------------------------------------*/
	public static final String SQL_SEL_AIRPORT = "SELECT * FROM AirPortTbl";
	public static final String SQL_INS_AIRPORT = "{ call qryInsAirPort(?,?,?,?) }";
	
	/*----------------------------------------- AIRPLANES QUERIES -----------------------------------------*/
	public static final String SQL_SEL_AIRPLANE = "SELECT * FROM AirPlaneTbl";
	public static final String SQL_INS_AIRPLANE = "{ call qryInsAirPlane(?,?) }";
	
	/*------------------------------------------FLIGHT SEATS QUERIES ---------------------------------------*/
	public static final String SQL_SEL_FLIGHTSEATS = "SELECT * FROM FlightSeatTbl";
	public static final String SQL_INS_FLIGHTSEATS = "{ call qryInsFlightSeat(?,?,?,?,?) }";
	
	
	
	/**
	 * find the correct path of the DB file
     * @return the path of the DB file (from eclipse or with runnable file)
	 */
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			// System.out.println(decoded) - Can help to check the returned path
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				return decoded + "/database/DB_ManagerFly.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				System.out.println(decoded);
				return decoded + "src/entity/DB_ManagerFly.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

