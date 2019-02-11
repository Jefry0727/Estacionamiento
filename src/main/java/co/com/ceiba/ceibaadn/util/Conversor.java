package co.com.ceiba.ceibaadn.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.ceiba.ceibaadn.dto.ParkingDTO;
import co.com.ceiba.ceibaadn.model.Parking;

public class Conversor {

	private ModelMapper modelMapper = new ModelMapper();

	/**
	 * 
	 * <p>
	 * <b>Investigar mas sobre el transformar una entidad a un dto </b>
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

		ParkingDTO parkingDTO = new ParkingDTO(parking.getId(), parking.getHourCheckIn(), parking.getHourCheckOut(),
				parking.getDateCheckIn(), parking.getDateCheckOut(), parking.getState(), parking.getVehicle());

		return parkingDTO;
	}

}
