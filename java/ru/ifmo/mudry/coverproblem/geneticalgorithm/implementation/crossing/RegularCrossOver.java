package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.crossing;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.ArrayList;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class RegularCrossOver implements CrossingFunction {
    final double rate;

    public RegularCrossOver(double rate) {
        this.rate = rate;
    }

    @Override
    public ArrayList<Vector> cross(Parents parents, ArrayList<Vector> population, SetsMatrix matrix, PopulationPattern populationPattern) {
        Vector first = parents.getFirstParent();
        Vector second = parents.getSecondParent();
        boolean[] vector1 = new boolean[first.getVector().length];
        boolean[] vector2 = new boolean[first.getVector().length];
        for (int i = 0; i < first.getVector().length; i++) {
            if (Math.random() < rate) {
                vector1[i] = first.getVector()[i];
                vector2[i] = second.getVector()[i];
            } else {
                vector2[i] = first.getVector()[i];
                vector1[i] = second.getVector()[i];
            }
        }
        ArrayList<Vector> arr = new ArrayList<>(2);
        arr.add(new Vector(vector1, FitnessFunction.calculateFitness(vector1, matrix.cost)));
        arr.add(new Vector(vector2, FitnessFunction.calculateFitness(vector2, matrix.cost)));
        return arr;
    }

    @Override
    public int getChildrenCount() {
        return 2;
    }
}
