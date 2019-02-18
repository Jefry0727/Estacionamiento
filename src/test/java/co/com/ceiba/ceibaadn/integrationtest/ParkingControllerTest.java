package co.com.ceiba.ceibaadn.integrationtest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;

import co.com.ceiba.ceibaadn.buildertest.VehicleDataBuilder;
import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
import co.com.ceiba.ceibaadn.repository.IPaymentRepository;
import co.com.ceiba.ceibaadn.repository.QueryRepository;
import co.com.ceiba.ceibaadn.service.ParkingService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class ParkingControllerTest {

	@Autowired
	ParkingService parkingService;

	@Autowired
	private QueryRepository queryRepository;

	@Autowired
	private IPaymentRepository iPaymentRespository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	Environment env;

	private static final int AUX_QUANTITY = 8;

	private static final String URL_SAVE_PARKING = "http://localhost:8080/saveParking";

	private static final String URL_SAVE_PAYMENT = "http://localhost:8080/savePayment";

	private static final String URL_LIST_PARKING = "http://localhost:8080/listParking";

	@Before
	public void setUp() throws Exception {

		for (int i = 0; i < AUX_QUANTITY; i++) {

			VehicleDTO vehicleDTO = new VehicleDTO(0, "TTX4" + i, VehicleDataBuilder.CYLINDER,
					VehicleDataBuilder.TYPE_MOTORCYCLE);
			saveParking(vehicleDTO);
		}
	}

	@Test
	public void saveParkingVehicleValidateLicensePlate() throws Exception {
		// Arrange

		VehicleDTO vehicleDTO = VehicleDataBuilder.aVehicle()
				.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_MOTORCYCLE)
				.withCylinder(VehicleDataBuilder.CYLINDER).withVehicleType(VehicleDataBuilder.TYPE_MOTORCYCLE)
				.buildDTO();

		// act
		MvcResult result = saveParking(vehicleDTO);

		// Assert

		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(queryRepository.findVehicleParking(vehicleDTO.getLicenseDTO()));

	}

	@Test
	public void saveParkingInvalidatePlate() throws Exception {

		// Arrange

		VehicleDTO vehicleDTO = VehicleDataBuilder.aVehicle()
				.withLicensePlate(VehicleDataBuilder.INVALIDATE_LICENSE_PLATE)
				.withCylinder(VehicleDataBuilder.CYLINDER).withVehicleType(VehicleDataBuilder.TYPE_MOTORCYCLE)
				.buildDTO();

		// act
		MvcResult result = saveParking(vehicleDTO);

		// Assert
		
		assertEquals(result.getResolvedException().getMessage(), VehicleDataBuilder.VEHICLE_NOT_VALIDATE);
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(ParkingException.class);

	}

	@Test
	public void saveParkingVehicleParkedTest() throws Exception {
		// Arrange

		VehicleDTO vehicleDTO = VehicleDataBuilder.aVehicle()
				.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_CAR_2)
				.withCylinder(VehicleDataBuilder.CYLINDER).withVehicleType(VehicleDataBuilder.TYPE_CAR)
				.buildDTO();
		
		saveParking(vehicleDTO);

		// act

		MvcResult result = saveParking(vehicleDTO);

		// assert
		assertEquals(result.getResolvedException().getMessage(), VehicleDataBuilder.VEHICLE_PARKED);
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(ParkingException.class);

	}

	@Test
	public void saveParkingDayTest() throws Exception {

		// Arrange

		VehicleDTO vehicleDTO = VehicleDataBuilder.aVehicle()
				.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_BUSINESS_DAY)
				.withCylinder(VehicleDataBuilder.CYLINDER).withVehicleType(VehicleDataBuilder.TYPE_CAR)
				.buildDTO();
		

		// act

		MvcResult result = saveParking(vehicleDTO);
		
