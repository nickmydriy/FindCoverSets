package ru.ifmo.mudry.coverproblem.geneticalgorithm;


import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class Population {

    int populationCount;

    int step = 0;

    ArrayList<Vector> population;

    final CrossingFunction crossingFunction;
    final RecoveryFunction recoveryFunction;
    final MutationFunction mutationFunction;
    final SelectionFunction selectionFunction;
    final ReplacementFunction replacementFunction;
    final CoverCheckFunction coverCheckFunction;
    final SetsMatrix setsMatrix;
    final int populationGrowth;

    public Population(int populationCount, CrossingFunction crossingFunction,
                      RecoveryFunction recoveryFunction, MutationFunction mutationFunction,
                      SetsMatrix setsMatrix, CreateUnitFunction createUnitFunction, SelectionFunction selectionFunction,
                      ReplacementFunction replacementFunction, CoverCheckFunction coverCheckFunction, int populationGrowth) {
        this.populationCount = populationCount;
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
            if (!coverCheckFunction.checkCover(newUnit, setsMatrix)) {
                newUnit = recoveryFunction.recover(newUnit, setsMatrix);
            }
            population.add(newUnit);
        }
    }

    public void nextStep() {
        ArrayList<Parents> parentsArray = selectionFunction.select(population, setsMatrix, populationGrowth);
        for (Parents parents : parentsArray) {
            Vector newUnit = crossingFunction.cross(parents, population, setsMatrix);
            if (!coverCheckFunction.checkCover(newUnit, setsMatrix)) {
                newUnit = recoveryFunction.recover(newUnit, setsMatrix);
            }
            newUnit = mutationFunction.mutate(newUnit);
            if (!coverCheckFunction.checkCover(newUnit, setsMatrix)) {
                newUnit = recoveryFunction.recover(newUnit, setsMatrix);
            }
            replacementFunction.replace(population, newUnit);
        }
    }

    public Vector getTheBestResult() {
        return population.stream().min((o1, o2) -> o1.getFitness() == o2.getFitness() ? 0 :
                o1.getFitness() > o2.getFitness() ? 1 : -1).get();
    }
}
