package com.github.oc12345.utility;

import com.spatial4j.core.distance.DistanceUtils;
import org.springframework.stereotype.Component;

import static com.spatial4j.core.distance.DistanceUtils.EARTH_MEAN_RADIUS_MI;

@Component
public class CoordinateDistanceCalculator {

    public double calculateDistance(double latitudeFrom, double longitudeFrom, double latitudeTo, double longitudeTo) {
        double distanceInRads = DistanceUtils.distHaversineRAD(DistanceUtils.toRadians(latitudeFrom),
                DistanceUtils.toRadians(longitudeFrom),
                DistanceUtils.toRadians(latitudeTo),
                DistanceUtils.toRadians(longitudeTo));
        return DistanceUtils.radians2Dist(distanceInRads, EARTH_MEAN_RADIUS_MI);
    }

}
