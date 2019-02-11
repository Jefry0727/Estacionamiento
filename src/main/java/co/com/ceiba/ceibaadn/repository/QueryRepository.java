package co.com.ceiba.ceibaadn.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Repository;

import co.com.ceiba.ceibaadn.model.Parking;

@Repository
public class QueryRepository {

	@PersistenceContext
	public EntityManager entityManager;

	public int quantityVehicleByType(int typeVehicle) {

		Query query = entityManager.createQuery(
				"SELECT count(p.id) FROM Parking p join Vehicle v on v.id = p.vehicle.id where v.vehicleType = "
						+ typeVehicle + " and p.state = 1");

		return Integer.parseInt(query.getSingleResult().toString());

	}

	public Parking findVehicleParking(String licensePlate) {
		
		try {
			
			Query query = entityManager
					.createQuery("SELECT p FROM Parking p JOIN Vehicle v ON v.id = p.vehicle.id WHERE v.licensePlate = '"
							+ licensePlate + "' AND p.state = 1");
				
			return (Parking)query.getSingleResult();
			
		}catch(NoResultException e) {
			
			LogManager.getLogger(this.getClass()).info("Exception: " + e.getMessage());
			
			return null;
		}
		

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Parking> getListParking() {
		
		Query query = entityManager
				.createQuery("SELECT p FROM Parking p JOIN Vehicle v ON v.id = p.vehicle.id WHERE p.state = 1");
		
		return (List<Parking>) query.getResultList();
		
	}
}
