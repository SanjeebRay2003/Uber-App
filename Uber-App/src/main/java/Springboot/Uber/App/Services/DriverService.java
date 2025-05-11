package Springboot.Uber.App.Services;

import Springboot.Uber.App.DTO.CustomerDTO;
import Springboot.Uber.App.DTO.DriverDTO;
import Springboot.Uber.App.DTO.RideDTO;
import Springboot.Uber.App.Entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface DriverService {

    RideDTO acceptRide(Long rideRequestId);
    RideDTO cancelRide(Long rideId);
    RideDTO startRide(Long rideId,String OTP);
    RideDTO endRide(Long rideId);

    CustomerDTO rateCustomer(Long rideId , Integer rating);

    DriverDTO getMyProfile();

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver updateDriverAvailability(Driver driver, boolean available);

    Driver createNewDriver (Driver driver);



}
