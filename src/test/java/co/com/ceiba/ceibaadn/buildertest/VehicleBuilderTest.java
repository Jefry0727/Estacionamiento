package co.com.ceiba.ceibaadn.buildertest;

import co.com.ceiba.ceibaadn.model.Vehicle;

public class VehicleBuilderTest {

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
