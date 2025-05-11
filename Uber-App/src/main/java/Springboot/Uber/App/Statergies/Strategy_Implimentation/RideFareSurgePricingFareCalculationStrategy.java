package Springboot.Uber.App.Statergies.Strategy_Implimentation;

import Springboot.Uber.App.Entities.RideRequest;
import Springboot.Uber.App.Services.DistanceService;
import Springboot.Uber.App.Statergies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Qualifier
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final double SURGE_FACTOR = 2;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService
                .calculateDistance(rideRequest.getPickupLocation(),rideRequest.getDropOffLocation());
        return distance*RIDE_FARE_MULTIPLIER*SURGE_FACTOR;
    }
}
