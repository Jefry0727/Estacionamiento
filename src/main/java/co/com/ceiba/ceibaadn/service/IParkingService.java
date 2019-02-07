package co.com.ceiba.ceibaadn.service;

import co.com.ceiba.ceibaadn.dto.ParkingDTO;
import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;

public interface IParkingService {
	
	/**
	 * Jefry Londoño
	 * Guarda el paqueo de un vehiculo en el estacionamiento
	 * @param object
	 * @return
	 * @throws ParkingException
	 */
	public ParkingDTO saveParkinIn(VehicleDTO object) throws ParkingException;
	
}
