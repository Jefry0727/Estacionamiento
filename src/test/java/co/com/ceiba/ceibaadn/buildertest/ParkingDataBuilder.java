package co.com.ceiba.ceibaadn.buildertest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;

import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Vehicle;

public class ParkingDataBuilder {

    private int id;

    private Vehicle vehicle;

    private String hourCheckIn;

    private String hourCheckOut;

    private Date dateCheckIn;

    private Date dateCheckOut;

    private int state;

    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
    
    public static final String HOUR_CHECK_OUT = "13:00:00"; 
    
    public static final String HOUR_CHECK_IN = "11:00:00"; 
    
    public static final String DATE_CHECK_IN = "2019-02-08"; 
    
    public static final String DATE_CHECK_OUT = "2019-02-08"; 
    

    public ParkingDataBuilder() {

        try {

            this.vehicle = VehicleDataBuilder.aVehicle().withId(1).build();
            this.hourCheckIn = HOUR_CHECK_IN;
            this.hourCheckOut = HOUR_CHECK_OUT;

            this.dateCheckIn = dt.parse(DATE_CHECK_IN);
            this.dateCheckOut = dt.parse(DATE_CHECK_OUT);
            this.state = 1;

        } catch (ParseException e) {

        	LogManager.getLogger(this.getClass()).info("ERROR: BAD_REQUEST, " + e.getMessage());
        }
    }

    public ParkingDataBuilder withVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public ParkingDataBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public ParkingDataBuilder withHourCheckIn(String hourCheckIn) {
        this.hourCheckIn = hourCheckIn;
        return this;
    }

    public ParkingDataBuilder withHourCheckOut(String hourCheckOut) {
        this.hourCheckOut = hourCheckOut;
        return this;
    }
    
    public ParkingDataBuilder withDateCheckIn(Date dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
        return this;
    }

    public ParkingDataBuilder withDateCheckOut(Date dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
        return this;
    }

    public Parking build() {
        return new Parking(vehicle, hourCheckIn, hourCheckOut, dateCheckIn, dateCheckOut, state);
    }

    public static ParkingDataBuilder aParking() {
        return new ParkingDataBuilder();
    }
}
