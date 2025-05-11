package Springboot.Uber.App.Repositories;

import Springboot.Uber.App.Entities.Customer;
import Springboot.Uber.App.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByUser(User user);
}
