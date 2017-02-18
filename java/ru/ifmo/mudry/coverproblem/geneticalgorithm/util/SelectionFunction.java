package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Функция селекции.
 */
public interface SelectionFunction {
    /**
     * @param population популяция.
     * @param matrix матрица множеств.
     * @param count количество необходимых родительских пар.
     * @return список {@link Parents} родительских пар.
     */
    ArrayList<Parents> select(List<Vector> population, SetsMatrix matrix, int count);
}
