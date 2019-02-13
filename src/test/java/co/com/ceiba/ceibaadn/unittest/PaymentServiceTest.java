package co.com.ceiba.ceibaadn.unittest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.ceibaadn.buildertest.ParkingDataBuilder;
import co.com.ceiba.ceibaadn.buildertest.PaymentDataBuilder;
import co.com.ceiba.ceibaadn.buildertest.VehicleDataBuilder;
import co.com.ceiba.ceibaadn.dto.PaymentDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Vehicle;
import co.com.ceiba.ceibaadn.repository.IParkingRepository;
import co.com.ceiba.ceibaadn.repository.IPaymentRepository;
import co.com.ceiba.ceibaadn.repository.QueryRepository;
import co.com.ceiba.ceibaadn.service.PaymentService;

//import org.powermock.api.mockito.PowerMockito;

@RunWith(SpringRunner.class)
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

	VehicleDataBuilder vehicleBuilder;

	ParkingDataBuilder parkingBuilder;

	SimpleDateFormat dt;

//	final Date date;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		paymentService = mock(PaymentService.class);

		parkingBuilder = mock(ParkingDataBuilder.class);

		vehicleBuilder = mock(VehicleDataBuilder.class);

//		date = spy(new Date());

		paymentService = spy(new PaymentService(paymentRepository, parkingRepository, queryRepository));

		dt = spy(new SimpleDateFormat("yyyy-MM-DD"));
	}

	@Test
	public void saveParkingTest() {
		try {
			// Arrange

			// paymentService = spy(new PaymentService(paymentRepository, parkingRepository,
			// queryRepository));

			Date date = Mockito.mock(Date.class);

			Parking parking = spy(new Parking());

			Vehicle vehicle = spy(new Vehicle());
			vehicle.setVehicleType(1);
			parking.setVehicle(vehicle);

			when(queryRepository.findVehicleParking(vehicleBuilder.LICENSE_PLATE_CAR))
					.thenReturn(ParkingDataBuilder.aParking()
							.withVehicle(VehicleDataBuilder.aVehicle()
									.withLicensePlate(vehicleBuilder.LICENSE_PLATE_CAR).withVehicleType(2).build())
							.build());
			// ******************************+ OJO MUCHAS DUDAS EN EL MOCK PORQUE NO RETORNA
			// EL VALOR DADO
//			when(parking.setDateCheckOut(d)).thenReturn(dt.parse("2019-02-08"));
//			Mockito.doReturn(dt.parse("2019-02-08")).when(date);
			// Mockito.spy
//			when(date).thenReturn(dt.parse("2019-02-08"));
//			PowerMockito.whenNew(Date.class).withAnyArguments().thenReturn(new Date());

//			when(dt.format(new Date())).thenReturn(ParkingDataBuilder.HOUR_CHECK_OUT);
//			Mockito.doReturn(ParkingDataBuilder.HOUR_CHECK_OUT).when(dt).format(date);
			PowerMockito.doCallRealMethod().doReturn(57).when(paymentService)
					.calculateTimeInside(ParkingDataBuilder.aParking().build());
//			 Mockito.doReturn(57).when(paymentService).calculateTimeInside(ParkingDataBuilder.aParking().build());
//			when(paymentService.calculateTimeInside(ParkingDataBuilder.aParking().build())).thenReturn(57);
			

			when(paymentService.calculatePayment(0.0, 57, 0.0, 0.0)).thenReturn(PaymentDataBuilder.PRICE_TEST);

			when(paymentService.updateParking(ParkingDataBuilder.aParking().build(), 0.0, 12))
					.thenReturn(PaymentDataBuilder.aPayment().withId(978678).build());
			// act

			PaymentDTO paymentDTO = paymentService.savePayment(vehicleBuilder.LICENSE_PLATE_CAR);

			// assert

			assertNotNull(paymentDTO);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Test
	public void calculateTimeInsideTest() {
		// Arrange

		try {
			Parking parking = ParkingDataBuilder.aParking().withDateCheckIn(dt.parse(ParkingDataBuilder.DATE_CHECK_IN))
					.withHourCheckIn(ParkingDataBuilder.HOUR_CHECK_IN)
					.withDateCheckOut(dt.parse(ParkingDataBuilder.DATE_CHECK_OUT))
					.withHourCheckOut(ParkingDataBuilder.HOUR_CHECK_OUT).build();
			
			//act
			
			int calculate = paymentService.calculateTimeInside(parking);
			
			// assert
			
			assertEquals(calculate, 2);
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void calculateTimeInsideInvalidateTest() {
		// Arrange

		try {
			Parking parking = ParkingDataBuilder.aParking().withDateCheckIn(dt.parse(ParkingDataBuilder.DATE_CHECK_IN))
					.withHourCheckIn(ParkingDataBuilder.HOUR_CHECK_IN)
					.withDateCheckOut(dt.parse(ParkingDataBuilder.DATE_CHECK_OUT_IVALIDATE))
					.withHourCheckOut(ParkingDataBuilder.HOUR_CHECK_OUT).build();
			
			//act
			
			int calculate = paymentService.calculateTimeInside(parking);
			
			// assert
			
		} catch (ParseException e) {

			assertThatExceptionOfType(ParseException.class);

		}

	}
	

	@Test
	public void calculatePaymentCarTest() {
		
		// Arrange
		double price = 0;
		int timeInside = PaymentDataBuilder.TIME_INSIDE;
		double priceHour = paymentService.CAR_HOUR_PRICE;
		double priceDay = paymentService.CAR_DAY_PRICE;
		
		// act
		
		double validte = paymentService.calculatePayment(price, timeInside, priceHour, priceDay);
		
		// assert
		
		assertEquals((Double)validte, (Double) 24000.0);
	}
	
	

}
