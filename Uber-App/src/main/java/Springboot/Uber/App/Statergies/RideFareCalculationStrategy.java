package Springboot.Uber.App.Statergies;

import Springboot.Uber.App.Entities.RideRequest;


public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10;

    double calculateFare(RideRequest rideRequest);
}
