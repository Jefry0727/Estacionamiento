package co.com.ceiba.ceibaadn.dto;



import java.io.Serializable;

import co.com.ceiba.ceibaadn.model.Parking;

public class PaymentDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int idDTO;

	private Parking parkingDTO;

	private double priceDTO;

	private int timeInsideDTO;

	public PaymentDTO() {
		
		super();

	}

	public PaymentDTO(int idDTO, Parking parkingDTO, double priceDTO, int timeInsideDTO) {
		super();
		this.idDTO = idDTO;
		this.parkingDTO = parkingDTO;
		this.priceDTO = priceDTO;
		this.timeInsideDTO = timeInsideDTO;
	}

	public int getIdDTO() {
		return idDTO;
	}

	public Parking getParkingDTO() {
		return parkingDTO;
	}

	public double getPriceDTO() {
		return priceDTO;
	}

	public int getTimeInsideDTO() {
		return timeInsideDTO;
	}

	public void setIdDTO(int idDTO) {
		this.idDTO = idDTO;
	}

	public void setParkingDTO(Parking parkingDTO) {
		this.parkingDTO = parkingDTO;
	}

	public void setPriceDTO(double priceDTO) {
		this.priceDTO = priceDTO;
	}

	public void setTimeInsideDTO(int timeInsideDTO) {
		this.timeInsideDTO = timeInsideDTO;
	}


	
	
	
	
}
