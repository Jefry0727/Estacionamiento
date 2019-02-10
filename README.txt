//	@Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(:lastName)")
//    public List<Person> find(@Param("lastName") String lastName);
	
//	Vehicle findByLicensePlate(String license);



//	@Query(
//			  value = "SELECT * FROM Users ORDER BY id", 
//			  countQuery = "SELECT count(*) FROM Users", 
//			  nativeQuery = true)
//			Page<User> findAllUsersWithPagination(Pageable pageable);