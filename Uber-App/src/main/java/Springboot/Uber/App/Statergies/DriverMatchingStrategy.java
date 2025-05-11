package Springboot.Uber.App.Statergies;

import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDrivers(RideRequest rideRequest);
}