//		assertEquals(result.getResolvedException().getMessage(), VehicleDataBuilder.VEHICLE_NOT_ALLOWED);
		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	public void saveParkingInvalidateType() throws Exception {

		// Arrange

		VehicleDTO vehicleDTO = VehicleDataBuilder.aVehicle()
				.withLicensePlate(VehicleDataBuilder.INVALIDATE_LICENSE_PLATE)
				.withCylinder(VehicleDataBuilder.CYLINDER).withVehicleType(VehicleDataBuilder.INVALIDATE_TYPE_VEHICLE)
				.buildDTO();

		// act

		MvcResult result = saveParking(vehicleDTO);
		
		assertEquals(result.getResolvedException().getMessage(), VehicleDataBuilder.VEHICLE_NOT_VALIDATE);
		assertEquals(400, result.getResponse().getStatus());

	}

	@Test
	public void savePaymentTest() throws Exception {

		// Arrange
		VehicleDTO vehicleDTO = VehicleDataBuilder.aVehicle()
				.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_CAR_2)
				.withCylinder(VehicleDataBuilder.CYLINDER).withVehicleType(VehicleDataBuilder.TYPE_CAR)
				.buildDTO();
	

		saveParking(vehicleDTO);

		// act
		MvcResult result = savePayment(vehicleDTO);

		// assert
		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	public void savePaymentVehicleNotFoundTest() throws Exception {

		// Arrange
		VehicleDTO vehicleDTO = VehicleDataBuilder.aVehicle()
				.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_CAR_3)
				.withCylinder(VehicleDataBuilder.CYLINDER).withVehicleType(VehicleDataBuilder.TYPE_CAR)
				.buildDTO();

		// act
		MvcResult result = savePayment(vehicleDTO);
		

		// assert

		assertEquals(result.getResolvedException().getMessage(), VehicleDataBuilder.VEHICLE_NOT_PARKING);
		assertEquals(400, result.getResponse().getStatus());
	
	}

	@Test
	public void savePaymentMaxCylinder() throws Exception {

		// Arrange
		VehicleDTO vehicleDTO = VehicleDataBuilder.aVehicle()
				.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_MOTORCYCLE_MAX_CYLINDER)
				.withCylinder(VehicleDataBuilder.MAX_CYLINDER).withVehicleType(VehicleDataBuilder.TYPE_MOTORCYCLE)
				.buildDTO();

		saveParking(vehicleDTO);

		// act
		MvcResult result = savePayment(vehicleDTO);

		// assert

		assertEquals(200, result.getResponse().getStatus());
		assertEquals((int) (queryRepository.findVehiclePayment(vehicleDTO.getLicenseDTO())).getTotalPrice(), 2500);

	}

	@Test
	public void saveParkingNotAvailabilityVehicle() throws Exception {

		VehicleDTO vehicleDTO = VehicleDataBuilder.aVehicle()
				.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_MOTORCYCLE_3)
				.withCylinder(VehicleDataBuilder.MAX_CYLINDER).withVehicleType(VehicleDataBuilder.TYPE_MOTORCYCLE)
				.buildDTO();


		saveParking(vehicleDTO);

		vehicleDTO = VehicleDataBuilder.aVehicle()
				.withLicensePlate(VehicleDataBuilder.VALIDATE_LICENSE_PLATE_MOTORCYCLE_2)
				.withCylinder(VehicleDataBuilder.MAX_CYLINDER).withVehicleType(VehicleDataBuilder.TYPE_MOTORCYCLE)
				.buildDTO();


		MvcResult result = saveParking(vehicleDTO);

		assertEquals(result.getResolvedException().getMessage(), VehicleDataBuilder.NOT_AVAILABILITY_VEHICLE);
		assertEquals(400, result.getResponse().getStatus());

	}

	@Test
	public void listParkingTest() throws Exception {

		// Arrange

		// act
		MvcResult result = listParking();

		// assert
		assertEquals(200, result.getResponse().getStatus());

	}

	public MvcResult saveParking(VehicleDTO vehicleDTO) throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_SAVE_PARKING)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(new JSONObject(vehicleDTO).toString());
		
		return mockMvc.perform(requestBuilder).andReturn();
		
	}

	public MvcResult savePayment(VehicleDTO vehicleDTO) throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_SAVE_PAYMENT)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(new JSONObject(vehicleDTO).toString());
		
		return mockMvc.perform(requestBuilder).andReturn();
		
	}

	public MvcResult listParking() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL_LIST_PARKING)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(new JSONObject().toString());
		
		return mockMvc.perform(requestBuilder).andReturn();
		
	}

}
