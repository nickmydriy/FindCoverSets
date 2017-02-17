package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.GeneticAlgorithm;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.Population;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.createunit.RandomCreateUnitsFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.crossing.StandardOnePointCrossOver;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.mutation.OnePointMutation;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.recovery.SimpleGreedyRecover;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.replacement.ReplaceOnesWhosWorse;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.selection.HalfTournamentSelection;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.StandardCoverCheck;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class StandardImplementation implements GeneticAlgorithm {
    @Override
    public Population oldCalculate(boolean[][] matrix, double[] cost) {
        SetsMatrix setsMatrix = new SetsMatrix(matrix, cost);
        Population population = new Population(setsMatrix, 100, 30, new StandardOnePointCrossOver(), new SimpleGreedyRecover(),
                new OnePointMutation(), new RandomCreateUnitsFunction(), new HalfTournamentSelection(),
                new ReplaceOnesWhosWorse(), new StandardCoverCheck());

        int stepsWithoutProgress = 0;

        int stepsWOPtoStop = 15;

        double currentProgress = population.getTheBestResult().getFitness();

        while (true) {
            System.out.println(population.step + " " + currentProgress);
            population.nextStep();
            double stepProgress = population.getTheBestResult().getFitness();
            if (currentProgress == stepProgress) {
                stepsWithoutProgress++;
                if (stepsWithoutProgress >= stepsWOPtoStop) {
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
