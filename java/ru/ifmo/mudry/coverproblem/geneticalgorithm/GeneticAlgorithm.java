package ru.ifmo.mudry.coverproblem.geneticalgorithm;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public interface GeneticAlgorithm {
    Population oldCalculate(boolean[][] matrix, double[] cost);

    Population calculate(boolean[][] matrix, double[] cost, int populationSize, int populationGrowth,
                         int stepsToStop, CreateUnitFunction createUnitFunction, CrossingFunction crossingFunction,
                         MutationFunction mutationFunction, RecoveryFunction recoveryFunction,
                         ReplacementFunction replacementFunction, SelectionFunction selectionFunction);

    Population calculate(SetsMatrix matrix, int populationSize, int populationGrowth,
                         int stepsToStop, GeneticFunctions functions);
}
