package org.siriusxi.htec.fa.infra.calc.distance;

/**
 *  The Radius of earth measurements.
 *
 * @author Mohamed Taman
 * @since v0.4
 */
public enum MeasureType {
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
