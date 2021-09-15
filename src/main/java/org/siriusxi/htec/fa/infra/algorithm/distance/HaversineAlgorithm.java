package org.siriusxi.htec.fa.infra.algorithm.distance;

import static java.lang.Math.*;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

/**
 * <p>
 * The Haversine formula calculates the shortest distance between two points on a sphere
 * using their latitudes and longitudes measured along the surface.
 * </p>
 * <string>Source:</string> <a href='https://en.wikipedia.org/wiki/Haversine_formula'>Wikipedia</a>
 *
 * @author Mohamed Taman
 * @apiNote It is important for use in navigation.
 * @see DistanceAlgorithm
 * @since v0.4
 */
public final class HaversineAlgorithm implements DistanceAlgorithm {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate(Point first, Point second, MeasureType measure) {
        
        requireNonNull(first, "First co-ordinate can't be null.");
        requireNonNull(second, "Second co-ordinate can't be null.");
        measure = requireNonNullElse(measure, MeasureType.KILOMETER);
        
        // distance between latitudes and longitudes
        double dLat = toRadians(second.latitude() - first.latitude());
        double dLon = toRadians(second.longitude() - first.longitude());
        
        // convert to radians
        double latitude1 = toRadians(first.latitude());
        double latitude2 = toRadians(second.latitude());
        
        // apply formulae
        double a = pow(sin(dLat / 2), 2) +
                       pow(sin(dLon / 2), 2) *
                           cos(latitude1) *
                           cos(latitude2);
        
        double distance = 2 * asin(sqrt(a));
        
        return distance * measure.getValue();
    }
    
    /**
     * {@inheritDoc}
     */
    public double calculate(Point first, Point second) {
        return calculate(first, second, null);
    }
}
