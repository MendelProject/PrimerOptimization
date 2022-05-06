/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import org.nyumc.isg.lims.objects.PrimerUtils;

public class PrimerTemperatureConstraint extends PrimerRangeConstraint{
    public static final double DNAConcentration = 5;
    public static final double SaltConcentration = 0.05;

    /**
     *
     * @param name - constraint name
     * @param type - constrint type
     * @param min - mon temperature
     * @param max - max temperature
     */
    public PrimerTemperatureConstraint(SupportedConstrains name, ConstraintType type, double min, double max) {
        super(name,type, min, max);
    }

    /**
     *
     * @param primer - primer DNA sequence
     * @param environment - optimization environment
     * @return - annealing temperature
     */
    @Override
    double computeValue(String primer, Environment environment) {
        if (primer.length() <= 13 )
            return PrimerUtils.temperatureShort(primer);
        else
            return PrimerUtils.temperatureSL98(primer, DNAConcentration, SaltConcentration);
    }
}
