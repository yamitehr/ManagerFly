package entity;

import java.time.LocalDateTime;

public class GroundAttendantInShift {
	private Shift shift;
	private GroundAttendant groundAttendant;
	private String role;
	public GroundAttendantInShift(Shift shift, GroundAttendant groundAttendant, String role) {
		super();
		this.shift = shift;
		this.groundAttendant = groundAttendant;
		this.role = role;
	}
	public Shift getShift() {
		return shift;
	}
	public void setShift(Shift shift) {
		this.shift = shift;
	}
	public GroundAttendant getGroundAttendant() {
		return groundAttendant;
	}
	public void setGroundAttendant(GroundAttendant groundAttendant) {
		this.groundAttendant = groundAttendant;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "GroundAttendantInShift [shift=" + shift + ", groundAttendant=" + groundAttendant + ", role=" + role
				+ "]";
	}
	
	
	
	
}
