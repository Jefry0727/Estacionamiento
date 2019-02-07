package co.com.ceiba.ceibaadn.controller;

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

@RestController
public class IndexController {
	
	@Autowired
	public ParkingService parkingService;
	
	@PostMapping(value = "/vehiculo")
	public ResponseEntity<?> saveVehiculo(@RequestBody VehicleDTO vehicleDTO){
		
		
		
//		parkingService.response();
//		
//		return new ResponseEntity<>("Success", HttpStatus.OK);
		
		try {
			
			parkingService.saveParkinIn(vehicleDTO);
			
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (ParkingException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>("Error",
					HttpStatus.BAD_REQUEST);
		}
		
		
	}

}
