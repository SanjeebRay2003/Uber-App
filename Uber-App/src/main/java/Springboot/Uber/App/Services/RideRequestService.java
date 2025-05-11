package Springboot.Uber.App.Services;

import Springboot.Uber.App.Entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById ( Long rideRequestId);

    void update(RideRequest rideRequest);
}
