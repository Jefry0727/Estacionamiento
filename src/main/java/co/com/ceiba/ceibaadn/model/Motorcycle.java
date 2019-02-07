package co.com.ceiba.ceibaadn.model;

import javax.persistence.Entity;

@Entity
public class Motorcycle extends Vehicle{


	private static final long serialVersionUID = 1L;

	
	
	public Motorcycle() {
		super();

	}

	public Motorcycle(String licensePlate, String cylinder, int vehicleType) {
		super();
		
		this.licensePlate = licensePlate;
		
		this.cylinder = cylinder;
		
		this.vehicleType = vehicleType;
	
	}
	
	

}
