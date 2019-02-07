package co.com.ceiba.ceibaadn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
import co.com.ceiba.ceibaadn.service.IParkingService;



@RestController
public class ParkingController {
	
	@Autowired
	private IParkingService iParkingService;


	@PostMapping(value = "/save")
	public ResponseEntity<?> saveParking(@RequestBody VehicleDTO vehicleDTO){
		
		try {
			
			iParkingService.saveParkinIn(vehicleDTO);
			
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (ParkingException e) {
			
			return new ResponseEntity<>("Error",
					HttpStatus.BAD_REQUEST);
			
		}
		
		
	}

}
