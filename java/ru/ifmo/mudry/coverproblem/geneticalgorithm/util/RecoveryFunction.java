package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

import java.util.ArrayList;

/**
 * Функция восстановления покрытия.
 */
public interface RecoveryFunction {
    /**
     * @param unit индивид для восстановления.
     * @param matrix матрица множеств.
     * @return индивида, полученного из unit, который является покрытием.
     */
    Vector recover(Vector unit, SetsMatrix matrix);
}
