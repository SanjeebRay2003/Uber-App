package Springboot.Uber.App.Statergies;


import Springboot.Uber.App.Statergies.Strategy_Implimentation.DriverMatchingHighestRatedDriverStrategy;
import Springboot.Uber.App.Statergies.Strategy_Implimentation.DriverMatchingNearestDriverStrategy;
import Springboot.Uber.App.Statergies.Strategy_Implimentation.RideFareDefaultFareCalculationStrategy;
import Springboot.Uber.App.Statergies.Strategy_Implimentation.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;
    private final RideFareDefaultFareCalculationStrategy rideFareDefaultFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double rideRating){

        if (rideRating >= 4.8){
            return highestRatedDriverStrategy;
        } else {
            return nearestDriverStrategy;
        }

    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){

        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(21,0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if (isSurgeTime){
            return rideFareSurgePricingFareCalculationStrategy;
        }else {
            return rideFareDefaultFareCalculationStrategy;
        }

    }
}

