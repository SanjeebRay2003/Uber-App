package Springboot.Uber.App.Controller;


import Springboot.Uber.App.DTO.*;
import Springboot.Uber.App.Services.DriverService;
import Springboot.Uber.App.Services.Implementation.Driver_Service;
import Springboot.Uber.App.Services.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
@Secured("ROLE_DRIVER")
public class DriverController {

    private final DriverService driverService;


    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDTO> acceptRide (@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping("/startRide/{rideRequestId}")
    public ResponseEntity<RideDTO> startRide (@PathVariable Long rideRequestId,
                                              @RequestBody RideStartDTO rideStartDTO){
        return ResponseEntity.ok(driverService.startRide(rideRequestId,rideStartDTO.getOTP()));
    }

    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<RideDTO> endRide (@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.endRide(rideId));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDTO> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @PostMapping("/rateCustomers")
    public ResponseEntity<CustomerDTO> rateCustomers(@RequestBody RatingDTO ratingDTO){
        return ResponseEntity.ok(driverService.rateCustomer(ratingDTO.getRideId(),ratingDTO.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDTO> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping("/getAllMyRides")
    public ResponseEntity<Page<RideDTO>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                       @RequestParam(defaultValue = "10" ,required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffSet,pageSize,
                Sort.by(Sort.Direction.DESC,"createdTime","id"));
        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest));
    }

//    @PostMapping("/rateCustomer/{rideId}/{rating}")
//    public ResponseEntity<CustomerDTO> rateCustomer(@PathVariable Long rideId,@PathVariable Integer rating){
//        return ResponseEntity.ok(driverService.rateCustomer(rideId,rating));
//    }

}
