package Springboot.Uber.App.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDTO {

    private double[] coordinates;
    private String type ="point";

    public PointDTO(double[] coordinates) {
        this.coordinates=coordinates;
    }
}
