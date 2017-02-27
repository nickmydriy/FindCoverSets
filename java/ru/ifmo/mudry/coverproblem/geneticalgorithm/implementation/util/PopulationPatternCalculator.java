package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.Population;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.PopulationPattern;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class PopulationPatternCalculator {
    public static PopulationPattern calculate(ArrayList<Vector> population) {
        int populationSize = population.size(), vectorSize = population.get(0).getVector().length;
        double[] p0 = new double[vectorSize], p1 = new double[vectorSize],
                f = new double[populationSize];
        for (int i = 0; i < vectorSize; i++) {
            int p = 0;
            for (Vector aPopulation : population) {
                if (aPopulation.getVector()[i]) {
                    p++;
                }
            }
            p1[i] = (double)p / populationSize;
            p0[i] = 1.0 - p1[i];
        }
        double[] reverse = new double[populationSize];
        for (int i = 0; i < populationSize; i++) {
            reverse[i] = 1.0 / population.get(i).getFitness();
        }
        double sum = Arrays.stream(reverse).sum();
        for (int i = 0; i < populationSize; i++) {
            f[i] = reverse[i] / sum;
        }
        return new PopulationPattern(p0, p1, f);
    }
}
