package Springboot.Uber.App.Services;

import Springboot.Uber.App.DTO.CustomerDTO;
import Springboot.Uber.App.DTO.DriverDTO;
import Springboot.Uber.App.DTO.RideDTO;
import Springboot.Uber.App.DTO.RideRequest_DTO;
import Springboot.Uber.App.Entities.Customer;
import Springboot.Uber.App.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CustomerService {

    RideRequest_DTO requestRide(RideRequest_DTO rideRequestDto);

    RideDTO cancelRide(Long rideId);

    RideDTO endRide(Long rideId);

    DriverDTO rateDriver(Long rideId ,Integer rating);

    CustomerDTO getMyProfile();

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    Customer createNewCustomer(User user);

    Customer getCurrentCustomer();

}
