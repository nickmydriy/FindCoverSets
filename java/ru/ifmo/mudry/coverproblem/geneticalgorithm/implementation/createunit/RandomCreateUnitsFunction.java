package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.createunit;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

/**
 * Функция для создания индивида путем случайного выбора генов.
 */
public class RandomCreateUnitsFunction implements CreateUnitFunction {
    final double rate;
    final RecoveryFunction recoveryFunction;

    public RandomCreateUnitsFunction(double rate, RecoveryFunction recoveryFunction) {
        this.rate = rate;
        this.recoveryFunction = recoveryFunction;
    }

    @Override
    public Vector createUnit(SetsMatrix setsMatrix) {
        boolean[] vector = new boolean[setsMatrix.setsCount];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = Math.random() < 0.5;
        }
        if (recoveryFunction != null) {
            return recoveryFunction.recover(
                    new Vector(vector, FitnessFunction.calculateFitness(vector, setsMatrix.cost)), setsMatrix);
        }
        return new Vector(vector, FitnessFunction.calculateFitness(vector, setsMatrix.cost));
    }
}
