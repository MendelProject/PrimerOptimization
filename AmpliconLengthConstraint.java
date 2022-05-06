/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;


public class AmpliconLengthConstraint extends PrimerRangeConstraint {

    /**
     *
     * @param name - public consgtructor
     * @param type - constraint type
     * @param min - min value
     * @param max - max value
     */
    public AmpliconLengthConstraint(SupportedConstrains name, ConstraintType type, double min, double max) {
        super(name, type, min, max);
    }

    /**
     *
     * @param primer - primer DNA
     * @param environment - environment
     * @return
     */
    @Override
    double computeValue(String primer,  Environment environment) {
        return environment.getSequence().indexOf(primer);
    }

    /**
     *
     * @param primer - primer DNA
     * @param environment - environment
     * @return
     */
    @Override
    public  double  evaluate(String primer, Environment environment){
        value = computeValue(primer,  environment);
        double length = environment.getSequence().length() ;

        double result = 0.0;
        if (type == ConstraintType.constraint )
            result =  1.0 ; // we are ALWAYS within the amplicon
        else {
            // reversed primers need to be close to the beginning of the region,
            // forward primers need to be closer to the end of the region
            result = environment.isReversed() ? (1.0 - value / length) : (1.0 - (length - value) / length);
            result *= weight;
        }
        return result;
    }
}
