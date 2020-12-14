package org.siriusxi.htec.fa.infra.calc.distance.impl;

import org.siriusxi.htec.fa.infra.calc.distance.DistanceAlgorithm;
import org.siriusxi.htec.fa.infra.calc.distance.MeasureType;
import org.siriusxi.htec.fa.infra.calc.distance.Point;

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

/**
 * <p>
 * The Haversine formula calculates the shortest distance between two points on a sphere
 * using their latitudes and longitudes measured along the surface.
 * </p>
 * <string>Source:</string> <a href='https://en.wikipedia.org/wiki/Haversine_formula'>Wikipedia</a>
 *
 * @apiNote It is important for use in navigation.
 *
 * @see org.siriusxi.htec.fa.infra.calc.distance.DistanceAlgorithm
 *
 * @author Mohamed Taman
 * @since v0.4
 */
public class HaversineAlgorithm implements DistanceAlgorithm {
    
    /**
     * {@inheritDoc}
     *
     * @param first point in latitude and longitude.
     * @param second point in latitude and longitude.
     * @param measure the result of the calculation either to be in Mile or KM.
     * @return the final distance.
     *
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
}
