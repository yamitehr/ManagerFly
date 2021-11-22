package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFrame;



import entity.AirPlane;
import entity.AirPort;
import entity.Flight;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import util.Consts;


public class ReportsLogic {

	private static ReportsLogic _instance;

	private ReportsLogic() {
	}

	public static ReportsLogic getInstance() {
		if (_instance == null)
			_instance = new ReportsLogic();
		return _instance;
	}
	/**
	 * 
	 * * The method create a report of the biggestFlights query which selects flights that occurred between 2 dates that the user gave, and contains at least
	 * a given number of tourists seats.
	 * @param seatsNum = the lower bound of tourists seats that the flight should contain. 
	 * @param from	= the date which  the method start collect flights.
	 * @param until = the date which  the method end collect flights.
	 * @return a new frame that contain the report
	 */
	public  JFrame compileBiggestFlights(int seatNum, LocalDate from, LocalDate until) 
	{
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR))
			{
				HashMap<String, Object> params = new HashMap<>();
				LocalDateTime fromWwithTime  = from.atTime(00, 00, 01);
				LocalDateTime toWwithTime  = until.atTime(23, 59, 59);
				java.sql.Timestamp sqlFrom = Timestamp.valueOf(fromWwithTime);
				java.sql.Timestamp sqlyeTo = Timestamp.valueOf(toWwithTime);
				params.put("p1", sqlFrom);
				params.put("p2", sqlyeTo);
				params.put("p3", seatNum);
				JasperPrint print = JasperFillManager.fillReport(
						getClass().getResourceAsStream("/boundery/BiggestFlightsReport.jasper"),
						params, conn);
				JFrame frame = new JFrame("Show Report for " + LocalDate.now());
				frame.getContentPane().add(new JRViewer(print));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.pack();
				return frame;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
	
}
	
