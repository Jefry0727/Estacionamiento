package co.com.ceiba.ceibaadn.dto;

import java.io.Serializable;
import java.util.List;

public class RestResponseParkingDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	
	private ParkingDTO parkingDTO;
	
	private List<ParkingDTO> list;
	
	

	public RestResponseParkingDTO() {
		super();

	}

	public RestResponseParkingDTO(String message, ParkingDTO parkingDTO) {
		super();
		this.message = message;
		this.parkingDTO = parkingDTO;
	}
	
	

	public RestResponseParkingDTO(String message, List<ParkingDTO> list) {
		super();
		this.message = message;
		this.list = list;
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
	
	
	
	public List<ParkingDTO> getList() {
		return list;
	}

	public void setList(List<ParkingDTO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		
		return "ResponseParkingDTO: message: "+message+", parkingDTO: "+ parkingDTO.toString();
	}
	
	

}
