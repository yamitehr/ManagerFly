package entity;

import java.util.ArrayList;
import java.util.HashMap;

public class AirPlane {

	private final String tailNum;					//Primary key
	private int attendantsNum;						//required number of air attendants for flight
	private HashMap<Integer,FlightSeat> seats;			//all the seats that belong to the plane
	
	//main constructor
	public AirPlane(String tailNum, int attendantsNum) {
		
		this.tailNum = tailNum;
		this.attendantsNum = attendantsNum;
		seats = new HashMap<Integer,FlightSeat>();
	}
	
	//partial constructor
	public AirPlane(String tailNum) {
		
		this.tailNum = tailNum;		
	}
	
	//full constructor
	public AirPlane(String tailNum, int attendantsNum, HashMap<Integer,FlightSeat> seats) {
		
		this.tailNum = tailNum;
		this.attendantsNum = attendantsNum;
		this.seats = seats;
	}
	
	//getters and setters
	public String getTailNum() {
		
		return tailNum;
	}

	public int getAttendantsNum() {
		
		return attendantsNum;
	}

	public void setAttendantsNum(int attendantsNum) {
		
		this.attendantsNum = attendantsNum;
	}
	
	public HashMap<Integer,FlightSeat> getSeats() {
		
		return seats;
	}

	public void setSeats(HashMap<Integer,FlightSeat> seats) {
		
		this.seats = seats;
	}

	//hash function
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tailNum == null) ? 0 : tailNum.hashCode());
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
		AirPlane other = (AirPlane) obj;
		if (tailNum == null) {
			if (other.tailNum != null)
				return false;
		} else if (!tailNum.equals(other.tailNum))
			return false;
		return true;
	}

	//airplane to String
	@Override
	public String toString() {
		
		return "" + tailNum;
	}
	
	
}
