package co.com.ceiba.ceibaadn.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import co.com.ceiba.ceibaadn.model.Vehicle;


@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Long>{
	

	
	@Query("SELECT v FROM Vehicle v WHERE v.licensePlate = ?1")
	Vehicle findVehicleByLicense(String license);
	
	

}
