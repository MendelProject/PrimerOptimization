/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import org.nyumc.isg.lims.common.LimsException;
import org.nyumc.isg.lims.objects.PrimerUtils;

public class PrimerHairpinConstraint  extends PrimerLogicalConstraint {
    final static int MaxHairpinLen = 8;

    /**
     *
     * @param name - constraint name
     * @param type - constraint type
     */
    public PrimerHairpinConstraint(SupportedConstrains name, ConstraintType type) {
        super(name, type);
    }

    /**
     *
     * @param primer - DNA sequence
     * @param environment - optimization environment
     * @return - true, if condition holds
     */
    @Override
    boolean conditionHolds(String primer, Environment environment) {
        try {
            return !PrimerUtils.isHairpin(primer, MaxHairpinLen);
        } catch (LimsException e){
            return false;
        }
    }

}
