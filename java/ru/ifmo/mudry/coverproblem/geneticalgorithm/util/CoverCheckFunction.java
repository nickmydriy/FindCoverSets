package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Функция проверки на существование покрытия, которое задает индивид.
 */
public interface CoverCheckFunction {
    /**
     * @param unit индивид.
     * @param matrix матрица множеств.
     * @return true, если индивид задает сущствующие покрытие, иначе false.
     */
    boolean checkCover(Vector unit, SetsMatrix matrix);
}
