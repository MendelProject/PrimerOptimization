/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */
package org.nyumc.isg.lims.optimizer;


/**
 * abstract parametrized template class
 * @param <T> - template parameter
 */
public abstract class  Constraint <T> {
    enum ConstraintType {constraint, strongPreference, preference}

    enum SupportedConstrains{
        PrimerDiversityConstraint,
        PrimerGCConstraint,
        PrimerHairpinConstraint,
        PrimerLengthConstraint,
        PrimerMaxPolymerRunConstraint,
        PrimerTemperatureConstraint,
        AmpliconLengthConstraint,
        PrimerUniqueConstraint}

    SupportedConstrains name;
    ConstraintType type;
    double weightUnits;
    protected double weight;

    /**
     *
     * @return - constraint weight
     */
    int getWeightUnits() {
      return (int)weightUnits;
    }

    /**
     *
     * @param name - constraint name
     * @param type - constraint type
     */
    public Constraint(SupportedConstrains name, ConstraintType type){
        this.name = name;
        this.type = type;
        switch (type) {
            case strongPreference:
                weightUnits = 10;
                break;
            case preference:
            case constraint:
                weightUnits = 1;
                break;
            default:
                weightUnits = 0;
                break;
        }
        weight = weightUnits;
    }

    /**
     *
     * @param totalWeightUnits - total weights
     */
    void normalizeWeight(int totalWeightUnits){
        weight = weightUnits /totalWeightUnits;
    }

    /**
     *
     * @return - constrain name
     */
    SupportedConstrains getName() { return name; }

    public abstract double  evaluate(T object, Environment environment);


}
