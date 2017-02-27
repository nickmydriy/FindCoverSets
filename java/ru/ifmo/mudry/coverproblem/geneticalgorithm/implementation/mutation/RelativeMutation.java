package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.mutation;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.Arrays;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class RelativeMutation implements MutationFunction {

    @Override
    public Vector mutate(Vector unit, SetsMatrix matrix, PopulationPattern populationPattern) {
        boolean[] vector = unit.getVector();
        boolean[] schema = getSchema(vector, populationPattern);
        vector = mutateOnAllRange(vector, schema, populationPattern);
        return new Vector(vector, FitnessFunction.calculateFitness(vector, matrix.cost));
    }

    private boolean[] mutateOnAllRange(boolean[] vector, boolean[] schema, PopulationPattern populationPattern) {
        int size = vector.length;
        double[] h = new double[size];
        Arrays.fill(h, 0);
        for (int i = 0; i < size; i++) {
            if (!schema[i]) {
                double p0 = populationPattern.p0[i], p1 = populationPattern.p1[i];
                h[i] = 1.0 / (-p0 * Math.log(p0) - p1 * Math.log(p1));
            }
        }
        double sum = Arrays.stream(h).sum();
        double[] p = new double[size];
        double pt = 1.0 / size;
        for (int i = 0; i < size; i++) {
            if (!schema[i]) {
                p[i] = h[i] / sum;
            } else {
                if (Math.random() < pt) {
                    p[i] = pt;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            double p0 = populationPattern.p0[i], p1 = populationPattern.p1[i];
            if (p[i] >= pt && Math.random() < p[i]) {
                if (vector[i] && p1 > p0) {
                    vector[i] = false;
                }
                if (!vector[i] && p0 > p1) {
                    vector[i] = true;
                }
            }
        }
        return vector;
    }

    private boolean[] getSchema(boolean[] vector, PopulationPattern populationPattern) {
        boolean[] schema = new boolean[vector.length];
        for (int i = 0; i < vector.length; i++) {
            double p0 = populationPattern.p0[i], p1 = populationPattern.p1[i];
            if (p0 == 0.0 || p1 == 0.0) {
                schema[i] = true;
            }
        }
        return schema;
    }
}
