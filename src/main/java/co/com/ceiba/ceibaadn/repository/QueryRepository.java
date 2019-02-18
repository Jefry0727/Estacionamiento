package co.com.ceiba.ceibaadn.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import co.com.ceiba.ceibaadn.model.Parking;
import co.com.ceiba.ceibaadn.model.Payment;

@Repository
public class QueryRepository {

	@PersistenceContext
	public EntityManager entityManager;

	public int quantityVehicleByType(int typeVehicle) {

		Query query = entityManager.createQuery(
				"SELECT count(p.id) FROM Parking p join Vehicle v on v.id = p.vehicle.id where v.vehicleType = :type and p.state = :state");

		query.setParameter("type", typeVehicle);
		query.setParameter("state", 1);

		return Integer.parseInt(query.getSingleResult().toString());

	}

	public Parking findVehicleParking(String licensePlate) {

		Query query = entityManager.createQuery(
				"SELECT p FROM Parking p JOIN Vehicle v ON v.id = p.vehicle.id WHERE v.licensePlate = :license AND p.state = : s");

		query.setParameter("license", licensePlate);
		query.setParameter("s", 1);

		List<?> result = query.getResultList();

		if (!result.isEmpty()) {

			return (Parking) result.get(0);
		}

		return null;

	}

	public Payment findVehiclePayment(String licensePlate) {

		Query query = entityManager.createQuery(
				"SELECT py FROM Parking p JOIN Vehicle v ON v.id = p.vehicle.id join Payment py on py.parking.id = p.id WHERE v.licensePlate = :license AND p.state = :states");
		
		query.setParameter("license", licensePlate);
		query.setParameter("states", 0);
		
		List<?> result = query.getResultList();

		if (!result.isEmpty()) {

			return (Payment) result.get(0);
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	public List<Parking> getListParking() {

		Query query = entityManager
				.createQuery("SELECT p FROM Parking p JOIN Vehicle v ON v.id = p.vehicle.id WHERE p.state = :states");

		query.setParameter("states", 1);
		return (List<Parking>) query.getResultList();

	}
}
