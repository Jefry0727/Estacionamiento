package co.com.ceiba.ceibaadn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "id_parking" ,nullable = false)
	private Parking parking;
	
	@Column(name = "total_price")
	private double totalPrice;
	
	@Column(name = "time_inside")
	private int timeInside;

	public Payment() {
		super();
		
	}
	
	

	public Payment(Parking parking, double totalPrice, int timeInside) {
		super();
		this.parking = parking;
		this.totalPrice = totalPrice;
		this.timeInside = timeInside;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTimeInside() {
		return timeInside;
	}

	public void setTimeInside(int timeInside) {
		this.timeInside = timeInside;
	}
	
	
	
	

}
