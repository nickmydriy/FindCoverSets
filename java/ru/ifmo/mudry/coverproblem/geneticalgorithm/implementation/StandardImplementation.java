package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.GeneticAlgorithm;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.Population;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.StandardCoverCheck;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

/**
 * Реализация генетического алгоритма.
 * Использует {@link Population}.
 * Вызывает {@link Population#nextStep()} до тех пор пока в течении нескольких шагов есть улучшение результата.
 */
public class StandardImplementation implements GeneticAlgorithm {

    @Override
    public Population calculate(boolean[][] matrix, double[] cost, int populationSize, int populationGrowth,
                                int stepsToStop, CreateUnitFunction createUnitFunction,
                                CrossingFunction crossingFunction, MutationFunction mutationFunction,
                                RecoveryFunction recoveryFunction, ReplacementFunction replacementFunction,
                                SelectionFunction selectionFunction) {
        SetsMatrix setsMatrix = new SetsMatrix(matrix, cost);
        Population population = new Population(setsMatrix, populationSize, 30, crossingFunction, recoveryFunction,
                mutationFunction, createUnitFunction, selectionFunction, replacementFunction, new StandardCoverCheck());

        int stepsWithoutProgress = 0;

        double currentProgress = population.getTheBestResult().getFitness();

        while (true) {
            //System.out.println(population.step + " " + currentProgress);
            population.nextStep();
            double stepProgress = population.getTheBestResult().getFitness();
            if (currentProgress == stepProgress) {
                stepsWithoutProgress++;
                if (stepsWithoutProgress >= stepsToStop) {
                    break;
                }
            } else {
                currentProgress = stepProgress;
                stepsWithoutProgress = 0;
            }
        }
        return population;
    }

    @Override
    public Population calculate(SetsMatrix matrix, int populationSize, int populationGrowth,
                                int stepsToStop, GeneticFunctions functions) {
        return calculate(matrix.matrix, matrix.cost, populationSize, populationGrowth, stepsToStop, functions.createUnitFunction,
                functions.crossingFunction, functions.mutationFunction, functions.recoveryFunction,
                functions.replacementFunction, functions.selectionFunction);
    }
}
