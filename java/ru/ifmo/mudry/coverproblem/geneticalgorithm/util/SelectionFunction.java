package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public interface SelectionFunction {
    ArrayList<Parents> select(List<Vector> population, SetsMatrix matrix, int count);
}
