package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public interface MutationFunction {
    Vector mutate(Vector unit, SetsMatrix matrix);
}
