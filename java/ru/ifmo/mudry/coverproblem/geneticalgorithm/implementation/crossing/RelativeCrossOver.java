package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.crossing;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.Population;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.ArrayList;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class RelativeCrossOver implements CrossingFunction {
    @Override
    public ArrayList<Vector> cross(Parents parents, ArrayList<Vector> population, SetsMatrix matrix, PopulationPattern populationPattern) {
        boolean[] first = parents.getFirstParent().getVector(), second = parents.getSecondParent().getVector();
        boolean[] vector = new boolean[first.length];
        for (int i = 0; i < vector.length; i++) {
            if (first[i] == second[i]) {
                vector[i] = first[i];
            } else {
                double f1 = populationPattern.f[population.indexOf(parents.getFirstParent())],
                        f2 = populationPattern.f[population.indexOf(parents.getSecondParent())];
                double p0 = populationPattern.p0[i], p1 = populationPattern.p1[i];
                if (!first[i]) {
                    vector[i] = Math.random() * f1 * p0 < Math.random() * f2 * p1;
                } else {
                    vector[i] = Math.random() * f1 * p1 >= Math.random() * f2 * p0;
                }

            }
        }
        ArrayList<Vector> ans = new ArrayList<>(1);
        ans.add(new Vector(vector, FitnessFunction.calculateFitness(vector, matrix.cost)));
        return ans;
    }

    @Override
    public int getChildrenCount() {
        return 1;
    }
}
