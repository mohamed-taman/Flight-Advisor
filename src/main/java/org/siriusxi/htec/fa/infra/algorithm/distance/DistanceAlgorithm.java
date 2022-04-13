package org.siriusxi.htec.fa.infra.algorithm.distance;

/**
 * A distance Algorithm interface to represent different calculations algorithms type
 * of distances between any two points on earth.
 * <p>
 * The following is the supported distance algorithms:
 * <ol>
 *     <li>Haversine formula</li>
 *     <li>Orthodromic formula</li>
 * </ol>
 *
 * @author Mohamed Taman
 * @since v0.4
 */
public sealed interface DistanceAlgorithm permits HaversineAlgorithm, OrthodromicAlgorithm {
    
    /**
     * A factory method to return the implementation of selected distance  calculator algorithm.
     *
     * @param type of the algorithm.
     * @return The algorithm implementation.
     */
    static DistanceAlgorithm getAlgorithm(Type type) {
        return switch (type) {
            case HAVERSINE -> new HaversineAlgorithm();
            case ORTHODROMIC -> new OrthodromicAlgorithm();
        };
    }
    
    /**
     * Method to calculate the distance between co-ordinates of two points.
     *
     * @param first   point in latitude and longitude.
     * @param second  point in latitude and longitude.
     * @param measure the result of the calculation either to be in Mile or KM.
     * @return the final distance.
     */
    double calculate(Point first, Point second, MeasureType measure);
    
    /**
     * Method to calculate the distance between co-ordinates of two points.
     * And the calculated measure is in KM.
     *
     * @param first  point in latitude and longitude.
     * @param second point in latitude and longitude.
     * @return the final distance.
     */
    double calculate(Point first, Point second);
    
    /**
     * Enum to represent the implemented distance calculators algorithms.
     */
    enum Type {
        HAVERSINE,
        ORTHODROMIC
    }
    
    /**
     * The Radius of earth measurements.
     */
    enum MeasureType {
        /**
         * Earth radius in Mile.
         */
        MILE(3_958.0),
        /**
         * Earth radius in Kilometer.
         */
        KILOMETER(6_371.0);
        
        private final double value;
        
        MeasureType(double v) {
            this.value = v;
        }
        
        public double getValue() {
            return value;
        }
    }
    
}
