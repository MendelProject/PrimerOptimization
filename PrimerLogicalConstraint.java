/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

/**
 * Abstract base class
 */
public abstract class PrimerLogicalConstraint extends PrimerConstraint{
    /**
     *
     * @param name - constraint name
     * @param type - constraint type
     */
    public PrimerLogicalConstraint(SupportedConstrains name, ConstraintType type) {
        super(name, type);
    }

    /**
     *
     * @param primer - primer DNA
     * @param environment - optimization environment
     * @return - true, if holds
     */
    abstract boolean  conditionHolds(String primer, Environment environment);

    /**
     *
     * @param primer - primer
     * @param environment - optimization environment
     * @return - weighted result
     */
    @Override
    public double evaluate(String primer, Environment environment) {
        double result = conditionHolds(primer.toUpperCase(), environment) ? 1.0:0;
        result = type == ConstraintType.constraint ? result : result * weight;
        return result;
    }
}
