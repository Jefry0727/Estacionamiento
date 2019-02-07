package co.com.ceiba.ceibaadn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.ceibaadn.model.Payment;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Long>{

}
