package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public interface CoverCheckFunction {
    boolean checkCover(Vector unit, SetsMatrix matrix);
}
