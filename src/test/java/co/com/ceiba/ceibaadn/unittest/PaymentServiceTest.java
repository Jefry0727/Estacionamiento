package co.com.ceiba.ceibaadn.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.com.ceiba.ceibaadn.buildertest.ParkingBuilderTest;
import co.com.ceiba.ceibaadn.buildertest.PaymentBuilderTest;
import co.com.ceiba.ceibaadn.buildertest.VehicleBuilderTest;
import co.com.ceiba.ceibaadn.dto.PaymentDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Vehicle;
import co.com.ceiba.ceibaadn.repository.IParkingRepository;
import co.com.ceiba.ceibaadn.repository.IPaymentRepository;
import co.com.ceiba.ceibaadn.repository.QueryRepository;
import co.com.ceiba.ceibaadn.service.PaymentService;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@ContextConfiguration
public class PaymentServiceTest {

	@Mock
	IParkingRepository parkingRepository;

	@Mock
	IPaymentRepository paymentRepository;

	@Mock
	QueryRepository queryRepository;

	@InjectMocks
	PaymentService paymentService;

	VehicleBuilderTest vehicleBuilder;
	
	ParkingBuilderTest parkingBuilder;
	
	SimpleDateFormat dt;
	
	Date date;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		paymentService = mock(PaymentService.class);
		
		parkingBuilder = mock(ParkingBuilderTest.class);

		vehicleBuilder = mock(VehicleBuilderTest.class);
		
		date = spy(new Date());

		paymentService = spy(new PaymentService(paymentRepository, parkingRepository, queryRepository));
		
		dt = spy( new SimpleDateFormat("yyyy-MM-DD"));
	}

	@Test
	public void updateParkingTest() {
		try {
			// Arrange
			
			Parking parking = spy(new Parking());
			
			Vehicle vehicle = spy(new Vehicle());
			vehicle.setVehicleType(1);
			parking.setVehicle(vehicle);


			
			when(queryRepository.findVehicleParking(vehicleBuilder.LICENSE_PLATE_CAR)).thenReturn(ParkingBuilderTest
					.aParking()
					.withVehicle(
							VehicleBuilderTest.aVehicle().withLicensePlate(vehicleBuilder.LICENSE_PLATE_CAR).build())
					.build());
			
//			when(parking.setDateCheckOut(d)).thenReturn(dt.parse("2019-02-08"));
//			Mockito.doReturn(dt.parse("2019-02-08")).when(date); Mockito.spy
//			when(Mockito.spy(Date.class)).thenReturn(null);

//			when(dt.format(new Date())).thenReturn(ParkingBuilderTest.HOUR_CHECK_OUT);
//			Mockito.doReturn(ParkingBuilderTest.HOUR_CHECK_OUT).when(dt).format(date);
			Mockito.doReturn(57).when(paymentService).calculateTimeInside(ParkingBuilderTest.aParking().build());
			
			when((Integer)parking.getVehicle().getVehicleType()).thenReturn(2);
			
			when(paymentService.calculatePayment(0.0,57,0.0,0.0)).thenReturn(PaymentBuilderTest.PRICE_TEST);
			
			when(paymentService.updateParking(ParkingBuilderTest.aParking().build(), 0.0, 12))
					.thenReturn(PaymentBuilderTest.aPayment().withId(1).build());
			// act
			
			PaymentDTO paymentDTO = paymentService.savePayment(vehicleBuilder.LICENSE_PLATE_CAR);
			
			// assert
			
			assertNotNull(paymentDTO);
			

		} catch (ParseException | ParkingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
