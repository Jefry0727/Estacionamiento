package co.com.ceiba.ceibaadn.service;


import java.text.ParseException;

import co.com.ceiba.ceibaadn.dto.PaymentDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;

public interface IPaymentService {
	
	
	public PaymentDTO savePayment(String licensePlate) throws ParkingException, ParseException;

}
