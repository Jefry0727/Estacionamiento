package co.com.ceiba.ceibaadn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "license_plate" , unique = true, nullable = false)
	protected String licensePlate;
	
	@Column(name = "cylinder")
	protected String cylinder;
	

	@Column(name = "vehicle_type")
	protected int vehicleType;

	
	
	public Vehicle(String licensePlate, String cylinder, int vehicleType) {
		super();
		this.licensePlate = licensePlate;
		this.cylinder = cylinder;
		this.vehicleType = vehicleType;
	}


	public Vehicle() {
		super();

	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getCylinder() {
		return cylinder;
	}

	public void setCylinder(String cylinder) {
		this.cylinder = cylinder;
	}


	public int getVehicleType() {
		return vehicleType;
	}


	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}


	
	

}
