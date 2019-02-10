package co.com.ceiba.ceibaadn.integrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.SqlDateTypeAdapter;

import co.com.ceiba.ceibaadn.buildertest.ParkingDataBuilder;
import co.com.ceiba.ceibaadn.buildertest.VehicleDataBuilder;
import co.com.ceiba.ceibaadn.controller.ParkingController;
import co.com.ceiba.ceibaadn.dto.RestResponseParkingDTO;
import co.com.ceiba.ceibaadn.dto.RestResponsePaymentDTO;
import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.repository.QueryRepository;
import co.com.ceiba.ceibaadn.service.IParkingService;
import co.com.ceiba.ceibaadn.service.IPaymentService;
import co.com.ceiba.ceibaadn.service.ParkingService;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ContextConfiguration
public class ParkingControllerTest {

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Mock
	ParkingService parkingService;
	
	@Mock
	QueryRepository queryRepository;

	@LocalServerPort
	private int localServerPort;

	MockMvc mockMvc;

	@Mock
	private IParkingService iParkingService;

	@Mock
	private IPaymentService iPaymentService;

	@InjectMocks
	private ParkingController parkingController;
	
	VehicleDataBuilder vehicleBuilder;

	Gson gson;
	
	VehicleDTO vehicleMotorcycleDTO;
	
	VehicleDTO vehicleCarDTO;
	
	VehicleDTO vehicleMotorcycleMaxCylinderDTO;
	
	VehicleDTO vehicleMotorcycleErrorLicense;

	@Before
	public void setUp() {
		
		mockMvc = MockMvcBuilders.standaloneSetup(parkingController).build();
		
		parkingController = new ParkingController(iParkingService,iPaymentService);
		
		SqlDateTypeAdapter sqlAdapter = new SqlDateTypeAdapter();
		gson = new GsonBuilder().registerTypeAdapter(java.sql.Date.class, sqlAdapter).setDateFormat("yyyy-MM-dd")
				.create();
		
		String motorcycleDTO = "{\r\n" + 
				"\"licenseDTO\" : \"HNA88E\",\r\n" + 
				"\"cylinderDTO\" : \"154\",\r\n" + 
				"\"typeVehicleDTO\" : 1\r\n" + 
				"}";
		
		String carDTO = "{\r\n" + 
				"\"licenseDTO\" : \"CLC349\",\r\n" + 
				"\"cylinderDTO\" : \"\",\r\n" + 
				"\"typeVehicleDTO\" : 2\r\n" + 
				"}";
		String motorcycleMaxCylinderDTO = "{\r\n" + 
				"\"licenseDTO\" : \"TTX49D\",\r\n" + 
				"\"cylinderDTO\" : \"650\",\r\n" + 
				"\"typeVehicleDTO\" : 1\r\n" + 
				"}";
		
		String motorcycleErrorLicense = "{\r\n" + 
				"\"licenseDTO\" : \"TTX49D3\",\r\n" + 
				"\"cylinderDTO\" : \"650\",\r\n" + 
				"\"typeVehicleDTO\" : 1\r\n" + 
				"}";
		
		vehicleMotorcycleDTO = gson.fromJson(motorcycleDTO, VehicleDTO.class);
		
		vehicleCarDTO = gson.fromJson(carDTO, VehicleDTO.class);
		
		vehicleMotorcycleMaxCylinderDTO = gson.fromJson(motorcycleMaxCylinderDTO, VehicleDTO.class);
		
		vehicleMotorcycleErrorLicense = gson.fromJson(motorcycleErrorLicense, VehicleDTO.class);

		
	}
	
	@Test
	public void saveParkingTest() {
		
		try {
			
			URI url = new URI("http://localhost:" + localServerPort + "/saveParking");
			
			RestResponseParkingDTO responseDTO = restTemplate.postForObject(url, vehicleMotorcycleDTO, RestResponseParkingDTO.class);
//			
			assertThat(responseDTO.getMessage(), equalTo(HttpStatus.OK.toString()));
			
		} catch (URISyntaxException e) {

			e.printStackTrace();
		} 
		
	}
	
	@Test
	public void savePaymentTest() {
		
		try {
			
		when(queryRepository.findVehicleParking("")).thenReturn(ParkingDataBuilder.aParking()
				.withVehicle(VehicleDataBuilder.aVehicle()
						.withLicensePlate("HNA88E").withVehicleType(1).build())
				.build());
		
			URI url = new URI("http://localhost:" + localServerPort + "/savePayment");
			
			RestResponsePaymentDTO responseDTO = restTemplate.postForObject(url, vehicleMotorcycleDTO, RestResponsePaymentDTO.class);

			assertThat(responseDTO.getMessage(), equalTo(HttpStatus.OK.toString()));
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void saveParkingErrorLicenseTest() {
		
//		RestResponseParkingDTO responseDTO;
		
		try {
			
			URI url = new URI("http://localhost:" + localServerPort + "/saveParking");
			
			RestResponseParkingDTO responseDTO = restTemplate.postForObject(url, vehicleMotorcycleErrorLicense, RestResponseParkingDTO.class);
//			fail();
			assertNull(responseDTO.getMessage()); 
			
		} catch (URISyntaxException e) {
			
			System.out.println("entre");
			e.printStackTrace();
			
		} 
//		catch (HttpClientErrorException httpError) {
//
//			assertEquals(HttpStatus.OK, httpError.getStatusCode());
//		}
		
	}
	

}
