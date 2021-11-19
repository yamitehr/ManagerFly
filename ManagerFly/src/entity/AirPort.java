package entity;

public class AirPort {

	private final int airportCode;			//Primary key
	private String city;					//city's airport
	private String country;					//country's airport
	private int timeZone;					//time zone in range of -12->12(int)
	private boolean isOpen;					//is the airport is open due to Covid 19 pandemic
	
	//main constructor
	public AirPort(int airportCode, String city, String country, int timeZone, boolean isOpen) {
		
		this.airportCode = airportCode;
		this.city = city;
		this.country = country;
		this.timeZone = timeZone;
		this.isOpen = isOpen;
	}
	
	//partial constructor
	public AirPort(int airportCode) {
		
		this.airportCode = airportCode;
	}

	//getters and setters
	public String getCity() {
		
		return city;
	}

	public void setCity(String city) {
		
		this.city = city;
	}

	public String getCountry() {
		
		return country;
	}

	public void setCountry(String country) {
		
		this.country = country;
	}

	public int getTimeZone() {
		
		return timeZone;
	}

	public void setTimeZone(int timeZone) {
		
		this.timeZone = timeZone;
	}

	public boolean isOpen() {
		
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		
		this.isOpen = isOpen;
	}

	public int getAirportCode() {
		
		return airportCode;
	}

	//hash function
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + airportCode;
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
		AirPort other = (AirPort) obj;
		if (airportCode != other.airportCode)
			return false;
		return true;
	}

	//airport to String
	@Override
	public String toString() {
		
		return "AirPort [airportCode=" + airportCode + ", city=" + city + ", country=" + country + ", timeZone="
				+ timeZone + ", isOpen=" + isOpen + "]";
	}
	
	
	
}
