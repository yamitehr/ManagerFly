package entity;

import java.time.LocalDate;

public class Pilot extends Employee{

	private String LicenceID;				//flight license id
	private LocalDate issuedDate;			//the date the flight license was issued
	
	//main constructor
	public Pilot(String iD, String firstName, String lastName, LocalDate contractStart, LocalDate contractFinish,
			String licenceID, LocalDate issuedDate) {
		
		super(iD, firstName, lastName, contractStart, contractFinish);
		LicenceID = licenceID;
		this.issuedDate = issuedDate;
	}
	
	//partial constructor
	public Pilot(String iD) {
		
		super(iD);
	}

	//getters setters and to string
	public String getLicenceID() {
		
		return LicenceID;
	}

	public void setLicenceID(String licenceID) {
		
		LicenceID = licenceID;
	}

	public LocalDate getIssuedDate() {
		
		return issuedDate;
	}

	public void setIssuedDate(LocalDate issuedDate) {
		
		this.issuedDate = issuedDate;
	}

	@Override
	
	public String toString() {
		return super.toString() + " Licence ID = " + LicenceID + ", issued Date = " + issuedDate;
	}
	
	
}
