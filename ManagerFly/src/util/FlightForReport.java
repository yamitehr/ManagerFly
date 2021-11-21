package util;

import java.time.LocalDateTime;

import entity.AirPlane;
import entity.AirPort;

public class FlightForReport {

	private String  flightID;			
	private String planeID;
	private  String CountryFrom;
	private  String cityFrom;
	private  String countryTo;
	private  String cityTo;
	private String depTime;				
	private String LandTime;				
	private String status;
	
	public FlightForReport(String flightID, String planeID, String countryFrom, String cityFrom, String countryTo,
			String cityTo, String depTime, String landTime, String status) {
		
		this.flightID = flightID;
		this.planeID = planeID;
		CountryFrom = countryFrom;
		this.cityFrom = cityFrom;
		this.countryTo = countryTo;
		this.cityTo = cityTo;
		this.depTime = depTime;
		LandTime = landTime;
		this.status = status;
	}

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public String getPlaneID() {
		return planeID;
	}

	public void setPlaneID(String planeID) {
		this.planeID = planeID;
	}

	public String getCountryFrom() {
		return CountryFrom;
	}

	public void setCountryFrom(String countryFrom) {
		CountryFrom = countryFrom;
	}

	public String getCityFrom() {
		return cityFrom;
	}

	public void setCityFrom(String cityFrom) {
		this.cityFrom = cityFrom;
	}

	public String getCountryTo() {
		return countryTo;
	}

	public void setCountryTo(String countryTo) {
		this.countryTo = countryTo;
	}

	public String getCityTo() {
		return cityTo;
	}

	public void setCityTo(String cityTo) {
		this.cityTo = cityTo;
	}

	public String getDepTime() {
		return depTime;
	}

	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}

	public String getLandTime() {
		return LandTime;
	}

	public void setLandTime(String landTime) {
		LandTime = landTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
		
}
