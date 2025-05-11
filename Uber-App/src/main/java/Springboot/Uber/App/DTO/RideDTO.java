package Springboot.Uber.App.DTO;

import Springboot.Uber.App.Entities.Enums.PaymentMethod;
import Springboot.Uber.App.Entities.Enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
public class RideDTO {

    private Long id;

    private PointDTO pickupLocation;

    private PointDTO dropOffLocation;

    private LocalDateTime createdTime;

    private CustomerDTO customer;

    private DriverDTO driver;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;
    private String OTP;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

}
