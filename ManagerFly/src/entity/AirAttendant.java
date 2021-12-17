package entity;

import java.time.LocalDate;

public class AirAttendant extends Employee{

	
	//main constructor
	public AirAttendant(String iD, String firstName, String lastName, LocalDate contractStart,
			LocalDate contractFinish) {
		
		super(iD, firstName, lastName, contractStart, contractFinish);		
	}
	
	//partial constructor
	public AirAttendant(String iD) {
		
		super(iD);
	}
}
