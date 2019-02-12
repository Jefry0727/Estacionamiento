package co.com.ceiba.ceibaadn.util;

import org.modelmapper.ModelMapper;

import co.com.ceiba.ceibaadn.dto.ParkingDTO;
import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Vehicle;

public class Conversor {

	

	/**
	 * 
	 * <p>
	 * <b>Investigar mas sobre el transformar una entidad a un dto
	 * private ModelMapper modelMapper = new ModelMapper(); </b>
	 * </p>
	 * <br/>
	 * <ul>
	 * <li></li>
	 * </ul>
	 * <br/>
	 * 
	 * @author Jefry Londoño <br/>
	 *         Email: jefry.londono@ceiba.com.co Feb 10, 2019
	 * @version 1.0
	 * @param parking
	 * @return
	 */
	public ParkingDTO convertToDto(Parking parking) {

		return new ParkingDTO(parking.getId(), parking.getHourCheckIn(), parking.getHourCheckOut(),
				parking.getDateCheckIn(), parking.getDateCheckOut(), parking.getState(),convertToDtoVehicleDTO(parking.getVehicle()) );
	}
	
	public VehicleDTO convertToDtoVehicleDTO(Vehicle vehicle) {
		
		return new VehicleDTO(vehicle.getId(), vehicle.getLicensePlate(), vehicle.getCylinder(),vehicle.getVehicleType());
	}

}
