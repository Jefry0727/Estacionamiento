package co.com.ceiba.ceibaadn.buildertest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Vehicle;

public class ParkingBuilderTest {

    private int id;

    private Vehicle vehicle;

    private String hourCheckIn;

    private String hourCheckOut;

    private Date dateCheckIn;

    private Date dateCheckOut;

    private int state;

    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-DD");
    
    public static final String HOUR_CHECK_OUT = "13:00:00"; 

    public ParkingBuilderTest() {

        try {

            this.vehicle = VehicleBuilderTest.aVehicle().withId(1).build();
            this.hourCheckIn = "10:00:00";
            this.hourCheckOut = "13:00:00";

            this.dateCheckIn = dt.parse("2019-02-08");
            this.dateCheckOut = dt.parse("2019-02-08");
            this.state = 1;

        } catch (ParseException e) {

            e.printStackTrace();
        }
    }

    public ParkingBuilderTest withVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public ParkingBuilderTest withId(int id) {
        this.id = id;
        return this;
    }

    public ParkingBuilderTest withHourCheckIn(String hourCheckIn) {
        this.hourCheckIn = hourCheckIn;
        return this;
    }

    public ParkingBuilderTest withHourCheckOut(String hourCheckOut) {
        this.hourCheckOut = hourCheckOut;
        return this;
    }

    public Parking build() {
        return new Parking(vehicle, hourCheckIn, hourCheckOut, dateCheckIn, dateCheckOut, state);
    }

    public static ParkingBuilderTest aParking() {
        return new ParkingBuilderTest();
    }
}
