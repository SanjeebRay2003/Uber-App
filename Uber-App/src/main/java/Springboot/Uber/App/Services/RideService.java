package Springboot.Uber.App.Services;

import Springboot.Uber.App.DTO.RideRequest_DTO;
import Springboot.Uber.App.Entities.Customer;
import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.Enums.RideStatus;
import Springboot.Uber.App.Entities.Ride;
import Springboot.Uber.App.Entities.RideRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

     Ride getRideById(Long rideId);

//    void matchWithDrivers(RideRequest_DTO rideRequestDto);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfCustomer(Customer customer, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);


}
