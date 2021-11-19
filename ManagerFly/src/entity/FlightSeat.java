package entity;

public class FlightSeat {

	private final int seatID;						//Primary key
	private int rowNum;								//row number
	private String colNum;							//col number (1 char)
	private String seatType;						//type {first class, business,tourists}
	private String tailNum;					//Foreign key, this is the tail number of the plane that the seat belongs to  
	 
	//main constructor
	public FlightSeat(int seatID, int rowNum, String colNum, String seatType, String tailNum) {
		
		this.seatID = seatID;
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.seatType = seatType;
		this.tailNum = tailNum;
	}
	
	//partial constructor
	public FlightSeat(int seatID) {
		
		this.seatID = seatID;
	}

	//getters and setters
	public int getRowNum() {
		
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		
		this.rowNum = rowNum;
	}

	public String getColNum() {
		return colNum;
	}

	public void setColNum(String colNum) {
		
		this.colNum = colNum;
	}

	public String getSeatType() {
		
		return seatType;
	}

	public void setSeatType(String seatType) {
		
		this.seatType = seatType;
	}

	public int getSeatID() {
		
		return seatID;
	}

	public String getTailNum() {
		
		return tailNum;
	}
	
	public void setTailNum(String getTailNum) {
		
		this.tailNum = getTailNum;
	}
	
	//hash function
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + seatID;
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
		FlightSeat other = (FlightSeat) obj;
		if (seatID != other.seatID)
			return false;
		return true;
	}

	//flight seat to String
	@Override
	public String toString() {
		
		return "FlightSeat [seatID=" + seatID + ", rowNum=" + rowNum + ", colNum=" + colNum + ", seatType=" + seatType
				+ ", tailNum=" + tailNum + "]";
	}
	
	
}
