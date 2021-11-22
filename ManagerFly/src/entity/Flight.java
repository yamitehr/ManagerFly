package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Flight {

	private final int flightNum;			//Primary key
	private LocalDateTime depatureTime;				//departure time and date
	private LocalDateTime landingTime;				//landing time and date
	private String flightStatus;			//flight status {on time,  cancelled, delayed}
	private  AirPort depatureAirport;			//Foreign key, depature airport id
	private  AirPort destinationAirport;		//Foreign key, destination airport id
	private  AirPlane airPlaneTail;		//Foreign key, airplane tail number of the flight
	private String cheifPilotID;			//TODO: change to object pilot
	private String coPilotID;				//TODO: change to object pilot
	
	
	/**
	 * main constructor
	 * @param flightNum
	 * @param depatureTime
	 * @param landingTime
	 * @param depatureAirportID
	 * @param destinationAirportID
	 * @param airPlaneTailNum
	 * @param cheifPilotID
	 * @param coPilotID
	 */
	public Flight(int flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, AirPort depatureAirportID,
			AirPort destinationAirportID, AirPlane airPlaneTailNum, String cheifPilotID, String coPilotID) {
		
		this.flightNum = flightNum;
		this.depatureTime = depatureTime;
		this.landingTime = landingTime;
		//TODO: take default status from DB
		this.depatureAirport = depatureAirportID;
		this.destinationAirport = destinationAirportID;
		this.airPlaneTail = airPlaneTailNum;
		this.cheifPilotID = cheifPilotID;
		this.coPilotID = coPilotID;
	}
	
	/**
	 * partial constructor
	 * @param flightNum
	 */
	public Flight(int flightNum) {
		
		this.flightNum = flightNum;
	}

	/**
	 * biggestFlyReport constructor
	 * @param flightNum
	 * @param depatureTime
	 * @param landingTime
	 * @param flightStatus
	 */
	public Flight(int flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, String flightStatus) {
		
		this.flightNum = flightNum;
		this.depatureTime = depatureTime;
		this.landingTime = landingTime;
		this.flightStatus = flightStatus;
	}
	
	/**
	 * biggestFlyReport constructor
	 * @param flightNum
	 * @param depatureTime
	 * @param landingTime
	 * @param flightStatus
	 */
	public Flight(int flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, String flightStatus ,AirPlane airPlane , AirPort dep, AirPort dest) {
		
		this.flightNum = flightNum;
		this.depatureTime = depatureTime;
		this.landingTime = landingTime;
		this.flightStatus = flightStatus;
		this.airPlaneTail = airPlane;
		this.depatureAirport = dep;
		this.destinationAirport = dest;
	}
	
	/**
	 * full parameters constructor
	 * @param flightNum
	 * @param depatureTime
	 * @param landingTime
	 * @param flightStatus
	 * @param depatureAirportID
	 * @param destinationAirportID
	 * @param airPlaneTailNum
	 * @param cheifPilotID
	 * @param coPilotID
	 * @param orderStatus
	 */
	public Flight(int flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, String flightStatus,
			AirPort depatureAirportID, AirPort destinationAirportID, AirPlane airPlaneTailNum, String cheifPilotID,
			String coPilotID) {
		
		this.flightNum = flightNum;
		this.depatureTime = depatureTime;
		this.landingTime = landingTime;
		this.flightStatus = flightStatus;
		this.depatureAirport = depatureAirportID;
		this.destinationAirport = destinationAirportID;
		this.airPlaneTail = airPlaneTailNum;
		this.cheifPilotID = cheifPilotID;
		this.coPilotID = coPilotID;
	}

	//getters and setters
	public LocalDateTime getDepatureTime() {
		return depatureTime;
	}

	public void setDepatureTime(LocalDateTime depatureTime) {
		
		this.depatureTime = depatureTime;
	}

	public LocalDateTime getLandingTime() {
		
		return landingTime;
	}

	public void setLandingTime(LocalDateTime landingTime) {
		
		this.landingTime = landingTime;
	}

	public String getFlightStatus() {
		
		return flightStatus;
	}

	public void setFlightStatus(String flightStatus) {
		
		this.flightStatus = flightStatus;
	}

	public AirPort getDepatureAirportID() {
		
		return depatureAirport;
	}

	public void setDepatureAirportID(AirPort depatureAirportID) {
		
		this.depatureAirport = depatureAirportID;
	}

	public AirPort getDestinationAirportID() {
		
		return destinationAirport;
	}

	public void setDestinationAirportID(AirPort destinationAirportID) {
		
		this.destinationAirport = destinationAirportID;
	}

	public AirPlane getAirPlaneTailNum() {
		
		return airPlaneTail;
	}

	public void setAirPlaneTailNum(AirPlane airPlaneTailNum) {
		
		this.airPlaneTail = airPlaneTailNum;
	}

	public String getCheifPilotID() {
		
		return cheifPilotID;
	}

	public void setCheifPilotID(String cheifPilotID) {
		
		this.cheifPilotID = cheifPilotID;
	}

	public String getCoPilotID() {
		
		return coPilotID;
	}

	public void setCoPilotID(String coPilotID) {
		
		this.coPilotID = coPilotID;
	}

	public int getFlightNum() {
		
		return flightNum;
	}

	//hash function
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + flightNum;
		return result;
	}

	//equals method
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (flightNum != other.flightNum)
			return false;
		return true;
	}

	//flight to String
	@Override
	public String toString() {
		
		return "Flight [flightNum=" + flightNum + ", depatureTime=" + depatureTime + ", LandingTime=" + landingTime
				+ ", flightStatus=" + flightStatus + ", depatureAirportID=" + depatureAirport
				+ ", destinationAirportID=" + destinationAirport + ", airPlaneTailNum=" + airPlaneTail
				+ ", cheifPilotID=" + cheifPilotID + ", coPilotID=" + coPilotID + "]";
	}
	
	public String toStringForReport() {
		
		return "flight num = " + this.getFlightNum() +  " from = " + this.getDepatureAirportID().getCountry() + " " + this.getDepatureAirportID().getCity() + " to = " + this.getDestinationAirportID().getCountry() + " " + this.getDestinationAirportID().getCity() + " DepTime = " + getDepatureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " LandingTime = " + getLandingTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " FlightStatus = " + this.getFlightStatus() + "";
	}
		
}
