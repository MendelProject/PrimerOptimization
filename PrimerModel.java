/*
 * Institute For Systems Genetics. NYU Langone HealthCopyright (c) 2020. Created by Sergei German
 */

package org.nyumc.isg.lims.optimizer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nyumc.isg.lims.common.LimsException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Optimization model
 */
public class PrimerModel extends Model<String>{
    /**
     * Helper class
     */
    class RankedPrimer implements Comparable<RankedPrimer>{
        String value;
        double rank;

        public RankedPrimer(String primer, double rank) {
            this.value = primer;
            this.rank = rank;
        }

        @Override
        public int compareTo(RankedPrimer other) {
            if (other.rank > this.rank)
                return 1;
            else if (other.rank < this.rank)
                return -1;
            return 0;
        }

        @Override
        public String toString(){
        return "" + rank + ", " + value;
        }
    }

    int minPrimerLength;
    int maxPrimerLength;
    BufferedWriter stream;
    protected static final Logger logger = LogManager.getLogger("MenDEL");

    /**
     *
     * @param environment - models's Environment
     * @throws LimsException - LimsException
     */
    public PrimerModel(Environment environment) throws LimsException{
        super(environment);

        String  constraintRequest = environment.request.getConstraints();
        if (constraintRequest.isEmpty() || constraintRequest.equalsIgnoreCase("None"))
            throw new LimsException("Invalid constraint request");

        constraints = new ArrayList<>();
        preferences = new ArrayList<>();
        JsonArray  jac = new JsonParser().parse(constraintRequest).getAsJsonArray();
        for (int i = 0; i < jac.size(); ++i){
            JsonObject jo = jac.get(i).getAsJsonObject();
            Constraint constraint = PrimerConstraintFactory.getInstance().get(jo);
            Constraint.ConstraintType type = Constraint.ConstraintType.valueOf(jo.get("type").getAsString());
            if (type == Constraint.ConstraintType.constraint) {
                constraints.add(constraint);
            }
            else
                preferences.add(constraint);
        }
        weightPreferences();
        minPrimerLength = minPrimerLength();
        maxPrimerLength = maxPrimerLength();
    }

    /**
     * Weights preferences
     */
    private void weightPreferences(){
        int totalWeightUnits = 0;
        for (Constraint preference : preferences){
            totalWeightUnits += preference.getWeightUnits();
        }

        for (Constraint preference : preferences){
            preference.normalizeWeight(totalWeightUnits);
        }
    }

    /**
     *
     * @param primer - primer to be avaluated
     * @param environment - Environment context
     * @return - primer's rank
     */
    private double evaluatePrimer(String primer, Environment environment){
        double rank = 1;

        for (Constraint constraint : constraints){
            if(constraint.evaluate(primer, environment) <= 0)
                return 0;
        }
        for (Constraint preference:preferences){
            rank += preference.evaluate(primer, environment);
        }
        return rank;
    }

    /**
     *
     * @return - minimal amplicon length
     */
    public int getMinAmpliconLength(){
        for(Constraint constraint : constraints){
            if (constraint.getName() == Constraint.SupportedConstrains.AmpliconLengthConstraint)
                return (int)((AmpliconLengthConstraint)constraint).minValue;
        }
        return ModelDefaults.minDistance(environment.getAssembly().getType());
    }

    /**
     *
     * @return - max amplicon length
     */
    public int getMaxAmpliconLength(){
        for(Constraint constraint : constraints){
            if (constraint.getName() == Constraint.SupportedConstrains.AmpliconLengthConstraint)
                return (int)((AmpliconLengthConstraint)constraint).maxValue;
        }
        return ModelDefaults.maxDistance(environment.getAssembly().getType());
    }

    /**
     *
     * @return - extension
     */
    public int getExtension(){
        for(Constraint constraint : constraints){
            if (constraint.getName() == Constraint.SupportedConstrains.AmpliconLengthConstraint)
                return (int)((AmpliconLengthConstraint)constraint).minValue;
        }
        return ModelDefaults.minDistance(environment.getAssembly().getType());
    }

    /**
     *
     * @return - min primer length
     */
    private int minPrimerLength(){
        for(Constraint constraint : constraints){
            if (constraint.getName() == Constraint.SupportedConstrains.PrimerLengthConstraint)
                return (int)((PrimerLengthConstraint)constraint).minValue;
        }
        return ModelDefaults.minPrimerLength;
    }

    /**
     *
     * @return - max primer length
     */
    private int maxPrimerLength(){
        for(Constraint constraint : constraints){
            if (constraint.getName() == Constraint.SupportedConstrains.PrimerLengthConstraint)
                return (int)((PrimerLengthConstraint)constraint).maxValue;
        }
        return ModelDefaults.maxPrimerLength;
    }

    /**
     *
     * @return - min primer length
     */
    public int getMinPrimerLength(){
        return minPrimerLength;
    }

    /**
     *
     * @return - max primer length
     */
    public int getMaxPrimerLength(){
       return maxPrimerLength;
    }

    /**
     *
     * @param sequence - sequence to evaluate primers
     * @return
     */
    @Override
    public List<RankedPrimer> evaluate(String sequence, boolean reversed) {
        List<RankedPrimer> primers = new ArrayList<>();
        int sequenceLength = sequence.length();
        int step = sequence.length() / ModelDefaults.numPrimerCandidates;

        for (int i = 0, index = 0;
             primers.size() < ModelDefaults.numPrimerCandidates &&
                     i < sequenceLength - minPrimerLength;
             i += step) {
            // use index to determine where we should start computing primers
            // when we compute forward primers we move right to left,
            // reversed primers - move left to right
            index = reversed ? i : (sequenceLength - (i + step));

            for (int length = minPrimerLength, count = 0;
                 length < maxPrimerLength && index >= 0 && (index + length) < sequenceLength && count < 5;
                 length += ModelDefaults.lengthStep) {
                String primer = sequence.substring(index, index + length);
                double rank = evaluatePrimer(primer, environment);
                if (rank > 0) {
                    count++;
                    primers.add(new RankedPrimer(primer, rank));
                }
            }
        }
        Collections.sort(primers);
        return primers;
    }

    /**
     *
     * @param sequence - dna sequence where primer should be computed
     * @param reversed - indicator of whether primer is a forward or reversed
     * @return
     * @throws LimsException - Lims exception
     */
    public String getPrimer(String sequence, boolean reversed) throws LimsException {
        environment.setSequence(sequence);
        environment.setReversed(reversed);
        List<RankedPrimer> primers =  evaluate(sequence, reversed);
        if (primers.size() < 1)
            throw new LimsException("Unable to find primer.");
        RankedPrimer primer = primers.get(0);
        System.out.println(primer);
        return primer.value;
    }

}
