package Springboot.Uber.App.Services.Implementation;

import Springboot.Uber.App.DTO.CustomerDTO;
import Springboot.Uber.App.DTO.DriverDTO;
import Springboot.Uber.App.DTO.RideDTO;
import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.Enums.RideRequestStatus;
import Springboot.Uber.App.Entities.Enums.RideStatus;
import Springboot.Uber.App.Entities.Ride;
import Springboot.Uber.App.Entities.RideRequest;
import Springboot.Uber.App.Entities.User;
import Springboot.Uber.App.Exceptions.ResourceNotFoundException;
import Springboot.Uber.App.Repositories.DriverRepository;
import Springboot.Uber.App.Services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.sql.DriverManager.getDriver;


@Service
@RequiredArgsConstructor
public class Driver_Service implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;



    @Override
    @Transactional
    // Accepting the Ride Request by its request Id
    public RideDTO acceptRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);
        if (!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride request can not be accepted, Status is "+rideRequest.getRideRequestStatus());
        }

        Driver currentDriver = getCurrentDriver();
        if (!currentDriver.getAvailable()){
            throw new RuntimeException("Driver doesn't available, Can not accept the ride");
        }

       Driver savedDriver = updateDriverAvailability(currentDriver,false);

        Ride ride = rideService.createNewRide(rideRequest,savedDriver);

        return modelMapper.map(ride,RideDTO.class);
    }



    @Override
    public RideDTO cancelRide(Long rideId) {
       Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cant start the ride until he accept the ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride can not be canceled invalid status : "+ride.getRideStatus());
        }

        rideService.updateRideStatus(ride,RideStatus.CANCELED);
       updateDriverAvailability(driver,true);
        return modelMapper.map(ride,RideDTO.class);

    }



    @Override
    // Start the ride after accepting the ride after checking every thing
    public RideDTO startRide(Long rideId,String OTP) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cant start the ride until he accept the ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride status can not be CONFIRMED hence can not be started, status : " +ride.getRideStatus());
        }

        if (!OTP.equals(ride.getOTP())){
            throw new ResourceNotFoundException("OTP is not valid please check your OTP , OTP : "+OTP);
        }

        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride,RideStatus.ONGOING);

        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);


        return modelMapper.map(savedRide,RideDTO.class);
    }



    @Override
    @Transactional
    public RideDTO endRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cant start the ride until he accept the ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride status can not be ONGOING hence can not be ended, status : " +ride.getRideStatus());
        }

        ride.setEndedAt(LocalDateTime.now());
       Ride savedRide =  rideService.updateRideStatus(ride,RideStatus.ENDED);
        updateDriverAvailability(driver,true);
        paymentService.processPayment(ride);

        return modelMapper.map(savedRide,RideDTO.class);

    }

    @Override
    public CustomerDTO rateCustomer(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver is not the owner of this ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status can not be ENDED hence can not be start rating, status : " +ride.getRideStatus());
        }

       return ratingService.rateCustomer(ride,rating);

    }

    @Override
    public DriverDTO getMyProfile() {
       Driver currentDriver = getCurrentDriver();
       return modelMapper.map(currentDriver,DriverDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver, pageRequest).map(
                ride -> modelMapper.map(ride,RideDTO.class)
        );
    }

    @Override
    public Driver getCurrentDriver() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return driverRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not associated with user with id " + user.getId()));

    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        driverRepository.save(driver);
        return driver;
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
