package Springboot.Uber.App.Statergies.Strategy_Implimentation;

import Springboot.Uber.App.Entities.RideRequest;
import Springboot.Uber.App.Services.DistanceService;
import Springboot.Uber.App.Statergies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class RideFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {



    private final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService
                .calculateDistance(rideRequest.getPickupLocation(),rideRequest.getDropOffLocation());
    return distance*RIDE_FARE_MULTIPLIER;
    }
}
