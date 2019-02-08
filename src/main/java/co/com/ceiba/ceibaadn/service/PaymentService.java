package co.com.ceiba.ceibaadn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.ceibaadn.dto.PaymentDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Payment;
import co.com.ceiba.ceibaadn.repository.IParkingRepository;
import co.com.ceiba.ceibaadn.repository.IPaymentRepository;
import co.com.ceiba.ceibaadn.repository.QueryRepository;

@Service
public class PaymentService implements IPaymentService {

	private static final double CAR_HOUR_PRICE = 1000;

	private static final double MOTORCYCLE_HOUR_PRICE = 500.0;

	private static final double CAR_DAY_PRICE = 8000.0;

	private static final double MOTORCYCLE_DAY_PRICE = 4000.0;

	private static final int MAX_CYLINDER = 500;

	private static final double PRICE_MAX_CYLINDER = 2000.0;

	private static final String VEHICLE_NOT_PARKING = "El vehículo no se encuentra estacionado";

	private static final String FORMAT_DATE = "YYYY-MM-DD";

	private static final String FORMAT_DATE_TIME = "YYYY-MM-DD HH:mm:ss";

	@Autowired
	private IPaymentRepository paymentRepository;

	@Autowired
	private IParkingRepository parkingRepository;

	@Autowired
	private QueryRepository queryRepository;

	public PaymentService(IPaymentRepository paymentRepository, IParkingRepository parkingRepository,
			QueryRepository queryRepository) {
		super();
		this.paymentRepository = paymentRepository;
		this.parkingRepository = parkingRepository;
		this.queryRepository = queryRepository;
	}

	public PaymentDTO savePayment(String licensePlate) throws ParkingException, ParseException {

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

			double price = 0.0;

			switch (parking.getVehicle().getVehicleType()) {
			case 1:

				price = calculatePayment(0, timeInside, MOTORCYCLE_HOUR_PRICE, MOTORCYCLE_DAY_PRICE);

				if (Integer.parseInt(parking.getVehicle().getCylinder()) > MAX_CYLINDER) {

					price += PRICE_MAX_CYLINDER;

				}

				break;

			case 2:

				price = calculatePayment(0, timeInside, CAR_HOUR_PRICE, CAR_DAY_PRICE);

				break;
			}

			Payment payment = updateParking(parking, price, timeInside);

			paymentRepository.save(payment);

			PaymentDTO paymentDTO = new PaymentDTO(payment.getId(), payment.getParking(), payment.getTotalPrice(),
					payment.getTimeInside());
			
			return paymentDTO;

		}
	}
	
	
	public Payment updateParking(Parking parking, double price, int timeInside) {
		
		parkingRepository.save(parking);

		return new Payment(parking, price, timeInside);
		
		
	}

	public int calculateTimeInside(Parking parking) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);

		SimpleDateFormat dateFormatCheckInOut = new SimpleDateFormat(FORMAT_DATE_TIME);

		/*
		 * fecha de entrada
		 */
		Date dateCheckIn = dateFormatCheckInOut
				.parse(dateFormat.format(parking.getDateCheckIn()) + " " + parking.getHourCheckIn());

		/*
		 * fecha de salida
		 */
		Date dateCheckOut = dateFormatCheckInOut
				.parse(dateFormat.format(parking.getDateCheckOut()) + " " + parking.getHourCheckOut());

		int diff = (int) ((dateCheckOut.getTime() - dateCheckIn.getTime()));

		int hours = 0;

		hours = (int) (diff / (60 * 60 * 1000));

		return hours;

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
