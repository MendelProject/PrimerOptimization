/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import com.google.gson.JsonObject;
import org.nyumc.isg.lims.common.LimsException;

/**
 * Singleton factory class
 */
public class PrimerConstraintFactory {
    /**
     *
     */
    private static final PrimerConstraintFactory instance = new PrimerConstraintFactory();

    /**
     *
     * @return - static factory instance
     */
    public static final PrimerConstraintFactory getInstance() {
        return instance;
    }

    /**
     *
     * @param jo - jason object
     * @return - Constraint object
     * @throws LimsException - Lims Exception
     */
    public Constraint get(JsonObject jo) throws LimsException{
        String name = jo.get("name").getAsString();
        Constraint.ConstraintType type = Constraint.ConstraintType.valueOf(jo.get("type").getAsString());

        Constraint constraint;
        double min,max;
        Constraint.SupportedConstrains supportedConstraint = Constraint.SupportedConstrains.valueOf(name);
        switch (supportedConstraint) {
            case PrimerDiversityConstraint:
                constraint = new PrimerInfoRichnessConstraint(supportedConstraint, type);
                break;
            case PrimerGCConstraint:
                min = jo.get("min").getAsDouble();
                max = jo.get("max").getAsDouble();
                constraint = new PrimerGCConstraint(supportedConstraint, type,min,max);
                break;
            case PrimerHairpinConstraint:
                constraint = new PrimerHairpinConstraint(supportedConstraint, type);
                break;
            case PrimerLengthConstraint:
                min = jo.get("min").getAsDouble();
                max = jo.get("max").getAsDouble();
                constraint = new PrimerLengthConstraint(supportedConstraint, type,min,max);
                break;
            case PrimerMaxPolymerRunConstraint:
                constraint = new PrimerMaxPolymerRunConstraint(supportedConstraint, type);
                break;
            case PrimerTemperatureConstraint:
                min = jo.get("min").getAsDouble();
                max = jo.get("max").getAsDouble();
                constraint = new PrimerTemperatureConstraint(supportedConstraint, type,min,max);
                break;
            case AmpliconLengthConstraint:
                min = jo.get("min").getAsDouble();
                max = jo.get("max").getAsDouble();
                constraint = new AmpliconLengthConstraint(supportedConstraint, type,min,max);
                break;
            case PrimerUniqueConstraint:
                constraint = new PrimerUniqueConstraint(supportedConstraint, type);
                break;
            default:
                throw new LimsException("Unknown Constraint class: " + name);
        }
        return constraint;

    }

    /**
     * Private constructor
     */
    private PrimerConstraintFactory(){}
}
