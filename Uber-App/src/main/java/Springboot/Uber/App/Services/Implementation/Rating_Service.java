package Springboot.Uber.App.Services.Implementation;

import Springboot.Uber.App.DTO.CustomerDTO;
import Springboot.Uber.App.DTO.DriverDTO;
import Springboot.Uber.App.Entities.Customer;
import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.Rating;
import Springboot.Uber.App.Entities.Ride;
import Springboot.Uber.App.Exceptions.ResourceNotFoundException;
import Springboot.Uber.App.Repositories.CustomerRepository;
import Springboot.Uber.App.Repositories.DriverRepository;
import Springboot.Uber.App.Repositories.RatingRepository;
import Springboot.Uber.App.Services.CustomerService;
import Springboot.Uber.App.Services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Rating_Service implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public CustomerDTO rateCustomer(Ride ride, Integer rating) {

        Customer customer = ride.getCustomer();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id "+ride.getId()));
        ratingObj.setCustomerRating(rating);
        if (ratingObj.getCustomerRating() != null){
            throw new RuntimeException("Customer is already rated");
        }

        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByCustomer(customer)
                .stream()
                .mapToDouble(rating1 -> rating1.getCustomerRating())
                .average()
                .orElse(0.0);

        customer.setRating(newRating);
       Customer savedCustomer =  customerRepository.save(customer);
       return modelMapper.map(savedCustomer,CustomerDTO.class);

    }

    @Override
    public DriverDTO rateDriver(Ride ride, Integer rating) {
        Driver driver = ride.getDriver();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id "+ride.getId()));
        ratingObj.setDriverRating(rating);

        if (ratingObj.getDriverRating() != null){
            throw new RuntimeException("Driver is already rated");
        }

        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(rating1 -> rating1.getDriverRating())
                .average()
                .orElse(0.0);

        driver.setRating(newRating);
      Driver savedDriver =  driverRepository.save(driver);
      return modelMapper.map(savedDriver,DriverDTO.class);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .driver(ride.getDriver())
                .customer(ride.getCustomer())
                .ride(ride)
                .build();
        ratingRepository.save(rating);
    }
}
