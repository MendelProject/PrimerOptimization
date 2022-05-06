/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import java.util.List;

/**
 * Abstract parametrized optimization model
 * @param <T>
 */
public abstract class Model <T>{
    protected List<Constraint> constraints;
    protected List<Constraint> preferences;
    protected Environment environment;

    /**
     *
     * @param environment - optimixation environment
     */
    public Model(Environment environment){
        this.environment = environment;
    }

    /**
     * abstract method
     * @param object - template object
     * @param reversed - boolean flag
     * @return object
     */
    public  abstract Object evaluate(T object, boolean reversed);
}
