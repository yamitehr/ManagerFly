package entity;

import java.time.LocalDate;

public class GroundAttendant extends Employee{

	//main constructor
	public GroundAttendant(String iD, String firstName, String lastName, LocalDate contractStart,
			LocalDate contractFinish) {
		
		super(iD, firstName, lastName, contractStart, contractFinish);
	}

	//partial constructor
	public GroundAttendant(String iD) {
		
		super(iD);
	}
}
