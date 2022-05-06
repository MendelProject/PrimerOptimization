/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import org.nyumc.isg.lims.objects.AssemblyUtils;

public class ModelDefaults {
    final static int minPrimerLength = 18;
    final static int maxPrimerLength = 36;

    /**
     *
     * @param assemblyType - assembly type
     * @return - minimal distance
     */
    static int minDistance(AssemblyUtils.AssemblyType assemblyType){
        return assemblyType == AssemblyUtils.AssemblyType.denovo ? 80:200;
    }
    /**
     *
     * @param assemblyType - assembly type
     * @return - max distance
     */
    static int maxDistance(AssemblyUtils.AssemblyType assemblyType){
        return assemblyType == AssemblyUtils.AssemblyType.denovo  ? 200:600;
    }

    final static int numPrimerCandidates = 100; // max number of candidates during evaluation

    final static int lengthStep = 2; // step to increment primer length
}
