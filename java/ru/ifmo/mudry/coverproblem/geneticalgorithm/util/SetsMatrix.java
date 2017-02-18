package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Класс описывающий набор множеств.
 */
public class SetsMatrix {
    /**
     * Матрица описывающая набор множеств.
     *
     * Каждая строка описывает множество.
     */
    public final boolean[][] matrix;

    /**
     * Количество множеств.
     */
    public final int setsCount;

    /**
     * количество элементов.
     */
    public final int elementsCount;

    /**
     * Цена множеств.
     */
    public final double[] cost;

    /**
     * Стандартный конструктор.
     *
     * @param matrix матрица описывающая множества.
     * @param cost массив цен множеств.
     */
    public SetsMatrix(boolean[][] matrix, double[] cost) {
        this.matrix = matrix;
        this.cost = cost;
        setsCount = matrix.length;
        elementsCount = matrix[0].length;
    }
}
