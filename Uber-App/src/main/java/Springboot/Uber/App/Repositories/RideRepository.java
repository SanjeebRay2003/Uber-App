package Springboot.Uber.App.Repositories;

import Springboot.Uber.App.Entities.Customer;
import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RideRepository extends JpaRepository<Ride,Long> {
    Page<Ride> findByCustomer(Customer customer, Pageable pageRequest);

    Page<Ride> findByDriver(Driver driver, Pageable pageRequest);
}
