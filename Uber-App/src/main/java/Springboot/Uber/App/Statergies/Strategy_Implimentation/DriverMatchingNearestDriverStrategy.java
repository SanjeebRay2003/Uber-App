package Springboot.Uber.App.Statergies.Strategy_Implimentation;

import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.RideRequest;
import Springboot.Uber.App.Repositories.DriverRepository;
import Springboot.Uber.App.Statergies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDrivers(RideRequest rideRequest) {
        return driverRepository.findTenNearestDrivers(rideRequest.getPickupLocation());
    }
}

