package ru.ifmo.mudry.coverproblem.geneticalgorithm;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public interface GeneticAlgorithm {
    Population calculate(boolean[][] matrix, double[] cost);
}
