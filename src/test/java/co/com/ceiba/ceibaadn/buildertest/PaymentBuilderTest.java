package co.com.ceiba.ceibaadn.buildertest;

import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Payment;

public class PaymentBuilderTest {

	public static final double PRICE_TEST = 3000.0;
	
	private int id;

	private Parking parking;

	private double totalPrice;

	private int timeInside;

	public PaymentBuilderTest() {
		this.parking = ParkingBuilderTest.aParking().withId(1).build();
		this.totalPrice = 12000;
		this.timeInside = 57;
	}

	public PaymentBuilderTest withTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
		return this;
	}

	public PaymentBuilderTest withId(int id) {
		this.id = id;
		return this;
	}

	public PaymentBuilderTest withCylinder(int timeInside) {
		this.timeInside = timeInside;
		return this;
	}

	public Payment build() {
		return new Payment(parking, totalPrice, timeInside);
	}

	public static PaymentBuilderTest aPayment() {
		return new PaymentBuilderTest();
	}
}
