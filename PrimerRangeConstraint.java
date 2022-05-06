/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

public abstract class PrimerRangeConstraint extends PrimerConstraint {
    double minValue;
    double maxValue;
    double value;

    /**
     *
     * @param name - constraint name
     * @param type - constraint type
     * @param min - min value
     * @param max - maxe value
     */
    public PrimerRangeConstraint(SupportedConstrains name, ConstraintType type, double min, double max){
        super(name,type);
        this.minValue = min;
        this.maxValue = max;
    }

    /**
     *
     * @return - min value
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     *
     * @return - max value
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     *
     * @return - value
     */
    public double getValue() {
        return value;
    }

    /**
     *
     * @param primer - primer DNA sequence
     * @param environment - optimization environment
     * @return - computed value
     */
    abstract double computeValue(String primer,  Environment environment);

    /**
     *
     * @param primer - primer
     * @param environment - optimiztion environment
     * @return - weighted result
     */
    public  double  evaluate(String primer, Environment environment){
        value = computeValue(primer,  environment);
        double midValue = (minValue + maxValue) / 2.0;

        double result = 0.0;
        if (type == ConstraintType.constraint )
            result = value >= minValue && value <= maxValue ? 1.0 : 0.0;
        else
            result =  weight * (1.0 - Math.abs(midValue - value) / midValue);

        return result;
    }
}
