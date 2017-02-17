package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.crossing;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.ArrayList;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class StandardOnePointCrossOver implements CrossingFunction {

    @Override
    public ArrayList<Vector> cross(Parents parents, ArrayList<Vector> population, SetsMatrix matrix) {
        Vector first = parents.getFirstParent();
        Vector second = parents.getSecondParent();
        int pos = Math.abs(Randomizer.random.nextInt()) % first.getVector().length;
        boolean[] vector1 = new boolean[first.getVector().length];
        System.arraycopy(first.getVector(), 0, vector1, 0, pos);
        System.arraycopy(second.getVector(), pos, vector1, pos, first.getVector().length - pos);
        boolean[] vector2 = new boolean[first.getVector().length];
        System.arraycopy(second.getVector(), 0, vector1, 0, pos);
        System.arraycopy(first.getVector(), pos, vector1, pos, first.getVector().length - pos);
        ArrayList<Vector> arr = new ArrayList<>(2);
        arr.add(new Vector(vector1, FitnessFunction.calculateFitness(vector1, matrix.cost)));
        arr.add(new Vector(vector2, FitnessFunction.calculateFitness(vector2, matrix.cost)));
        return arr;
    }
}
