package entity;

import java.time.LocalDateTime;


public class Flight {

	private final int flightNum;			//Primary key
	private LocalDateTime depatureTime;				//departure time and date
	private LocalDateTime landingTime;				//landing time and date
	private String flightStatus;			//flight status {on time,  canclelled, delayed}
	private  int depatureAirportID;			//Foreign key, depature airport id
	private  int destinationAirportID;		//Foreign key, destination airport id
	private  String airPlaneTailNum;		//Foreign key, airplane tail number of the flight
	private String cheifPilotID;			//Foreign key, cheif pilot id
	private String coPilotID;				//Foreign key, co - pilot id
	private String orderStatus;				//tickets order status {initialize, pre-sale, regular sale}
	
	
	//main constructor
	public Flight(int flightNum, LocalDateTime depatureTime, LocalDateTime landingTime, String flightStatus, int depatureAirportID,
			int destinationAirportID, String airPlaneTailNum, String cheifPilotID, String coPilotID,
			String orderStatus) {
		
		this.flightNum = flightNum;
		this.depatureTime = depatureTime;
		this.landingTime = landingTime;
		this.flightStatus = flightStatus;
		this.depatureAirportID = depatureAirportID;
		this.destinationAirportID = destinationAirportID;
		this.airPlaneTailNum = airPlaneTailNum;
		this.cheifPilotID = cheifPilotID;
		this.coPilotID = coPilotID;
		this.orderStatus = orderStatus;
	}
	
	//partial constructor
	public Flight(int flightNum) {
		
		this.flightNum = flightNum;
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

	public int getDepatureAirportID() {
		
		return depatureAirportID;
	}

	public void setDepatureAirportID(int depatureAirportID) {
		
		this.depatureAirportID = depatureAirportID;
	}

	public int getDestinationAirportID() {
		
		return destinationAirportID;
	}

	public void setDestinationAirportID(int destinationAirportID) {
		
		this.destinationAirportID = destinationAirportID;
	}

	public String getAirPlaneTailNum() {
		
		return airPlaneTailNum;
	}

	public void setAirPlaneTailNum(String airPlaneTailNum) {
		
		this.airPlaneTailNum = airPlaneTailNum;
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

	public String getOrderStatus() {
		
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		
		this.orderStatus = orderStatus;
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
				+ ", flightStatus=" + flightStatus + ", depatureAirportID=" + depatureAirportID
				+ ", destinationAirportID=" + destinationAirportID + ", airPlaneTailNum=" + airPlaneTailNum
				+ ", cheifPilotID=" + cheifPilotID + ", coPilotID=" + coPilotID + ", orderStatus=" + orderStatus + "]";
	}
		
}
