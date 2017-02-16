package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class SetsMatrix {
    public final boolean[][] matrix;

    public final int setsCount;

    public final int elementsCount;

    public final double[] cost;

    public SetsMatrix(boolean[][] matrix, double[] cost) {
        this.matrix = matrix;
        this.cost = cost;
        setsCount = matrix.length;
        elementsCount = matrix[0].length;
    }
}
