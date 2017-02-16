package ru.ifmo.mudry.coverproblem.geneticalgorithm;


import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.ArrayList;
import java.util.Set;

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

    public Population(int populationCount, ArrayList<Vector> population, CrossingFunction crossingFunction,
                      RecoveryFunction recoveryFunction, MutationFunction mutationFunction,
                      SetsMatrix setsMatrix, CreateUnitFunction createUnitFunction, SelectionFunction selectionFunction,
                      ReplacementFunction replacementFunction, CoverCheckFunction coverCheckFunction) {
        this.populationCount = populationCount;
        this.population = population;
        this.crossingFunction = crossingFunction;
        this.recoveryFunction = recoveryFunction;
        this.mutationFunction = mutationFunction;
        this.setsMatrix = setsMatrix;
        this.selectionFunction = selectionFunction;
        this.replacementFunction = replacementFunction;
        this.coverCheckFunction = coverCheckFunction;
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
        Parents parents = selectionFunction.select(population);
        Vector newUnit = crossingFunction.cross(parents.getFirstParent(), parents.getSecondParent());
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
