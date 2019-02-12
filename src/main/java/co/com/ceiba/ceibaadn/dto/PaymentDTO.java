package co.com.ceiba.ceibaadn.dto;



import java.io.Serializable;

public class PaymentDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int idDTO;

	private ParkingDTO parkingDTO;

	private double priceDTO;

	private int timeInsideDTO;

	public PaymentDTO() {
		
		super();

	}

	public PaymentDTO(int idDTO, ParkingDTO parkingDTO, double priceDTO, int timeInsideDTO) {
		super();
		this.idDTO = idDTO;
		this.parkingDTO = parkingDTO;
		this.priceDTO = priceDTO;
		this.timeInsideDTO = timeInsideDTO;
	}

	public int getIdDTO() {
		return idDTO;
	}

	public ParkingDTO getParkingDTO() {
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

	public void setParkingDTO(ParkingDTO parkingDTO) {
		this.parkingDTO = parkingDTO;
	}

	public void setPriceDTO(double priceDTO) {
		this.priceDTO = priceDTO;
	}

	public void setTimeInsideDTO(int timeInsideDTO) {
		this.timeInsideDTO = timeInsideDTO;
	}


	
	
	
	
}
