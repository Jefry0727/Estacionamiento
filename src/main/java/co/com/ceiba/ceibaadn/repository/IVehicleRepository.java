package co.com.ceiba.ceibaadn.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import co.com.ceiba.ceibaadn.model.Vehicle;


@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Long>{
	
//	@Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(:lastName)")
//    public List<Person> find(@Param("lastName") String lastName);
	
//	Vehicle findByLicensePlate(String license);
	
	
	@Query("SELECT v FROM Vehicle v WHERE v.licensePlate = ?1")
	Vehicle findVehicleByLicense(String license);
	
	
//	@Query(
//			  value = "SELECT * FROM Users ORDER BY id", 
//			  countQuery = "SELECT count(*) FROM Users", 
//			  nativeQuery = true)
//			Page<User> findAllUsersWithPagination(Pageable pageable);

}
