package co.com.ceiba.ceibaadn.controller;


import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
@CrossOrigin(origins = { "http://localhost:4200" })
public class ParkingController {
	
	@Autowired
	private IParkingService iParkingService;	
	
	@Autowired
	private IPaymentService iPaymentService;

	public ParkingController(IParkingService iParkingService, IPaymentService iPaymentService) {
		super();
		this.iParkingService = iParkingService;
		this.iPaymentService = iPaymentService;
	}

	@PostMapping(value = "/saveParking")
	public RestResponseParkingDTO saveParking(@RequestBody VehicleDTO vehicleDTO) throws ParkingException {
		
		ParkingDTO parkingDTO = iParkingService.saveParkinIn(vehicleDTO);
		
		
		return new RestResponseParkingDTO(HttpStatus.OK.toString(), parkingDTO);
		
	}
	
	@PostMapping(value = "/savePayment")
	public RestResponsePaymentDTO savePayment(@RequestBody VehicleDTO vehicleDTO) throws ParkingException {
		try {
			PaymentDTO paymentDTO;
			
			
				paymentDTO = iPaymentService.savePayment(vehicleDTO.getLicenseDTO());
			
			
			return new RestResponsePaymentDTO(HttpStatus.OK.toString(), paymentDTO);
			
			} catch (ParseException e) {

				LogManager.getLogger(this.getClass()).info("Exception: " + e.getMessage());
			}
		return null;
			
	}
	
	
	@GetMapping(value = "/listParking")
	public RestResponseParkingDTO getAllParking() {

		List<ParkingDTO> list = iParkingService.listParking();
		
		return new RestResponseParkingDTO(HttpStatus.OK.toString(), list);
		
	}
	

}
