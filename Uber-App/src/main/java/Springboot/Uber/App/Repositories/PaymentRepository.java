package Springboot.Uber.App.Repositories;

import Springboot.Uber.App.Entities.Payment;
import Springboot.Uber.App.Entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByRide(Ride ride);
}
