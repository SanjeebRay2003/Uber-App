package Springboot.Uber.App.Repositories;

import Springboot.Uber.App.Entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest,Long> {
}
