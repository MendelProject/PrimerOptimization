/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import org.nyumc.isg.lims.common.Util;

public class PrimerMaxPolymerRunConstraint extends PrimerLogicalConstraint {
    /**
     *
     * @param name - constraint name
     * @param type - constraint type
     */
    public PrimerMaxPolymerRunConstraint(SupportedConstrains name, ConstraintType type) {
        super(name,type);
    }

    /**
     *
     * @param primer - primer DNA
     * @param environment - optimization environment
     * @return - true, if condition holds
     */
    @Override
    boolean conditionHolds(String primer,  Environment environment) {
        return !Util.fiveInARow(primer) ;
    }
}
