package Springboot.Uber.App.Services.Implementation;

import Springboot.Uber.App.DTO.CustomerDTO;
import Springboot.Uber.App.DTO.DriverDTO;
import Springboot.Uber.App.DTO.RideDTO;
import Springboot.Uber.App.DTO.RideRequest_DTO;
import Springboot.Uber.App.Entities.*;
import Springboot.Uber.App.Entities.Enums.RideRequestStatus;
import Springboot.Uber.App.Entities.Enums.RideStatus;
import Springboot.Uber.App.Exceptions.ResourceNotFoundException;
import Springboot.Uber.App.Repositories.CustomerRepository;
import Springboot.Uber.App.Repositories.RideRequestRepository;
import Springboot.Uber.App.Services.CustomerService;
import Springboot.Uber.App.Services.DriverService;
import Springboot.Uber.App.Services.RatingService;
import Springboot.Uber.App.Services.RideService;
import Springboot.Uber.App.Statergies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor

public class Customer_Service implements CustomerService {


    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;
    private final CustomerRepository customerRepository;
    private final RideStrategyManager rideStrategyManager;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;


    @Override
    @Transactional
    public RideRequest_DTO requestRide(RideRequest_DTO rideRequestDto) {
        Customer customer = getCurrentCustomer();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setCustomer(customer);

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(customer.getRating())
                .findMatchingDrivers(rideRequest);

        // todo : send notification to all drivers about this ride request

        return modelMapper.map(savedRideRequest, RideRequest_DTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        Customer customer = getCurrentCustomer();
        Ride ride = rideService.getRideById(rideId);
        if (!customer.equals(ride.getCustomer())) {
            throw new RuntimeException("customer does not own the ride with id " + rideId);
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride can not be canceled invalid status : " + ride.getRideStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELED);
        driverService.updateDriverAvailability(ride.getDriver(), true);
        return modelMapper.map(savedRide, RideDTO.class);

    }

    @Override
    public RideDTO endRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Customer customer = getCurrentCustomer();

        if (!customer.equals(ride.getCustomer())){
            throw new RuntimeException("Customer is not the owner of this ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status can not be ENDED hence can not be start rating, status : " +ride.getRideStatus());
        }

        return ratingService.rateDriver(ride,rating);
    }

    @Override
    public CustomerDTO getMyProfile() {
        Customer currentCustomer = getCurrentCustomer();
        return modelMapper.map(currentCustomer,CustomerDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        Customer currentCustomer = getCurrentCustomer();
        return rideService.getAllRidesOfCustomer(currentCustomer, pageRequest).map(
                ride -> modelMapper.map(ride, RideDTO.class)
        );
    }

    @Override
    public Customer createNewCustomer(User user) {
        Customer customer = Customer
                .builder()
                .user(user)
                .rating(0.0)
                .build();

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCurrentCustomer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not associated with user with Id " + user.getId()));
    }
}
