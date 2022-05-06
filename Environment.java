/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import org.nyumc.isg.lims.common.LimsException;
import org.nyumc.isg.lims.objects.*;

import java.util.List;

/**
 * Constraint Environment  class
 */
public class Environment {
    PrimersRequest request;
    DNAAssembly assembly;
    String sequence;
    boolean isReversed;

    /**
     *
     * @param request - request object
     * @throws LimsException -
     */
    public Environment(PrimersRequest request) throws LimsException  {
        this.request = request;
        isReversed = false;
        try {
            assembly = AssemblyUtils.getAssembly(request.getStudyName(), request.getProjectName(), request.getAssemblyName());
        } catch (LimsException e){
            throw e;
        } catch (Exception e){
            throw new LimsException(e.getMessage(),e);
        }
    }

    /**
     * Environment for 'light' assembly
     * @param request - light primers request
     * @param segments - segments
     * @throws LimsException - lims exception
     */
    public Environment(LightPrimersRequest request, List<Segment> segments) throws LimsException{
        this.request = request;
        isReversed = false;
        assembly = new LightAssembly(AssemblyUtils.assemblyFromSegments(segments,10));
    }

    /**
     *
     * @return - primer request object
     */
    public PrimersRequest getRequest() {
        return request;
    }

    public DNAAssembly getAssembly() {
        return assembly;
    }

    /**
     *
     * @param sequence - DNA sequence
     */
    public void setSequence(String sequence) { this.sequence = sequence; }

    /**
     *
     * @return - DNA Sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     *
     * @return - true, if reversed
     */
    public boolean isReversed() { return isReversed; }

    /**
     *
     * @param reversed - boolean flag
     */
    public void setReversed(boolean reversed) { isReversed = reversed; }
}
