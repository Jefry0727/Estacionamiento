package co.com.ceiba.ceibaadn.dto;

import java.io.Serializable;
import java.util.Date;

import co.com.ceiba.ceibaadn.model.Vehicle;

public class ParkingDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int idDTO;
	
	private String hourCheckInDTO;
	
	private String hourCheckOutDTO;
	
	private Date dateCheckInDTO;
	
	private Date dateCheckOutDTO;
	
	private int stateDTO;
	
	private Vehicle vehicleDTO;

	public ParkingDTO() {
		
		super();
		
	}
	

	public ParkingDTO(int idDTO, String hourCheckInDTO, String hourCheckOutDTO, Date dateCheckInDTO,
			Date dateCheckOutDTO, int stateDTO, Vehicle vehicleDTO) {
		super();
		this.idDTO = idDTO;
		this.hourCheckInDTO = hourCheckInDTO;
		this.hourCheckOutDTO = hourCheckOutDTO;
		this.dateCheckInDTO = dateCheckInDTO;
		this.dateCheckOutDTO = dateCheckOutDTO;
		this.stateDTO = stateDTO;
		this.vehicleDTO = vehicleDTO;
	}



	public int getIdDTO() {
		return idDTO;
	}

	public String getHourCheckInDTO() {
		return hourCheckInDTO;
	}

	public String getHourCheckOutDTO() {
		return hourCheckOutDTO;
	}

	public Date getDateCheckInDTO() {
		return dateCheckInDTO;
	}

	public Date getDateCheckOutDTO() {
		return dateCheckOutDTO;
	}

	public int getStateDTO() {
		return stateDTO;
	}

	public void setIdDTO(int idDTO) {
		this.idDTO = idDTO;
	}

	public void setHourCheckInDTO(String hourCheckInDTO) {
		this.hourCheckInDTO = hourCheckInDTO;
	}

	public void setHourCheckOutDTO(String hourCheckOutDTO) {
		this.hourCheckOutDTO = hourCheckOutDTO;
	}

	public void setDateCheckInDTO(Date dateCheckInDTO) {
		this.dateCheckInDTO = dateCheckInDTO;
	}

	public void setDateCheckOutDTO(Date dateCheckOutDTO) {
		this.dateCheckOutDTO = dateCheckOutDTO;
	}

	public void setStateDTO(int stateDTO) {
		this.stateDTO = stateDTO;
	}


	public Vehicle getVehicleDTO() {
		return vehicleDTO;
	}


	public void setVehicleDTO(Vehicle vehicleDTO) {
		this.vehicleDTO = vehicleDTO;
	}

	@Override
	public String toString() {
		
		return "ParkinDTO: id "+ idDTO +" hourCheckInDTO "+ hourCheckInDTO;
	}
	
	
	

}
