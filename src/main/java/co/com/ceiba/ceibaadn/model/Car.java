package co.com.ceiba.ceibaadn.model;

import javax.persistence.Entity;

@Entity
public class Car extends Vehicle{

	private static final long serialVersionUID = 1L;

	
	
	public Car() {
		super();

	}



	public Car(String licensePlate, String cylinder, int vehicleType) {
		super();
		
		this.licensePlate = licensePlate;
		
		this.cylinder = cylinder;
		
		this.vehicleType = vehicleType;

	}
	
	

}
