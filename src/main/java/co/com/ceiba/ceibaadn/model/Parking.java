package co.com.ceiba.ceibaadn.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "parking")
public class Parking implements Serializable{


	private static final long serialVersionUID = 1L;
	
	public static final int MAXCARS = 20;
	public static final int MAXMOTORCYCLES = 10;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true , nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_vehicle")
	private Vehicle vehicle;
	
	@Column(name = "hour_check_in", nullable = false)
	private String hourCheckIn;
	
	@Column(name = "hour_check_out", nullable = true)
	private String hourCheckOut;
	
	@Column(name = "date_check_in", nullable = false)
	private Date dateCheckIn;
	
	@Column(name = "date_check_out", nullable = true)
	private Date dateCheckOut;
	
	@Column(name = "state", nullable = false)
	private int state;

	public Parking() {
		super();
		
	}

	
	public Parking(Vehicle vehicle, String hourCheckIn, String hourCheckOut, Date dateCheckIn, Date dateCheckOut,
			int state) {
		super();
		this.vehicle = vehicle;
		this.hourCheckIn = hourCheckIn;
		this.hourCheckOut = hourCheckOut;
		this.dateCheckIn = dateCheckIn;
		this.dateCheckOut = dateCheckOut;
		this.state = state;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getHourCheckIn() {
		return hourCheckIn;
	}

	public void setHourCheckIn(String hourCheckIn) {
		this.hourCheckIn = hourCheckIn;
	}

	public String getHourCheckOut() {
		return hourCheckOut;
	}

	public void setHourCheckOut(String hourCheckOut) {
		this.hourCheckOut = hourCheckOut;
	}

	public Date getDateCheckIn() {
		return dateCheckIn;
	}

	public void setDateCheckIn(Date dateCheckIn) {
		this.dateCheckIn = dateCheckIn;
	}

	public Date getDateCheckOut() {
		return dateCheckOut;
	}

	public void setDateCheckOut(Date dateCheckOut) {
		this.dateCheckOut = dateCheckOut;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}

	

	
	
}
