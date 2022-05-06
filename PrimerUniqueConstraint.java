/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import org.nyumc.isg.lims.common.LimsException;
import org.nyumc.isg.lims.common.Util;

public class PrimerUniqueConstraint extends PrimerLogicalConstraint {
    /**
     *
     * @param name - constraint name
     * @param type - constraint type
     */
    public PrimerUniqueConstraint(SupportedConstrains name, ConstraintType type) {
        super(name, type);
    }

    /**
     *
     * @param primer - primer DNA
     * @param environment - optimization environment
     * @return - true, if unique
     */

    @Override
    boolean conditionHolds(String primer,  Environment environment) {
        try {
            return !Util.isPrimerPathwayDuplicate(primer, environment.assembly.getValue().toUpperCase());
        } catch (LimsException e){
            return false;
        }
    }
}
