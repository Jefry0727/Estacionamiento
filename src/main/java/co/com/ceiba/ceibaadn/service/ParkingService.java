package co.com.ceiba.ceibaadn.service;

import java.lang.annotation.Documented;
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

	private static final String VEHICLE_NOT_ALLOWED = "El vehículo no se puede ingresar al estacionamiento";

	@Autowired
	private IParkingRepository parkingRepository;

	@Autowired
	private IVehicleRepository vehicleRepository;

	@Autowired
	private QueryRepository queryRepository;

	GregorianCalendar calendar = new GregorianCalendar();

	public ParkingService(IParkingRepository parkingRepository, IVehicleRepository vehicleRepository,
			QueryRepository queryRepository) {
		super();
		this.parkingRepository = parkingRepository;
		this.vehicleRepository = vehicleRepository;
		this.queryRepository = queryRepository;
	}

	public ParkingService() {

		super();

	}

	@Override
	public ParkingDTO saveParkinIn(VehicleDTO object) throws ParkingException {

		Vehicle vehicle = null;

		ParkingDTO parking = null;

		if (queryRepository.findVehicleParking(object.getLicenseDTO()) != null) {

			throw new ParkingException(VEHICLE_PARKED);

		}

		if (validateLicensePlateAndDays(object.getLicenseDTO())) {

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

					Vehicle motorcycle = new Motorcycle(object.getLicenseDTO(), object.getCylinderDTO(),
							validateTypeVehicle);

					vehicle = saveVehicle(motorcycle);

				} else {

					Car car = new Car(object.getLicenseDTO(), object.getCylinderDTO(), validateTypeVehicle);

					vehicle = saveVehicle(car);

				}

			}

			parking = saveParking(vehicle);
		}else {
			
			throw new ParkingException(VEHICLE_NOT_ALLOWED);
			
		}

		return parking;

	}

	@Override
	public Vehicle saveVehicle(Vehicle vehicle) {

		vehicleRepository.save(vehicle);

		return vehicle;
	}

	@Override
	public ParkingDTO saveParking(Vehicle vehicle) {

		SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");

		Date dateCheckInt = new Date();

		String checkInt = dt.format(dateCheckInt);

		Parking parking = new Parking(vehicle, checkInt, "", dateCheckInt, null, 1);

		parkingRepository.save(parking);

		ParkingDTO parkingDTO = new ParkingDTO(parking.getId(), parking.getHourCheckIn(), parking.getHourCheckOut(),
				parking.getDateCheckIn(), parking.getDateCheckOut(), parking.getState(), parking.getVehicle());

		return parkingDTO;

	}

	/**
	 * 
	 * <p>
	 * <b> Valida si la placa inicia en la letra A, solo se pueda registra los lunes
	 * y domingos</b>
	 * </p>
	 * <br/>
	 * <ul>
	 * <li></li>
	 * </ul>
	 * <br/>
	 * 
	 * @author Jefry Londoño <br/>
	 *         Email: jefry.londono@ceiba.com.co Feb 7, 2019
	 * @version 1.0
	 * @param license
	 * @return
	 */
	public boolean validateLicensePlateAndDays(String license) {

		Pattern patron = Pattern.compile(PATTERN);

		Matcher encaja = patron.matcher(license);

		if (encaja.find()) {
			
			if(validateDay()) {
				
				return true;
				
			}else {
				
				return false;
				
			}


		}

		return true;
	}

	/**
	 * 
	 * <p>
	 * <b>Valida que la cantidad de vehiculos tanto carro como moto no se pase del
	 * tope </b>
	 * </p>
	 * <br/>
	 * <ul>
	 * <li></li>
	 * </ul>
	 * <br/>
	 * 
	 * @author Jefry Londoño <br/>
	 *         Email: jefry.londono@ceiba.com.co Feb 7, 2019
	 * @version 1.0
	 * @param typeVehicle
	 * @return
	 */
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
	 * 
	 * <p>
	 * <b> Valida que el dia actual sea un lunes o un domingo</b>
	 * </p>
	 * <br/>
	 * <ul>
	 * <li></li>
	 * </ul>
	 * <br/>
	 * 
	 * @author Jefry Londoño <br/>
	 *         Email: jefry.londono@ceiba.com.co Feb 7, 2019
	 * @version 1.0
	 * @return
	 */
	public boolean validateDay() {

		calendar.setTime(new Date());

		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {

			return true;

		}

		return false;

	}
	
	public void setCalendario(GregorianCalendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * 
	 * <p>
	 * <b>Valida que tipo de vehiculo es el que se va a ingresar teniendo en cuanta
	 * que: </b>
	 * </p>
	 * <br/>
	 * <ul>
	 * <li>si es un carro las placas seran compuestas por tres letras seguida de
	 * tres digitos</li>
	 * <li>si es una moto las placas seran compuestas por tres letras seguida de dos
	 * digitos y una letra o solo de tres letras y dos digitos</li>
	 * </ul>
	 * <br/>
	 * 
	 * @author Jefry Londoño <br/>
	 *         Email: jefry.londono@ceiba.com.co Feb 7, 2019
	 * @version 1.0
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
