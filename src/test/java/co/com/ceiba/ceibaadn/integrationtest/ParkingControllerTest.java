package co.com.ceiba.ceibaadn.integrationtest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



import org.json.JSONObject;
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
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.env.Environment;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.json.JSONObject;
//import org.springframework.http.MediaType;

import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
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
	private MockMvc mockMvc;

	@Autowired
	Environment env;

	private static final String VALIDATE_LICENSE_PLATE_MOTORCYCLE = "HNA88E";

	private static final String VALIDATE_LICENSE_PLATE_CAR = "CCL884";

	private static final String INVALIDATE_LICENSE_PLATE = "AC0884";

	private static final String VALIDATE_LICENSE_PLATE_DAY = "ACC884";

	private static final String MAX_CYLINDER = "650";

	private static final String CYLINDER = "650";

	private static final int VEHICLE_MOTORCYLE = 1;

	private static final String URL_SAVE_PARKING = "http://localhost:8080/saveParking";

	@Test
	public void saveParkingVehicleValidateLicensePlate() throws Exception {
		// Arrange

		VehicleDTO vehicleDTO = new VehicleDTO(0, VALIDATE_LICENSE_PLATE_MOTORCYCLE, CYLINDER, VEHICLE_MOTORCYLE);

		// act
		MvcResult result = saveParking(vehicleDTO);

		// Assert

		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(queryRepository.findVehicleParking(vehicleDTO.getLicenseDTO()));

	}

	@Test
	public void saveParkingInvalidatePlate() throws Exception {

		// Arrange

		VehicleDTO vehicleDTO = new VehicleDTO(0,INVALIDATE_LICENSE_PLATE , CYLINDER, VEHICLE_MOTORCYLE);

		// act
		MvcResult result = saveParking(vehicleDTO);

		// Assert

		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(ParkingException.class);


	}
	
	@Test
	public void saveParkingVehicleParkedTest() throws Exception {
		// Arrange
		
		VehicleDTO vehicleDTO = new VehicleDTO(0,VALIDATE_LICENSE_PLATE_CAR , CYLINDER, VEHICLE_MOTORCYLE);
		MvcResult result = saveParking(vehicleDTO);
		
		// act
		
		result = saveParking(vehicleDTO);
		
		// assert
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(ParkingException.class);
		
	}
	
	@Test
	public void saveParkingDayTest() throws Exception {
		
		// Arrange
		
		VehicleDTO vehicleDTO = new VehicleDTO(0,VALIDATE_LICENSE_PLATE_DAY , CYLINDER, VEHICLE_MOTORCYLE);
		
		// act
		
		MvcResult result = saveParking(vehicleDTO);
		
		
//		assertEquals(400, result.getResponse().getStatus());
//		assertThatExceptionOfType(ParkingException.class);
		
		
		
	}

	public MvcResult saveParking(VehicleDTO vehicleDTO) throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_SAVE_PARKING)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(new JSONObject(vehicleDTO).toString());
		return mockMvc.perform(requestBuilder).andReturn();
	}

}
