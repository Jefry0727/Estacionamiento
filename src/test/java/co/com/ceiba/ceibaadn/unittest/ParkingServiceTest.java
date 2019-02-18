package co.com.ceiba.ceibaadn.unittest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import co.com.ceiba.ceibaadn.buildertest.VehicleDataBuilder;
import co.com.ceiba.ceibaadn.dto.ParkingDTO;
import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;

import co.com.ceiba.ceibaadn.model.Vehicle;
import co.com.ceiba.ceibaadn.repository.IParkingRepository;
import co.com.ceiba.ceibaadn.repository.IVehicleRepository;
import co.com.ceiba.ceibaadn.repository.QueryRepository;
import co.com.ceiba.ceibaadn.service.ParkingService;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@ContextConfiguration
public class ParkingServiceTest {

	@Mock
	IParkingRepository parkingRepository;

	@Mock
	IVehicleRepository vehicleRepository;

	@Mock
	QueryRepository queryRepository;

	@InjectMocks
	ParkingService parkingService;

	VehicleDataBuilder vehicleBuilder = new VehicleDataBuilder();

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		parkingService = mock(ParkingService.class);

		parkingService = spy(new ParkingService(parkingRepository, vehicleRepository, queryRepository));
	}

	@Test
	public void sevaParkingCarNotExistsTest() {

		// Arrange
		
			VehicleDTO vehicleDTO = new VehicleDTO(0, VehicleDataBuilder.VALIDATE_LICENSE_PLATE_CAR, "", 0);

			Vehicle vehicle = vehicleBuilder.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_CAR).withVehicleType(2).build();

			when(queryRepository.findVehicleParking(vehicleDTO.getLicenseDTO())).thenReturn(null);
			when(parkingService.validateLicensePlateAndBusinessDays(vehicleDTO.getLicenseDTO())).thenReturn(true);
			when(vehicleRepository.findVehicleByLicense(vehicleDTO.getLicenseDTO())).thenReturn(null);

			when(parkingService.validateTypeVehicle(vehicleDTO.getLicenseDTO())).thenReturn(2);
			Mockito.doReturn(false).when(parkingService).validateQuantityVehicle(vehicleDTO.getTypeVehicleDTO());
			when(parkingService.saveVehicle(vehicleBuilder.build())).thenReturn(vehicle);

			// act
			ParkingDTO parkingDTO = parkingService.saveParkinIn(vehicleDTO);

			// assert

			assertEquals(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_CAR, parkingDTO.getVehicleDTO().getLicenseDTO());

		
	}

	@Test
	public void sevaParkingMotorcycleNotExistsTest() {

		// Arrange
		
			VehicleDTO vehicleDTO = new VehicleDTO(0, VehicleDataBuilder.VALIDATE_LICENSE_PLATE_MOTORCYCLE, "", 0);

			Vehicle vehicle = vehicleBuilder.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_MOTORCYCLE).withVehicleType(1).build();

			when(queryRepository.findVehicleParking(vehicleDTO.getLicenseDTO())).thenReturn(null);
			when(parkingService.validateLicensePlateAndBusinessDays(vehicleDTO.getLicenseDTO())).thenReturn(true);
			when(vehicleRepository.findVehicleByLicense(vehicleDTO.getLicenseDTO())).thenReturn(null);

			when(parkingService.validateTypeVehicle(vehicleDTO.getLicenseDTO())).thenReturn(1);
			Mockito.doReturn(false).when(parkingService).validateQuantityVehicle(vehicleDTO.getTypeVehicleDTO());
			when(parkingService.saveVehicle(vehicleBuilder.build())).thenReturn(vehicle);

			// act
			ParkingDTO parkingDTO = parkingService.saveParkinIn(vehicleDTO);

			// assert

			assertEquals(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_MOTORCYCLE, parkingDTO.getVehicleDTO().getLicenseDTO());

		
	}
	
	
	@Test
	public void sevaParkingInvalidateTypeVehicleTest() {

		// Arrange
		try {
			VehicleDTO vehicleDTO = new VehicleDTO(0, VehicleDataBuilder.VALIDATE_LICENSE_PLATE_CAR, "", 0);

			vehicleBuilder.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_CAR).withVehicleType(VehicleDataBuilder.INVALIDATE_TYPE_VEHICLE).build();

			when(queryRepository.findVehicleParking(vehicleDTO.getLicenseDTO())).thenReturn(null);
			when(parkingService.validateLicensePlateAndBusinessDays(vehicleDTO.getLicenseDTO())).thenReturn(true);
			when(vehicleRepository.findVehicleByLicense(vehicleDTO.getLicenseDTO())).thenReturn(null);

			when(parkingService.validateTypeVehicle(vehicleDTO.getLicenseDTO())).thenReturn(0);
			Mockito.doReturn(false).when(parkingService).validateQuantityVehicle(vehicleDTO.getTypeVehicleDTO());


			// act
			parkingService.saveParkinIn(vehicleDTO);

			
			// assert

		} catch (ParkingException e) {

			assertEquals(e.getMessage(), VehicleDataBuilder.VEHICLE_NOT_VALIDATE);
			assertThatExceptionOfType(ParkingException.class);

		}
	}
	
	@Test
	public void sevaParkingValidateQuantityNotAvailabilityTest() {
	
		// Arrange
		try {
			VehicleDTO vehicleDTO = new VehicleDTO(0, VehicleDataBuilder.VALIDATE_LICENSE_PLATE_CAR, "", 0);

			vehicleBuilder.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_CAR).withVehicleType(VehicleDataBuilder.TYPE_CAR).build();

			when(queryRepository.findVehicleParking(vehicleDTO.getLicenseDTO())).thenReturn(null);
			when(parkingService.validateLicensePlateAndBusinessDays(vehicleDTO.getLicenseDTO())).thenReturn(true);
			when(vehicleRepository.findVehicleByLicense(vehicleDTO.getLicenseDTO())).thenReturn(null);

			when(parkingService.validateTypeVehicle(vehicleDTO.getLicenseDTO())).thenReturn(VehicleDataBuilder.TYPE_CAR);
			Mockito.doReturn(true).when(parkingService).validateQuantityVehicle(vehicleDTO.getTypeVehicleDTO());
//			Mockito.when(parkingService.validateQuantityVehicle(vehicleDTO.getTypeVehicleDTO())).thenReturn(true);
			Mockito.when(queryRepository.quantityVehicleByType(vehicleDTO.getTypeVehicleDTO())).thenReturn(20);
			
			
			// act
			ParkingDTO v = parkingService.saveParkinIn(vehicleDTO);
			System.out.println(v.getHourCheckInDTO());
			
			// assert

		} catch (ParkingException e) {

			assertThatExceptionOfType(ParkingException.class);

		}
	}

	@Test
	public void saveParkingTest() {

		// arrange

		Vehicle vehicle = vehicleBuilder.withId(1).build();

		// act

		ParkingDTO parkingDTO = parkingService.saveParking(vehicle);

		// assert

		assertNotNull(parkingDTO);

	}

	@Test
	public void validateBusinessDayTest() {

		// Arrange
		GregorianCalendar calendar = Mockito.mock(GregorianCalendar.class);
		Mockito.when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(Calendar.MONDAY);

		// act
		parkingService.setCalendario(calendar);

		boolean valiate = parkingService.validateBusinessDay();

		// assert

		assertTrue(valiate);
	}

	@Test
	public void validateTypeVehicleMotorcycleTest() {

		// Arrange

		Vehicle vehicle = vehicleBuilder.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_MOTORCYCLE).build();

		// act

		int validate = parkingService.validateTypeVehicle(vehicle.getLicensePlate());

		// assert

		Assert.assertEquals(validate, VehicleDataBuilder.TYPE_MOTORCYCLE);

	}

	@Test
	public void validateTypeVehicleInvalidateTest() {

		// Arrange

		Vehicle vehicle = vehicleBuilder.withLicensePlate(VehicleDataBuilder.INVALIDATE_LICENSE_PLATE).build();

		// act

		int validate = parkingService.validateTypeVehicle(vehicle.getLicensePlate());

		// assert

		Assert.assertEquals(validate, VehicleDataBuilder.INVALIDATE_TYPE_VEHICLE);

	}

	@Test
	public void validateQuantityVehicleMotorcycleTest() {

		// Arrange

		when(queryRepository.quantityVehicleByType(VehicleDataBuilder.TYPE_MOTORCYCLE)).thenReturn(9);

		// act

		boolean validate = parkingService.validateQuantityVehicle(VehicleDataBuilder.TYPE_MOTORCYCLE);

		// assert

		assertFalse(validate);
	}

	@Test
	public void validateQuantityVehicleMotorcycleMaxTest() {

		// Arrange

		when(queryRepository.quantityVehicleByType(VehicleDataBuilder.TYPE_CAR)).thenReturn(20);

		// act

		boolean validate = parkingService.validateQuantityVehicle(VehicleDataBuilder.TYPE_CAR);

		// assert

		assertTrue(validate);
	}
	
	@Test
	public void validateQuantityVehicleCarMaxTest() {

		// Arrange

		when(queryRepository.quantityVehicleByType(VehicleDataBuilder.TYPE_MOTORCYCLE)).thenReturn(10);

		// act

		boolean validate = parkingService.validateQuantityVehicle(VehicleDataBuilder.TYPE_MOTORCYCLE);

		// assert

		assertTrue(validate);
	}

	@Test
	public void validateLicensePlateAndBusinessDaysTest() {

		// Arrange
		Mockito.doReturn(true).when(parkingService).validateBusinessDay();

		// act

		boolean validate = parkingService.validateLicensePlateAndBusinessDays(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_BUSINESS_DAY);

		// assert

		assertTrue(validate);

	}

}
