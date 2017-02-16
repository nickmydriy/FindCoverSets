package ru.ifmo.mudry.coverproblem.bruteforcealgorithm;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class BruteforceAlgorithm {
    boolean[] min;
    double minCost = Double.MAX_VALUE;
    final double[] cost;
    final boolean[][] matrix;

    int elementsCount = 0;
    int setsCount = 0;

    public BruteforceAlgorithm(boolean[][] matrix, double[] cost) {
        elementsCount = matrix[0].length;
        setsCount = matrix.length;
        this.cost = cost;
        this.matrix = matrix;
    }

    public Vector calculate() {
        for (long i = 1; i < Math.pow(2, setsCount); i++) {
            boolean[] vector = getVector(i);
            if (checkCover(vector)) {
                double r = getCost(vector);
                if (r < minCost) {
                    minCost = r;
                    min = vector;
                }
            }
        }
        return new Vector(min, minCost);
    }

    private boolean[] getVector(long input) {
        boolean[] vector = new boolean[setsCount];
        for (int i = setsCount - 1; i >= 0; i--) {
            vector[i] = (input & (1 << i)) != 0;
        }
        return vector;
    }

    private double getCost(boolean[] vector) {
        double result = 0;
        for (int i = 0; i < vector.length; i++) {
            if (vector[i]) {
                result += cost[i];
            }
        }
        return result;
    }

    private boolean checkCover(boolean[] vector) {
        Set<Integer> coverSet = new TreeSet<>();
        for (int i = 0; i < vector.length; i++) {
            if (vector[i]) {
                for (int j = 0; j < elementsCount; j++) {
                    if (matrix[i][j]) {
                        coverSet.add(j);
                    }
                }
            }
            if (coverSet.size() == elementsCount) {
                return true;
            }
        }
        return false;
    }
}
