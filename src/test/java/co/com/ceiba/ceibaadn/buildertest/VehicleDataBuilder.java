package co.com.ceiba.ceibaadn.buildertest;

import co.com.ceiba.ceibaadn.model.Vehicle;

public class VehicleDataBuilder {

	public static final String VALIDATE_LICENSE_PLATE_CAR = "CLC889";
	
	public static final String VALIDATE_LICENSE_PLATE_MOTORCYCLE = "HNA88E";
	
	public static final String VALIDATE_LICENSE_PLATE_MOTORCYCLE_2 = "HNE88E";
	
	public static final String VALIDATE_LICENSE_PLATE_MOTORCYCLE_3 = "HGE88E";
	
	public static final String VALIDATE_LICENSE_PLATE_MOTORCYCLE_MAX_CYLINDER = "DDT88A";
	
	public static final String VALIDATE_LICENSE_PLATE_CAR_2 = "CCL884";
	
	public static final String VALIDATE_LICENSE_PLATE_CAR_3 = "CCK884";
	
	public static final String INVALIDATE_LICENSE_PLATE = "HNA88YY";
	
	public static final String VALIDATE_LICENSE_PLATE_BUSINESS_DAY = "AHJ343";
	
	public static final String MAX_CYLINDER = "650";

	public static final String CYLINDER = "150";
	
	public static final int TYPE_MOTORCYCLE = 1;
	
	public static final int TYPE_CAR = 2;
	
	public static final int INVALIDATE_TYPE_VEHICLE = 0;
	
	private int id;
	
	private String licensePlate;
	
	private String cylinder;
	
	private int vehicleType;
    
    public VehicleDataBuilder(){
        this.licensePlate = VALIDATE_LICENSE_PLATE_MOTORCYCLE;
        this.cylinder = "125";
        this.vehicleType = 0;
    }
    
    public VehicleDataBuilder withLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
        return this;
    }
    
    public VehicleDataBuilder withId(int id){
        this.id = id;
        return this;
    }
    
    public VehicleDataBuilder withCylinder(String cylinder){
        this.cylinder = cylinder;
        return this;
    }
    
    public VehicleDataBuilder withVehicleType(int vehicleType){
        this.vehicleType = vehicleType;
        return this;
    }
    
    public Vehicle build(){
        return new Vehicle(licensePlate, cylinder, vehicleType);
    }
    
    public static VehicleDataBuilder aVehicle(){
        return new VehicleDataBuilder();
    }
}
