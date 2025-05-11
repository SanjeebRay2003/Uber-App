package Springboot.Uber.App.DTO;

import Springboot.Uber.App.Entities.Enums.PaymentMethod;
import Springboot.Uber.App.Entities.Enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest_DTO {

    private Long id;

    private PointDTO pickupLocation;

    private PointDTO dropOffLocation;

    private LocalDateTime requestedTime;

    private CustomerDTO customer;

    private PaymentMethod paymentMethod;

    private Double fare;

    private RideRequestStatus rideRequestStatus;

}
