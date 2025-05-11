package Springboot.Uber.App.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_rating_customer",columnList = "customer_id"),
        @Index(name = "idx_rating_driver",columnList = "driver_id")
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Ride ride;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Driver driver;

    private Integer driverRating; // rating for driver
    private Integer customerRating; // rating for customer

}
