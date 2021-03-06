package co.com.ceiba.ceibaadn.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import co.com.ceiba.ceibaadn.util.TransformerDTO;

@Service
public class ParkingService implements IParkingService {

	private static final String PATTERN = "^a|^A";

	private static final String REGEX_TYPE_CAR = "^([A-Z]{3}\\d{3})";

	private static final String REGEX_TYPE_MOTORCYCLE = "^([A-Z]{3}\\d{2}[A-Z]{1})";

	private static final String REGEX_TYPE_MOTORCYCLE_TWO_DIGITS = "^([A-Z]{3}\\d{2})";

	private static final String VEHICLE_NOT_VALIDATE = "El veh�culo ingrasado no es v�lido";

	private static final String NOT_AVAILABILITY_VEHICLE = "No hay disponibilidad de cupo para el Veh�culo";

	private static final String VEHICLE_PARKED = "El veh�culo ya esta en el estacionamiento";

	private static final String VEHICLE_NOT_ALLOWED = "El veh�culo no se puede ingresar al estacionamiento, No es un d�a H�BIL.";

	@Autowired
	private IParkingRepository parkingRepository;

	@Autowired
	private IVehicleRepository vehicleRepository;

	@Autowired
	private QueryRepository queryRepository;

	GregorianCalendar calendar = new GregorianCalendar();

	TransformerDTO conversor = new TransformerDTO();

	public ParkingService(IParkingRepository parkingRepository, IVehicleRepository vehicleRepository,
			QueryRepository queryRepository) {
	
		this.parkingRepository = parkingRepository;
		this.vehicleRepository = vehicleRepository;
		this.queryRepository = queryRepository;
	}

	public ParkingService() {


	}

	@Override
	public ParkingDTO saveParkinIn(VehicleDTO object) {

		Vehicle vehicle = null;

		ParkingDTO parking = null;

		object.setLicenseDTO(object.getLicenseDTO().toUpperCase());

		if (queryRepository.findVehicleParking(object.getLicenseDTO()) != null) {

			throw new ParkingException(VEHICLE_PARKED);

		}

		if (validateLicensePlateAndBusinessDays(object.getLicenseDTO())) {

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
		} else {

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

		return new ParkingDTO(parking.getId(), parking.getHourCheckIn(), parking.getHourCheckOut(),
				parking.getDateCheckIn(), parking.getDateCheckOut(), parking.getState(),
				conversor.convertToDtoVehicleDTO(parking.getVehicle()));

	}

	
	public boolean validateLicensePlateAndBusinessDays(String license) {

		Pattern patron = Pattern.compile(PATTERN);

		Matcher encaja = patron.matcher(license);
		
		boolean validate = true;

		if (encaja.find()) {

			if (validateBusinessDay()) {

				return validate;

			}

			return false;

		}

		return validate;
	}

	
	public boolean validateQuantityVehicle(int typeVehicle) {

		int quantity = queryRepository.quantityVehicleByType(typeVehicle);

		boolean returnValidate = false;

		if (typeVehicle == 1) {

			if (quantity >= Parking.MAXMOTORCYCLES) {

				returnValidate = true;

			}

		} else if (typeVehicle == 2 && quantity >= Parking.MAXCARS) {

			returnValidate = true;

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
	 * @author Jefry Londo�o <br/>
	 *         Email: jefry.londono@ceiba.com.co Feb 7, 2019
	 * @version 1.0
	 * @return
	 */
	public boolean validateBusinessDay() {

		calendar.setTime(new Date());

		boolean validate = false;

		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {

			validate = true;
		}

		return validate;

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
	 * @author Jefry Londo�o <br/>
	 *         Email: jefry.londono@ceiba.com.co Feb 7, 2019
	 * @version 1.0
	 * @param licensePlate
	 * @return
	 * @throws ParkingException
	 */
	public int validateTypeVehicle(String licensePlate) {

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

	@Override
	public List<ParkingDTO> listParking() {

		List<Parking> listParking = queryRepository.getListParking();

		List<ParkingDTO> listParkingDTO = new ArrayList<>();

		for (Parking parking : listParking) {

			listParkingDTO.add(conversor.convertToDto(parking));

		}
		return listParkingDTO;
	}

}
