package Springboot.Uber.App.Services.Implementation;

import Springboot.Uber.App.Entities.RideRequest;
import Springboot.Uber.App.Exceptions.ResourceNotFoundException;
import Springboot.Uber.App.Repositories.RideRequestRepository;
import Springboot.Uber.App.Services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequest_Service implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride request not found with this id " + rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {
        rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ride Request not found with this id " + rideRequest.getId()));
        rideRequestRepository.save(rideRequest);
    }
}
