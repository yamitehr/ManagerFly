package entity;

import java.time.LocalDate;

public abstract class Employee {

	private final String ID;				//id of the worker (primary key)
	private String firstName;				//worker first name
	private String lastName;				//worker last name
	private LocalDate contractStart;		//contract start date
	private LocalDate contractFinish;		//contract finish date
	
	//main constructor
	public Employee(String iD, String firstName, String lastName, LocalDate contractStart, LocalDate contractFinish) {
	
		ID = iD;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contractStart = contractStart;
		this.contractFinish = contractFinish;
	}
	
	//partial constructor
	public Employee(String iD) {
		ID = iD;
	}
	
	//getters and setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getContractStart() {
		return contractStart;
	}

	public void setContractStart(LocalDate contractStart) {
		this.contractStart = contractStart;
	}

	public LocalDate getContractFinish() {
		return contractFinish;
	}

	public void setContractFinish(LocalDate contractFinish) {
		this.contractFinish = contractFinish;
	}

	public String getID() {
		return ID;
	}

	//equals hash code and to string
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ID = " + ID + ", first name = " + firstName + ", last name = " + lastName;
	}
	
	public String fullToString() {
		
		return toString() + ", contract start date = "
				+ contractStart + ", contract finish date = " + contractFinish;
	}
	
	
}
