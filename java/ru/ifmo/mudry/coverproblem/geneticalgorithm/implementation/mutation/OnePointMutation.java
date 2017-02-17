package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.mutation;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.FitnessFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.MutationFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class OnePointMutation implements MutationFunction {
    @Override
    public Vector mutate(Vector unit, SetsMatrix matrix) {
        int pos = Math.abs(Randomizer.random.nextInt()) % unit.getVector().length;
        boolean[] vector = unit.getVector();
        vector[pos] = !vector[pos];
        return new Vector(vector, FitnessFunction.calculateFitness(vector, matrix.cost));
    }
}
