package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.selection;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Parents;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SelectionFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class Outbreeding extends Breeding {
    @Override
    protected Vector getPair(List<Vector> population, Vector vec) {
        return population.stream().max((o1, o2) -> {
            double f1 = distance(vec, o1);
            double f2 = distance(vec, o2);
            return f1 == f2 ? 0 : f1 > f2 ? 1 : -1;
        }).get();
    }
}
