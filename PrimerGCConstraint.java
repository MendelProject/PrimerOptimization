/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import org.nyumc.isg.lims.objects.PrimerUtils;

public class PrimerGCConstraint extends PrimerRangeConstraint{
    /**
     *
     * @param name - constraint name
     * @param type - constraint type
     * @param min - min value
     * @param max - max value
     */
    public PrimerGCConstraint(SupportedConstrains name, ConstraintType type, double min, double max) {
        super(name, type, min, max);
    }

    /**
     *
     * @param primer - primer sequence
     * @param environment - optimization environment
     * @return - constraint value
     */
    @Override
    double computeValue(String primer, Environment environment) {
       return PrimerUtils.contentGC(primer) * 100; // convert to percent
    }
}
