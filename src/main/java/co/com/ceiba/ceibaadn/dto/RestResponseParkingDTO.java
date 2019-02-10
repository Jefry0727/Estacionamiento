package co.com.ceiba.ceibaadn.dto;

public class RestResponseParkingDTO {
	
	private String message;
	
	private ParkingDTO parkingDTO;

	public RestResponseParkingDTO(String message, ParkingDTO parkingDTO) {
		super();
		this.message = message;
		this.parkingDTO = parkingDTO;
	}

	public String getMessage() {
		return message;
	}

	public ParkingDTO getParkingDTO() {
		return parkingDTO;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setParkingDTO(ParkingDTO parkingDTO) {
		this.parkingDTO = parkingDTO;
	} 
	
	

}
