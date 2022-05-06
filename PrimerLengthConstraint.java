/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

public class PrimerLengthConstraint extends PrimerRangeConstraint {
    /**
     *
     * @param name - constraint name
     * @param type - constraint type
     * @param min - min length
     * @param max - max length
     */
    public PrimerLengthConstraint(SupportedConstrains name, ConstraintType type, double min, double max) {
        super(name, type, min, max);
    }

    /**
     *
     * @param primer - primer DNA
     * @param environment - optimization environment
     * @return - primer length
     */
    @Override
    double computeValue(String primer,  Environment environment) {
        return primer.length();
    }
}
