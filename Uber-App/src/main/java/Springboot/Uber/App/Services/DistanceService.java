package Springboot.Uber.App.Services;

import org.locationtech.jts.geom.Point;

public interface DistanceService {



    double calculateDistance(Point source , Point destination);

}
