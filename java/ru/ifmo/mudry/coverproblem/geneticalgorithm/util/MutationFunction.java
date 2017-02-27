package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Функция мутации.
 */
public interface MutationFunction {
    /**
     * @param unit индивид над которым надо произвести мутацию.
     * @param matrix матрица множеств.
     * @return индивид, получившийся из unit, с некоторым измененным набором генов. (Может и не быть изменений).
     */
    Vector mutate(Vector unit, SetsMatrix matrix, PopulationPattern populationPattern);
}
