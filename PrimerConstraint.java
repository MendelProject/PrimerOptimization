/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

/**
 * abstract base class
 */
public abstract class PrimerConstraint  extends Constraint<String>{
    /**
     *
     * @param name - constraint name
     * @param type - constraing type
     */
    public PrimerConstraint(SupportedConstrains name, ConstraintType type){
        super(name,type);
    }

    /**
     *
     * @param primer - primer
     * @param environment - optimiztion environment
     * @return computation results
     */
    public  abstract double  evaluate(String primer, Environment environment);
}
