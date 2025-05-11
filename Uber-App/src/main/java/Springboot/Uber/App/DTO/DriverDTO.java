package Springboot.Uber.App.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {

    private Long id;
    private UserDTO user;
    private Double rating;
    private String vehicleId;
    private Boolean available;


}
