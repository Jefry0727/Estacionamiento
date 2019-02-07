package co.com.ceiba.ceibaadn.service;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.ceibaadn.dto.ParkingDTO;
import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
import co.com.ceiba.ceibaadn.model.Car;
import co.com.ceiba.ceibaadn.model.Motorcycle;
import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Vehicle;
import co.com.ceiba.ceibaadn.repository.IParkingRepository;
import co.com.ceiba.ceibaadn.repository.IVehicleRepository;
import co.com.ceiba.ceibaadn.repository.QueryRepository;

@Service
public class ParkingService implements IParkingService {

	private static final String PATTERN = "^a|^A";

	private static final String REGEX_TYPE_CAR = "^([A-Z]{3}\\d{3})";

	private static final String REGEX_TYPE_MOTORCYCLE = "^([A-Z]{3}\\d{2}[A-Z]{1})";

	private static final String REGEX_TYPE_MOTORCYCLE_TWO_DIGITS = "^([A-Z]{3}\\d{2})";

	private static final String VEHICLE_NOT_VALIDATE = "El vehículo ingrasado no es válido";

	private static final String NOT_AVAILABILITY_VEHICLE = "No hay disponibilidad de cupo para el Vehículo";

	private static final String VEHICLE_PARKED = "El vehículo ya esta en el estacionamiento";

	@Autowired
	private IParkingRepository parkingRepository;

	@Autowired
	private IVehicleRepository vehicleRepository;

	@Autowired
	private QueryRepository queryRepository;

	GregorianCalendar calendar = new GregorianCalendar();

	
	public ParkingService(IParkingRepository parkingRepository, IVehicleRepository vehicleRepository) {
		super();
		this.parkingRepository = parkingRepository;
		this.vehicleRepository = vehicleRepository;
	}

	public ParkingService() {

		super();

	}

	
	@Override
	public ParkingDTO saveParkinIn(VehicleDTO object) throws ParkingException {

		Vehicle vehicle = null;

		ParkingDTO parking = null;
		
		if(queryRepository.findVehicleParking(object.getLicenseDTO()) != null) {
			
			throw new ParkingException(VEHICLE_PARKED);
			
		}

		if (validateLicensePlateByDays(object.getLicenseDTO())) {

			vehicle = vehicleRepository.findVehicleByLicense(object.getLicenseDTO());

			if (vehicle == null) {

				int validateTypeVehicle = validateTypeVehicle(object.getLicenseDTO());

				boolean validateQuantity = validateQuantityVehicle(validateTypeVehicle);


				if (validateTypeVehicle == 0) {

					throw new ParkingException(VEHICLE_NOT_VALIDATE);

				}

				if (validateQuantity) {

					throw new ParkingException(NOT_AVAILABILITY_VEHICLE);
				}

				if (validateTypeVehicle == 1) {

					Vehicle motorcycle = new Motorcycle(object.getLicenseDTO(), object.getCylinderDTO(), validateTypeVehicle);

					vehicle = saveVehicle(motorcycle);

				} else {

					Car car = new Car(object.getLicenseDTO(), object.getCylinderDTO(), validateTypeVehicle);

					vehicle = saveVehicle(car);

				}

			}

			parking = saveParking(vehicle);
		}

		return parking;

	}

	public Vehicle saveVehicle(Vehicle vehicle) {

		vehicleRepository.save(vehicle);

		return vehicle;
	}

	
	public ParkingDTO saveParking(Vehicle vehicle) {

		SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");

		Date dateCheckInt = new Date();

		String checkInt = dt.format(dateCheckInt);

		Parking parking = new Parking(vehicle, checkInt, "", dateCheckInt, null, 1);

		parkingRepository.save(parking);

		ParkingDTO parkingDTO = new ParkingDTO(parking.getId(), parking.getHourCheckIn(), parking.getHourCheckOut(),
				parking.getDateCheckIn(), parking.getDateCheckOut(), parking.getState());

		return parkingDTO;

	}

	
	public boolean validateLicensePlateByDays(String license) {

		Pattern patron = Pattern.compile(PATTERN);

		Matcher encaja = patron.matcher(license);

		if (encaja.find() && validateDay()) {

			return false;

		}

		return true;
	}

	public boolean validateQuantityVehicle(int typeVehicle) {

		int quantity = queryRepository.quantityVehicleByType(typeVehicle);

		boolean returnValidate = false;

		switch (typeVehicle) {
		case 1:

			if (quantity == Parking.MAXMOTORCYCLES) {

				returnValidate = true;

			}

			break;
		case 2:

			if (quantity == Parking.MAXCARS) {

				returnValidate = true;

			}

			break;

		default:

			returnValidate = true;
			break;
		}

		return returnValidate;

	}

	/**
	 * Jefry Londoño
	 * 
	 * @return
	 */
	public boolean validateDay() {

		calendar.setTime(new Date());

		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {

			return false;

		}

		return true;

	}

	/**
	 * Jefry Londoño Valida que tipo de vehiculo se va a estacionar 1 moto 2 carro
	 * 
	 * @param licensePlate
	 * @return
	 * @throws ParkingException
	 */
	public int validateTypeVehicle(String licensePlate) throws ParkingException {

		if (licensePlate.length() >= 5 && licensePlate.length() <= 6) {

			Pattern patronCar = Pattern.compile(REGEX_TYPE_CAR);

			Matcher encajaCar = patronCar.matcher(licensePlate);

			if (encajaCar.find()) {

				return 2;

			}

			Pattern patronMotorcycle = Pattern.compile(REGEX_TYPE_MOTORCYCLE);

			Matcher encajaMotorcycle = patronMotorcycle.matcher(licensePlate);

			Pattern patronMotorcycleTwo = Pattern.compile(REGEX_TYPE_MOTORCYCLE_TWO_DIGITS);

			Matcher encajaMotorcycleTwo = patronMotorcycleTwo.matcher(licensePlate);

			if (encajaMotorcycle.find() || encajaMotorcycleTwo.find()) {

				return 1;

			}

		}

		return 0;

	}

}
