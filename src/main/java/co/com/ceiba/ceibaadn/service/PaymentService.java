package co.com.ceiba.ceibaadn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.repository.IParkingRepository;
import co.com.ceiba.ceibaadn.repository.IPaymentRepository;
import co.com.ceiba.ceibaadn.repository.QueryRepository;

@Service
public class PaymentService implements IPaymentService {
	
	private static final double CAR_HOUR = 1000;
	
	private static final double MOTORCYCLE_HOUR = 500;
	
	private static final double CAR_DAY = 8000;
			
	private static final double MOTORCYCLE_DAY = 4000;
	
	private static final double MAX_CYLINDER = 500;
	
	private static final double PRICE_MAX_CYLINDER = 2000;
	
	@Autowired
	private IPaymentRepository paymentRepository;
	
	@Autowired
	private IParkingRepository parkingRepository;
	
	@Autowired
	private QueryRepository queryRepository;
	
	
	
	public void savePayment() {
		
		
	}
	
	
	public void updateParking(String licensePlate) {
		
		Parking parking = queryRepository.findVehicleParking(licensePlate);
		
		if(parking == null) {
			
			
		}else {
			
			
			
		}
	}
	
	public void findParkingVehicle(String licensePlate) {
		
		queryRepository.findVehicleParking(licensePlate);
		
	}

	
	public void calculatePayment() {
		
		
	}
	
	
}
