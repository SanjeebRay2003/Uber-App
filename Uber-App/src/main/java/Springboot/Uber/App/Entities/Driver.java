package Springboot.Uber.App.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_driver_vehicle_id",columnList = "vehicleId")
})
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "User_id")
    private User user;

    private Double rating;

    private String vehicleId;

    private Boolean available;

    @Column(columnDefinition = "Geometry(Point,4326)") // it points the location on earth
    private Point currentLocation;
}
