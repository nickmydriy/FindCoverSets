package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.createunit;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.CreateUnitFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.FitnessFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

/**
 * Функция для создания индивида путем случайного выбора генов.
 */
public class RandomCreateUnitsFunction implements CreateUnitFunction {
    @Override
    public Vector createUnit(SetsMatrix setsMatrix) {
        boolean[] vector = new boolean[setsMatrix.setsCount];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = Math.random() < 0.5;
        }
        return new Vector(vector, FitnessFunction.calculateFitness(vector, setsMatrix.cost));
    }
}
