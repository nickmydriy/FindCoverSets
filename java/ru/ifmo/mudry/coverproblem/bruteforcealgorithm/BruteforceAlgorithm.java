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

    public Vector calculateMultiThread4() {
        final boolean[][] min = new boolean[4][];
        final double[] minCost = new double[4];
        Arrays.fill(minCost, Double.MAX_VALUE);

        int q = (int)Math.pow(2, setsCount) / 4;

        Thread thread1 = new Thread(() -> {
            for (long i = 1; i < q; i++) {
                boolean[] vector = getVector(i);
                if (checkCover(vector)) {
                    double r = getCost(vector);
                    if (r < minCost[0]) {
                        minCost[0] = r;
                        min[0] = vector;
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (long i = q; i < q * 2; i++) {
                boolean[] vector = getVector(i);
                if (checkCover(vector)) {
                    double r = getCost(vector);
                    if (r < minCost[1]) {
                        minCost[1] = r;
                        min[1] = vector;
                    }
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (long i = q * 2; i < q * 3; i++) {
                boolean[] vector = getVector(i);
                if (checkCover(vector)) {
                    double r = getCost(vector);
                    if (r < minCost[2]) {
                        minCost[2] = r;
                        min[2] = vector;
                    }
                }
            }
        });

        Thread thread4 = new Thread(() -> {
            for (long i = q * 3; i < q * 4; i++) {
                boolean[] vector = getVector(i);
                if (checkCover(vector)) {
                    double r = getCost(vector);
                    if (r < minCost[3]) {
                        minCost[3] = r;
                        min[3] = vector;
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double curMin = Double.MAX_VALUE;
        boolean[] answer = new boolean[setsCount];

        for (int i = 0; i < 3; i++) {
            if (minCost[i] < curMin) {
                curMin = minCost[i];
                answer = min[i];
            }
        }
        return new Vector(answer, curMin);
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
