package Springboot.Uber.App.Services;

import Springboot.Uber.App.DTO.CustomerDTO;
import Springboot.Uber.App.DTO.DriverDTO;
import Springboot.Uber.App.Entities.Customer;
import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.Ride;

public interface RatingService {
    CustomerDTO rateCustomer(Ride ride, Integer rating);
    DriverDTO rateDriver(Ride ride, Integer rating);
    void createNewRating(Ride ride);
}
