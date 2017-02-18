package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

import java.util.ArrayList;

/**
 * Функция скрещивания.
 */
public interface CrossingFunction {
    /**
     * @param parents родители.
     * @param population популяция.
     * @param matrix матрица множеств.
     * @return возвращает список состоящих из детей заданных в parent индивидов.
     */
    ArrayList<Vector> cross(Parents parents, ArrayList<Vector> population, SetsMatrix matrix);
}
