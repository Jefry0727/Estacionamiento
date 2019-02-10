package co.com.ceiba.ceibaadn.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.ceibaadn.dto.ParkingDTO;
import co.com.ceiba.ceibaadn.dto.PaymentDTO;
import co.com.ceiba.ceibaadn.dto.RestResponseParkingDTO;
import co.com.ceiba.ceibaadn.dto.RestResponsePaymentDTO;
import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
import co.com.ceiba.ceibaadn.service.IParkingService;
import co.com.ceiba.ceibaadn.service.IPaymentService;



@RestController
public class ParkingController {
	
	@Autowired
	private IParkingService iParkingService;	
	
	@Autowired
	private IPaymentService iPaymentService;
	
	@PostMapping(value = "/saveParking")
	public RestResponseParkingDTO saveParking(@RequestBody VehicleDTO vehicleDTO) throws ParkingException {
		
		ParkingDTO parkingDTO = iParkingService.saveParkinIn(vehicleDTO);
		
		return new RestResponseParkingDTO(HttpStatus.OK.toString(), parkingDTO);
		
	}
	
	@PostMapping(value = "/savePayment")
	public RestResponsePaymentDTO savePayment(@RequestBody VehicleDTO vehicleDTO) throws ParkingException {
		
			PaymentDTO paymentDTO;
			
			paymentDTO = iPaymentService.savePayment(vehicleDTO.getLicenseDTO());
			
			return new RestResponsePaymentDTO(HttpStatus.OK.toString(), paymentDTO);
			
	}
	

}
