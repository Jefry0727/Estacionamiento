package co.com.ceiba.ceibaadn.buildertest;

import co.com.ceiba.ceibaadn.model.Vehicle;

public class VehicleDataBuilder {

	public static final String LICENSE_PLATE_CAR = "CLC889";
	
	public static final String LICENSE_PLATE_MOTORCYCLE = "HNA88E";
	
	public static final String LICENSE_PLATE_INVALIDATE = "HNA88YY";
	
	public static final String LICENSE_PLATE_SUNDAY_MONDEY = "AHJ343";
	
	public static final int TYPE_MOTORCYCLE = 1;
	
	public static final int TYPE_CAR = 2;
	
	public static final int TYPE_INVALIDATE = 0;
	
	private int id;
	
	private String licensePlate;
	
	private String cylinder;
	
	private int vehicleType;
    
    public VehicleDataBuilder(){
        this.licensePlate = "HNA88E";
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
