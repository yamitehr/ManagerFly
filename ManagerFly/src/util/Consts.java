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
	
	
	/*----------------------------------------- REPORTS QUERIES -----------------------------------------*/
	/*-----------------------------------------for BiggestFlyReport--------------------------------------*/
	/**
	 * return a list of planes and the number of tourists seats in them  
	 */
	public static final String SQL_SEL_CNT_TSEAT_BY_PLANE = "SELECT AirPlaneTbl.TailNum, Count(FlightSeatTbl.ID) AS CountOfID\r\n"
			+ "FROM AirPlaneTbl INNER JOIN FlightSeatTbl ON AirPlaneTbl.TailNum = FlightSeatTbl.TailNum\r\n"
			+ "WHERE (((FlightSeatTbl.type) Like \"tourists\"))\r\n"
			+ "GROUP BY AirPlaneTbl.TailNum;";
	/**
	 * String that present a query to return flights in range of to dates
	 * @param date1 = string of the date which  the method start collect flights.
	 * @param date2 = string of the date which  the method end collect flights.
	 * @return a string that present the qryFlightsInRange
	 */
	public static String flightsInRangeQuery(String date1, String date2) {
		
		return "SELECT FlightTbl.SerialNum, FlightTbl.AirPlaneTailNum, AirPortTbl.Country, AirPortTbl.City, AirPortTbl_1.Country, AirPortTbl_1.City, FlightTbl.DepatureTime, FlightTbl.LandingTime, FlightTbl.Status, AirPortTbl.airportCode, AirPortTbl_1.airportCode\r\n"
				+ "FROM AirPortTbl INNER JOIN (AirPortTbl AS AirPortTbl_1 INNER JOIN FlightTbl ON AirPortTbl_1.airportCode = FlightTbl.DestinationAirportID) ON AirPortTbl.airportCode = FlightTbl.DepatureAirportID\r\n"
				+ "WHERE (((FlightTbl.DepatureTime)>= " + date1 + "  And (FlightTbl.DepatureTime)<= " + date2 + ") AND ((FlightTbl.LandingTime)>= " + date1 + " And (FlightTbl.LandingTime)<= " + date2 + "))\r\n"
				+ "GROUP BY FlightTbl.SerialNum, FlightTbl.AirPlaneTailNum, AirPortTbl.Country, AirPortTbl.City, AirPortTbl_1.Country, AirPortTbl_1.City, FlightTbl.DepatureTime, FlightTbl.LandingTime, FlightTbl.Status, AirPortTbl.airportCode, AirPortTbl_1.airportCode\r\n"
				+ "ORDER BY AirPortTbl_1.Country DESC , AirPortTbl_1.City DESC , FlightTbl.DepatureTime DESC , FlightTbl.LandingTime DESC;";
	}
	
	/**
	 * String that present a query to return flights in range of to dates and that has ta list input amount of tourists seats
	 * @param date1 = from date String
	 * @param date2 = until date String
	 * @param num = tourists seat
	 * @return String that present a query
	 */
	public static  String qryBiggestFlightsStr(String date1, String date2, int num) {
		
		return "SELECT FlightTbl.SerialNum, AirPortTbl.Country, AirPortTbl.City, AirPortTbl_1.Country, AirPortTbl_1.City, FlightTbl.DepatureTime, FlightTbl.LandingTime, FlightTbl.Status\r\n"
				+ "FROM AirPortTbl INNER JOIN (qryCntTouristsInPlane INNER JOIN (AirPortTbl AS AirPortTbl_1 INNER JOIN FlightTbl ON AirPortTbl_1.airportCode = FlightTbl.DestinationAirportID) ON qryCntTouristsInPlane.TailNum = FlightTbl.AirPlaneTailNum) ON AirPortTbl.airportCode = FlightTbl.DepatureAirportID\r\n"
				+ "WHERE (((FlightTbl.DepatureTime)>= " + date1 + " And (FlightTbl.DepatureTime)<= " + date2 + " ) AND ((FlightTbl.LandingTime)>= " + date1 + " And (FlightTbl.LandingTime)<= " + date2 + " ) AND ((qryCntTouristsInPlane.CountOfID)>= " + num + " ))\r\n"
				+ "GROUP BY FlightTbl.SerialNum, AirPortTbl.Country, AirPortTbl.City, AirPortTbl_1.Country, AirPortTbl_1.City, FlightTbl.DepatureTime, FlightTbl.LandingTime, FlightTbl.Status\r\n"
				+ "ORDER BY AirPortTbl_1.Country DESC , AirPortTbl_1.City DESC , FlightTbl.DepatureTime DESC , FlightTbl.LandingTime DESC;";
	} 
	/*-----------------------------------------------------------------------------------------------------*/		
	
	
	
	
	
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
	
	/*----------------------------------------- FLIGHTS QUERIES -----------------------------------------*/
	public static final String SQL_SEL_FLIGHT = "SELECT * FROM FlightTbl";
	public static final String SQL_DEL_FLIGHT = "{ call qryDelFlight(?) }";
	public static final String SQL_INS_FLIGHT = "{ call qryInsFlight(?,?,?,?,?,?,?,?,?,?) }";
	public static final String SQL_UPD_FLIGHT = "{ call qryUpdFlight(?,?,?,?,?,?,?,?,?,?,?) }";

	/*----------------------------------------- AIRPORTS QUERIES -----------------------------------------*/
	public static final String SQL_SEL_AIRPORT = "SELECT * FROM AirPortTbl";
	
	/*----------------------------------------- AIRPLANES QUERIES -----------------------------------------*/
	public static final String SQL_SEL_AIRPLANE = "SELECT * FROM AirPlaneTbl";
	
}

