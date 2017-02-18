package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Функция для создания индивида по матрице множеств.
 */
public interface CreateUnitFunction {
    /**
     * @param setsMatrix матрица множеств.
     * @return полученный каким-то образом индивид.
     */
    Vector createUnit(SetsMatrix setsMatrix);
}
