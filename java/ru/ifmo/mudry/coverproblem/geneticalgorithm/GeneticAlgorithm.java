package ru.ifmo.mudry.coverproblem.geneticalgorithm;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

/**
 * Интерфейс генетического алгоритмя для решения взвешенной задачи о покрытии.
 */
public interface GeneticAlgorithm {

    /**
     * Вычисляет оптимальное значение покрытия используя класс {@link Population}
     *
     * @param matrix матрица, описывающая множества.
     * @param cost вес множеств.
     * @param populationSize размер популяции.
     * @param populationGrowth определяет количество проводимых скрещиваний для каждого шага.
     * @param stepsToStop необходимое количество шагов без улучшения для остановки алгоритма.
     * @param createUnitFunction функция для создания начальной популяции.
     * @param crossingFunction функция скрещивания.
     * @param mutationFunction функция мутации.
     * @param recoveryFunction функция восстановления ответа, в случае, если получившийся индивид не является покрытием.
     * @param replacementFunction функция смены популяции.
     * @param selectionFunction функция для отбора пар для скрещивания.
     * @return получившаяся в ходе работы алгоритма популяция.
     */
    Population calculate(boolean[][] matrix, double[] cost, int populationSize, int populationGrowth,
                         int stepsToStop, CreateUnitFunction createUnitFunction, CrossingFunction crossingFunction,
                         MutationFunction mutationFunction, RecoveryFunction recoveryFunction,
                         ReplacementFunction replacementFunction, SelectionFunction selectionFunction);

    /**
     * Вызывает {@link GeneticAlgorithm#calculate(boolean[][], double[], int, int, int,
     * CreateUnitFunction, CrossingFunction, MutationFunction,
     * RecoveryFunction, ReplacementFunction, SelectionFunction)} раскрывая элементы matrix и functions.
     *
     * @param matrix матрица описывающая множества и их веса.
     * @param populationSize размер популяции.
     * @param populationGrowth определяет количество проводимых скрещиваний для каждого шага.
     * @param stepsToStop необходимое количество шагов без улучшения для остановки алгоритма.
     * @param functions набор необходимых в {@link GeneticAlgorithm#calculate(boolean[][], double[], int, int, int,
     *                  CreateUnitFunction, CrossingFunction, MutationFunction, RecoveryFunction,
     *                  ReplacementFunction, SelectionFunction)} функций.
     * @return получившаяся в ходе работы алгоритма популяция.
     */
    Population calculate(SetsMatrix matrix, int populationSize, int populationGrowth,
                         int stepsToStop, GeneticFunctions functions);
}
