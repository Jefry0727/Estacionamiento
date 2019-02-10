package co.com.ceiba.ceibaadn.dto;

import java.io.Serializable;

public class RestResponseParkingDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	
	private ParkingDTO parkingDTO;
	
	

	public RestResponseParkingDTO() {
		super();

	}

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
	
	@Override
	public String toString() {
		
		return "ResponseParkingDTO: message: "+message+", parkingDTO: "+ parkingDTO.toString();
	}
	
	

}
