package co.com.ceiba.ceibaadn.service;


import co.com.ceiba.ceibaadn.dto.PaymentDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;

public interface IPaymentService {
	
	
	public PaymentDTO savePayment(String licensePlate) throws ParkingException;

}
