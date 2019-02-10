package co.com.ceiba.ceibaadn.buildertest;

import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Payment;

public class PaymentDataBuilder {

	public static final double PRICE_TEST = 3000.0;
	
	public static final int TIME_INSIDE = 57;

	
	private int id;

	private Parking parking;

	private double totalPrice;

	private int timeInside;

	public PaymentDataBuilder() {
		this.parking = ParkingDataBuilder.aParking().withId(1).build();
		this.totalPrice = 12000;
		this.timeInside = 57;
	}

	public PaymentDataBuilder withTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
		return this;
	}

	public PaymentDataBuilder withId(int id) {
		this.id = id;
		return this;
	}

	public PaymentDataBuilder withCylinder(int timeInside) {
		this.timeInside = timeInside;
		return this;
	}

	public Payment build() {
		return new Payment(parking, totalPrice, timeInside);
	}

	public static PaymentDataBuilder aPayment() {
		return new PaymentDataBuilder();
	}
}
