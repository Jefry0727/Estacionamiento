package co.com.ceiba.ceibaadn.unittest;

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
		try {
			VehicleDTO vehicleDTO = new VehicleDTO(0, "CLC889", "", 0);

			Vehicle vehicle = vehicleBuilder.withLicensePlate("CLC889").withVehicleType(2).build();

			when(queryRepository.findVehicleParking(vehicleDTO.getLicenseDTO())).thenReturn(null);
			when(parkingService.validateLicensePlateAndDays(vehicleDTO.getLicenseDTO())).thenReturn(true);
			when(vehicleRepository.findVehicleByLicense(vehicleDTO.getLicenseDTO())).thenReturn(null);

			when(parkingService.validateTypeVehicle(vehicleDTO.getLicenseDTO())).thenReturn(2);
			Mockito.doReturn(false).when(parkingService).validateQuantityVehicle(vehicleDTO.getTypeVehicleDTO());
			when(parkingService.saveVehicle(vehicleBuilder.build())).thenReturn(vehicle);

			// act
			ParkingDTO parkingDTO = parkingService.saveParkinIn(vehicleDTO);

			// assert

			assertEquals(VehicleDataBuilder.LICENSE_PLATE_CAR, parkingDTO.getVehicleDTO().getLicenseDTO());

		} catch (ParkingException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void sevaParkingMotorcycleNotExistsTest() {

		// Arrange
		try {
			VehicleDTO vehicleDTO = new VehicleDTO(0, "HNA88E", "", 0);

			Vehicle vehicle = vehicleBuilder.withLicensePlate("HNA88E").withVehicleType(1).build();

			when(queryRepository.findVehicleParking(vehicleDTO.getLicenseDTO())).thenReturn(null);
			when(parkingService.validateLicensePlateAndDays(vehicleDTO.getLicenseDTO())).thenReturn(true);
			when(vehicleRepository.findVehicleByLicense(vehicleDTO.getLicenseDTO())).thenReturn(null);

			when(parkingService.validateTypeVehicle(vehicleDTO.getLicenseDTO())).thenReturn(1);
			Mockito.doReturn(false).when(parkingService).validateQuantityVehicle(vehicleDTO.getTypeVehicleDTO());
			when(parkingService.saveVehicle(vehicleBuilder.build())).thenReturn(vehicle);

			// act
			ParkingDTO parkingDTO = parkingService.saveParkinIn(vehicleDTO);

			// assert

			assertEquals(VehicleDataBuilder.LICENSE_PLATE_MOTORCYCLE, parkingDTO.getVehicleDTO().getLicenseDTO());

		} catch (ParkingException e) {

			e.printStackTrace();
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
	public void validateDayTest() {

		// Arrange
		GregorianCalendar calendar = Mockito.mock(GregorianCalendar.class);
		Mockito.when(calendar.get(Calendar.DAY_OF_WEEK)).thenReturn(Calendar.MONDAY);

		// act
		parkingService.setCalendario(calendar);

		boolean valiate = parkingService.validateDay();

		// assert

		assertTrue(valiate);
	}

	@Test
	public void validateTypeVehicleMotorcycleTest() {

		// Arrange

		Vehicle vehicle = vehicleBuilder.withLicensePlate(VehicleDataBuilder.LICENSE_PLATE_MOTORCYCLE).build();

		// act

		int validate = parkingService.validateTypeVehicle(vehicle.getLicensePlate());

		// assert

		Assert.assertEquals(validate, VehicleDataBuilder.TYPE_MOTORCYCLE);

	}

	@Test
	public void validateTypeVehicleInvalidateTest() {

		// Arrange

		Vehicle vehicle = vehicleBuilder.withLicensePlate(VehicleDataBuilder.LICENSE_PLATE_INVALIDATE).build();

		// act

		int validate = parkingService.validateTypeVehicle(vehicle.getLicensePlate());

		// assert

		Assert.assertEquals(validate, VehicleDataBuilder.TYPE_INVALIDATE);

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

		when(queryRepository.quantityVehicleByType(VehicleDataBuilder.TYPE_MOTORCYCLE)).thenReturn(10);

		// act

		boolean validate = parkingService.validateQuantityVehicle(VehicleDataBuilder.TYPE_MOTORCYCLE);

		// assert

		assertTrue(validate);
	}

	@Test
	public void validateLicensePlateAndDaysTest() {

		// Arrange
		Mockito.doReturn(true).when(parkingService).validateDay();

		// act

		boolean validate = parkingService.validateLicensePlateAndDays(VehicleDataBuilder.LICENSE_PLATE_SUNDAY_MONDEY);

		// assert

		assertTrue(validate);

	}

}
