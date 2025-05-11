package Springboot.Uber.App.Services.Implementation;

import Springboot.Uber.App.DTO.RideRequest_DTO;
import Springboot.Uber.App.Entities.Customer;
import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.Enums.RideRequestStatus;
import Springboot.Uber.App.Entities.Enums.RideStatus;
import Springboot.Uber.App.Entities.Ride;
import Springboot.Uber.App.Entities.RideRequest;
import Springboot.Uber.App.Exceptions.ResourceNotFoundException;
import Springboot.Uber.App.Repositories.RideRepository;
import Springboot.Uber.App.Services.RideRequestService;
import Springboot.Uber.App.Services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class Ride_Service implements RideService {

    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with Id "+ rideId));
    }
    

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        Ride ride = modelMapper.map(rideRequest,Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOTP(generateOTP());
        ride.setId(null);
        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Ride ride,  RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfCustomer(Customer customer, PageRequest pageRequest) {
return rideRepository.findByCustomer(customer,pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver,pageRequest);

    }

    private String generateOTP(){
        Random random = new Random();
        int OTP = random.nextInt(10000);
        return String.format("%d",OTP);
    }
}
