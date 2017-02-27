package ru.ifmo.mudry.coverproblem.geneticalgorithm;


import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.PopulationPatternCalculator;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.ArrayList;

/**
 * Класс описывает текущую популяцию.
 * Осуществляет взаимодействие популяции. Проводит селекцию, скрещивание и мутации.
 *
 * Использует {@link FitnessFunction} для рассчета приспособленности.
 */
public class Population {

    public int step = 0;

    private ArrayList<Vector> population;

    private final CrossingFunction crossingFunction;
    private final RecoveryFunction recoveryFunction;
    private final MutationFunction mutationFunction;
    private final SelectionFunction selectionFunction;
    private final ReplacementFunction replacementFunction;
    private final CoverCheckFunction coverCheckFunction;
    private final SetsMatrix setsMatrix;
    private final int populationGrowth;

    /**
     * Стандартный конструктор
     * @param setsMatrix матрица описывающая множества и их веса.
     * @param populationCount размер популяции.
     * @param populationGrowth определяет количество проводимых скрещиваний для каждого шага.
     * @param crossingFunction функция скрещивания.
     * @param recoveryFunction функция восстановления ответа, в случае, если получившийся индивид не является покрытием.
     * @param mutationFunction функция мутации.
     * @param createUnitFunction функция для создания начальной популяции.
     * @param selectionFunction функция для отбора пар для скрещивания.
     * @param replacementFunction функция смены популяции.
     * @param coverCheckFunction функция для проверки является ли инвидив покрытием.
     */
    public Population(SetsMatrix setsMatrix, int populationCount, int populationGrowth, CrossingFunction crossingFunction,
                      RecoveryFunction recoveryFunction, MutationFunction mutationFunction,
                      CreateUnitFunction createUnitFunction, SelectionFunction selectionFunction,
                      ReplacementFunction replacementFunction, CoverCheckFunction coverCheckFunction) {
        this.crossingFunction = crossingFunction;
        this.recoveryFunction = recoveryFunction;
        this.mutationFunction = mutationFunction;
        this.setsMatrix = setsMatrix;
        this.selectionFunction = selectionFunction;
        this.replacementFunction = replacementFunction;
        this.coverCheckFunction = coverCheckFunction;
        this.populationGrowth = populationGrowth;
        population = new ArrayList<>(populationCount);
        for (int i = 0; i < populationCount; i++) {
            Vector newUnit = createUnitFunction.createUnit(setsMatrix);
            newUnit = recoveryFunction.recover(newUnit, setsMatrix);
            int distance = newUnit.getVector().length;
            for (Vector unit : population) {
                for (int j = 0; j < newUnit.getVector().length; j++) {
                    if (unit.getVector()[j] == newUnit.getVector()[j]) {
                        distance--;
                    }
                }
            }
            if (distance == 0) {
                i--;
            }
            population.add(newUnit);
        }
    }



    /**
     * Проводит следующий шаг.
     * Применяет операцию селекции. На выбранных потомках используется функция скрещивания. Проводятся мутации и
     * смена популяции.
     */
    public void nextStep() {
        step++;
        ArrayList<Parents> parentsArray = selectionFunction.select(population, setsMatrix,
                populationGrowth / crossingFunction.getChildrenCount());
        PopulationPattern populationPattern = PopulationPatternCalculator.calculate(population);
        ArrayList<Vector> newPop = new ArrayList<>();
        for (Parents parents : parentsArray) {
            ArrayList<Vector> children = crossingFunction.cross(parents, population, setsMatrix, populationPattern);
            for (Vector newUnit : children) {
                if (coverCheckFunction.checkCover(newUnit, setsMatrix)) {
                    newUnit = recoveryFunction.recover(newUnit, setsMatrix);
                }
                newUnit = mutationFunction.mutate(newUnit, setsMatrix, populationPattern);
                newUnit = recoveryFunction.recover(newUnit, setsMatrix);
                newPop.add(newUnit);
            }
        }
        for (Vector unit : newPop) {
            replacementFunction.replace(population, unit);
        }
    }

    /**
     * @return возвращает наилучшего индивида в популяции по параметру приспособлености.
     */
    public Vector getTheBestResult() {
        return population.stream().min((o1, o2) -> o1.getFitness() == o2.getFitness() ? 0 :
                o1.getFitness() > o2.getFitness() ? 1 : -1).get();
    }
}
