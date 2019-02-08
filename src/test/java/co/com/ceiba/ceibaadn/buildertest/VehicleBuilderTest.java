package co.com.ceiba.ceibaadn.buildertest;

import co.com.ceiba.ceibaadn.model.Vehicle;

public class VehicleBuilderTest {

	public static final String LICENSE_PLATE_CAR = "CLC889";
	
	public static final String LICENSE_PLATE_MOTORCYCLE = "HNA88E";
	
	public static final String LICENSE_PLATE_INVALIDATE = "HNA88YY";
	
	public static final int TYPE_MOTORCYCLE = 1;
	
	public static final int TYPE_INVALIDATE = 0;
	
	private int id;
	
	private String licensePlate;
	
	private String cylinder;
	
	private int vehicleType;
    
    public VehicleBuilderTest(){
        this.licensePlate = "HNA88E";
        this.cylinder = "125";
        this.vehicleType = 0;
    }
    
    public VehicleBuilderTest withLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
        return this;
    }
    
    public VehicleBuilderTest withId(int id){
        this.id = id;
        return this;
    }
    
    public VehicleBuilderTest withCylinder(String cylinder){
        this.cylinder = cylinder;
        return this;
    }
    
    public VehicleBuilderTest withVehicleType(int vehicleType){
        this.vehicleType = vehicleType;
        return this;
    }
    
    public Vehicle build(){
        return new Vehicle(licensePlate, cylinder, vehicleType);
    }
    
    public static VehicleBuilderTest aVehicle(){
        return new VehicleBuilderTest();
    }
}
