package Springboot.Uber.App.Entities;

import Springboot.Uber.App.Entities.Enums.PaymentMethod;
import Springboot.Uber.App.Entities.Enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_ride_customer",columnList = "customer_id"),
        @Index(name = "idx_ride_driver",columnList = "driver_id")
})
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point,4326)") // it points the location on earth
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point,4326)") // it points the location on earth
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private String OTP;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

}
