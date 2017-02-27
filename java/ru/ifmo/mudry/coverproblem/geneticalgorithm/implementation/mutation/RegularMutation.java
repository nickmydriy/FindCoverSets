package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.mutation;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.Arrays;

/**
 * Created by Nick Mudry on 23.02.2017.
 */
public class RegularMutation implements MutationFunction {
    final double rate;

    public RegularMutation(double rate) {
        this.rate = rate;
    }

    @Override
    public Vector mutate(Vector unit, SetsMatrix matrix, PopulationPattern populationPattern) {
        boolean[] vector = unit.getVector();
        for (int i = 0; i < vector.length; i++) {
            if (Math.random() < rate) {
                vector[i] = !vector[i];
            }
        }
        return new Vector(vector, FitnessFunction.calculateFitness(vector, matrix.cost));
    }
}
