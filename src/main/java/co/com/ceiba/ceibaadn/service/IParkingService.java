package co.com.ceiba.ceibaadn.service;

import co.com.ceiba.ceibaadn.dto.ParkingDTO;
import co.com.ceiba.ceibaadn.dto.VehicleDTO;
import co.com.ceiba.ceibaadn.exception.ParkingException;
import co.com.ceiba.ceibaadn.model.Vehicle;

public interface IParkingService {
	
	/**
	 * 
	 * <p><b>Guardar el estacionamiento, guarda el vehiculo si no existe </b></p><br/>
	 * <ul><li></li></ul><br/>
	 * @author Jefry Londoño <br/>
	 *		   Email: jefry.londono@ceiba.com.co
	 *		   Feb 7, 2019
	 * @version 1.0
	 * @param object
	 * @return
	 * @throws ParkingException
	 */
	public ParkingDTO saveParkinIn(VehicleDTO object) throws ParkingException;
	
	/**
	 * 
	 * <p><b>Guarda el Estacionamiento tomando los datos de entrada es decir la hora y fecha</b></p><br/>
	 * <ul><li></li></ul><br/>
	 * @author Jefry Londoño <br/>
	 *		   Email: jefry.londono@ceiba.com.co
	 *		   Feb 7, 2019
	 * @version 1.0
	 * @param vehicle
	 * @return
	 */
	public ParkingDTO saveParking(Vehicle vehicle);
	
	
	/**
	 * 
	 * <p><b> Guarda el Vehiculo en la base de datos</b></p><br/>
	 * <ul><li></li></ul><br/>
	 * @author Jefry Londoño <br/>
	 *		   Email: jefry.londono@ceiba.com.co
	 *		   Feb 7, 2019
	 * @version 1.0
	 * @param vehicle
	 * @return
	 */
	public Vehicle saveVehicle(Vehicle vehicle);
	
}
