package co.com.ceiba.ceibaadn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.ceibaadn.dto.PaymentDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Payment;
import co.com.ceiba.ceibaadn.repository.IParkingRepository;
import co.com.ceiba.ceibaadn.repository.IPaymentRepository;
import co.com.ceiba.ceibaadn.repository.QueryRepository;
import co.com.ceiba.ceibaadn.util.TransformerDTO;

@Service
public class PaymentService implements IPaymentService {

	public static final double CAR_HOUR_PRICE = 1000;
	
	public static final double CAR_DAY_PRICE = 8000.0;
	
	private static final int MAX_CYLINDER = 500;

	private static final double MOTORCYCLE_HOUR_PRICE = 500.0;

	private static final double MOTORCYCLE_DAY_PRICE = 4000.0;

	private static final double PRICE_MAX_CYLINDER = 2000.0;

	private static final String VEHICLE_NOT_PARKING = "El vehículo no se encuentra estacionado";

	private static final String FORMAT_DATE = "yyyy-MM-dd";

	private static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private IPaymentRepository paymentRepository;

	@Autowired
	private IParkingRepository parkingRepository;

	@Autowired
	private QueryRepository queryRepository;
	
	TransformerDTO conversor = new TransformerDTO();

	public PaymentService(IPaymentRepository paymentRepository, IParkingRepository parkingRepository,
			QueryRepository queryRepository) {

		this.paymentRepository = paymentRepository;
		this.parkingRepository = parkingRepository;
		this.queryRepository = queryRepository;
	}

	@Override
	public PaymentDTO savePayment(String licensePlate) {

		Parking parking = queryRepository.findVehicleParking(licensePlate);

		if (parking == null) {

			throw new ParkingException(VEHICLE_NOT_PARKING);

		} else {

			SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");

			Date dateCheckOut = new Date();

			String checkOut = dt.format(dateCheckOut);

			parking.setDateCheckOut(dateCheckOut);
			parking.setHourCheckOut(checkOut);
			parking.setState(0);

			int timeInside = calculateTimeInside(parking);

			double price;
			
			int vehicleType = parking.getVehicle().getVehicleType();
			
			if(vehicleType == 1 ) {
				
				price = calculatePayment(0, timeInside, MOTORCYCLE_HOUR_PRICE, MOTORCYCLE_DAY_PRICE);

				if (Integer.parseInt(parking.getVehicle().getCylinder()) > MAX_CYLINDER) {

					price += PRICE_MAX_CYLINDER;

				}
				
			}else {
				
				price = calculatePayment(0, timeInside, CAR_HOUR_PRICE, CAR_DAY_PRICE);
				
			}

			Payment payment = updateParking(parking, price, timeInside);

			paymentRepository.save(payment);

			
			return new PaymentDTO(payment.getId(), conversor.convertToDto(payment.getParking()), payment.getTotalPrice(),
					payment.getTimeInside());

		}
	}

	public Payment updateParking(Parking parking, double price, int timeInside) {

		parkingRepository.save(parking);

		return new Payment(parking, price, timeInside);

	}

	public int calculateTimeInside(Parking parking) {

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);

			SimpleDateFormat dateFormatCheckInOut = new SimpleDateFormat(FORMAT_DATE_TIME);

			/*
			 * fecha de entrada
			 */
			Date dateCheckIn;
			
			dateCheckIn = dateFormatCheckInOut
					.parse(dateFormat.format(parking.getDateCheckIn()) + " " + parking.getHourCheckIn());

			/*
			 * fecha de salida
			 */
			Date dateCheckOut = dateFormatCheckInOut
					.parse(dateFormat.format(parking.getDateCheckOut()) + " " + parking.getHourCheckOut());

			int diff = (int) (dateCheckOut.getTime() - dateCheckIn.getTime());
	
		    int minutes = (diff / (1000 * 60)) % 60;
		    
		    int hours = diff / (60 * 60 * 1000);
		    
		    if(minutes > 0) {
		    	
		    	hours++;
		    }

			

			return hours;

		} catch (ParseException e) {

			LogManager.getLogger(this.getClass()).info("ParseException, " + e.getMessage());

		}
		return 0;

	}

	public double calculatePayment(double price, int timeInside, double priceHour, double priceDay) {

		if (timeInside < 9) {

			if (timeInside == 0) {

				price += priceHour;
				

			} else {

				price += timeInside * priceHour;

			}

		}

		if (timeInside >= 9 && timeInside <= 24) {

			price += priceDay;
		}

		if (timeInside > 24) {

			double result = ((double) timeInside / 24.0);

			int reciduo = (int) Math.round((result - (int) result) * 24);

			price += ((int) (result) * priceDay);

			return calculatePayment(price, reciduo, priceHour, priceDay);

		}

		return price;

	}

}
