package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class FitnessFunction {
    public static double calculateFitness(boolean[] vector, double[] cost) {
        double result = 0;
        for (int i = 0; i < vector.length; i++) {
            if (vector[i]) {
                result += cost[i];
            }
        }
        return result;
    }
}
