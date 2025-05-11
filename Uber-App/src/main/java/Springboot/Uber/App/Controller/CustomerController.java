package Springboot.Uber.App.Controller;

import Springboot.Uber.App.DTO.*;
import Springboot.Uber.App.Services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customer")
@RequiredArgsConstructor
@Secured("ROLE_CUSTOMER")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;


    @PostMapping(path = "/requestRide")
    public ResponseEntity<RideRequest_DTO> requestRide(@RequestBody RideRequest_DTO rideRequestDto){
        return ResponseEntity.ok(customerService.requestRide(rideRequestDto));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDTO> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(customerService.cancelRide(rideId));
    }

    @PostMapping("/rateDriver")
    public ResponseEntity<DriverDTO> rateDriver(@RequestBody RatingDTO ratingDTO){
        return ResponseEntity.ok(customerService.rateDriver(ratingDTO.getRideId(),ratingDTO.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<CustomerDTO> getMyProfile(){
        return ResponseEntity.ok(customerService.getMyProfile());
    }

    @GetMapping("/getAllMyRides")
    public ResponseEntity<Page<RideDTO>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                    @RequestParam(defaultValue = "10" ,required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffSet,pageSize,
                Sort.by(Sort.Direction.DESC,"createdTime","id"));
        return ResponseEntity.ok(customerService.getAllMyRides(pageRequest));
    }

//    @PostMapping("/rateDriver/{rideId}/{rating}")
//    public ResponseEntity<DriverDTO> rateDriver(@PathVariable Long rideId,@PathVariable Integer rating){
//        return ResponseEntity.ok(customerService.rateDriver(rideId,rating));
//    }

}
