package control;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xmlbeans.impl.soap.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.Consts;
import entity.AirPlane;
import entity.AirPort;
import entity.Flight;

public class ImportXML {
	private static ImportXML _instance;
	private ImportXML() {
	}

	public static ImportXML getInstance() {
		if (_instance == null)
			_instance = new ImportXML();
		return _instance;
	}
	/**
	 * get the status of the flight from xml file
	 * 
	 * @return
	 */
	public HashMap<String, String> importFlightsFromXML() {
		HashMap<String, String> results = new HashMap<String, String>();
    	boolean f = true;
    	String path = "xml/flightStatus.xml";
    	try {
			Document doc = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder().parse(new File(path));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("flight");
			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nl.item(i);
					String flightNum = el.getAttribute("Id");
					String status = el.getElementsByTagName("status").item(0).getTextContent();
					boolean flag = vertiftFlight(flightNum);
					if(flag == false)
					{
						JOptionPane.showMessageDialog(null,"Flight doesn't exists");
					}
					else
					{
						results.put(flightNum, status);
						f = updateFlightStatus(flightNum,status);
					}
				}
			}
			if(f)
    		{
    			JOptionPane.showMessageDialog(null,"flights data imported successfully!");
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(null,"flights data imported with errors!");
    		}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
    	return results;
    }
	
	/**
	 * check if the flight exists in the db
	 * return true if exists or false otherwise
	 * 
	 * @return
	 */
	public boolean vertiftFlight(String flightNum) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_CHECK_FLIGHT)) {
				stmt.setString(1, flightNum); // can't be null
				ResultSet rs = stmt.executeQuery(); 
				if(rs.next()) {
					return true;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * update the status of the flight 
	 * return true if updated or false otherwise
	 * 
	 * @return
	 */
	public boolean updateFlightStatus(String flightNum, String status) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPDATE_FLIGHT_STATUS)) {
				int i = 1;
				stmt.setString(i++, status); // can't be null
				stmt.setString(i++, flightNum); // can't be null
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
	 * get all of the canceled flights
	 * 
	 * @return ArrayList of flights.
	 * @throws Exception
	 */
	public ArrayList<Flight> getCanceledFlights() {
		ArrayList<Flight> results = new ArrayList<Flight>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_CANCELES_FLIGHTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					String flightID = rs.getString(i++);
					LocalDateTime depTime = rs.getTimestamp(i++).toInstant()
						      .atZone(ZoneId.systemDefault())
						      .toLocalDateTime();
					LocalDateTime arrTime = rs.getTimestamp(i++).toInstant()
						      .atZone(ZoneId.systemDefault())
						      .toLocalDateTime();
					
					
					results.add(new Flight(flightID, depTime, arrTime, rs.getString(9), new AirPort(rs.getInt(i++)), new AirPort(rs.getInt(i++)),
								new AirPlane(rs.getString(i++)), rs.getString(i++), rs.getString(i++),rs.getString(10)));
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
