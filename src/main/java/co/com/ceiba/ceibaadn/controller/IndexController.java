package co.com.ceiba.ceibaadn.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.ceibaadn.dto.ParkingDTO;
import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
import co.com.ceiba.ceibaadn.service.ParkingService;
import co.com.ceiba.ceibaadn.service.PaymentService;

@RestController
public class IndexController {
	
	@Autowired
	public PaymentService paymentService;
	
	@PostMapping(value = "/vehiculo")
	public ResponseEntity<?> savePayment(@RequestBody VehicleDTO vehicleDTO){
		
		try {
			
			paymentService.updateParking(vehicleDTO.getLicenseDTO());
			
			return new ResponseEntity<>("Success", HttpStatus.OK);
			
		} catch (ParkingException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>("Error",
					HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>("Error",
					HttpStatus.BAD_REQUEST);
		}
		
		
	}

}
