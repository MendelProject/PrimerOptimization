/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import org.nyumc.isg.lims.objects.PrimerUtils;

public class PrimerInfoRichnessConstraint extends PrimerLogicalConstraint {
    /**
     *
     * @param name - constraint name
     * @param type - constraing type
     */
    public PrimerInfoRichnessConstraint(SupportedConstrains name, ConstraintType type) {
        super(name, type);
    }

    /**
     *
     * @param primer - primer seqyence
     * @param environment - optimization environment
     * @return - true, if condition holds
     */
    @Override
    boolean conditionHolds(String primer,  Environment environment) {
        return PrimerUtils.isDiversePrimer(primer);
    }
}
