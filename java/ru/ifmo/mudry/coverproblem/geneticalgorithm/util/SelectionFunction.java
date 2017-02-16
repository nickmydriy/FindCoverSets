package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

import javafx.util.Pair;

import java.util.List;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public interface SelectionFunction {
    Parents select(List<Vector> population);
}
