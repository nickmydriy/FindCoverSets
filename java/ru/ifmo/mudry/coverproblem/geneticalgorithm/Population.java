package ru.ifmo.mudry.coverproblem.geneticalgorithm;


import javafx.util.Pair;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.ArrayList;

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
    final SetsMatrix setsMatrix;

    public Population(int populationCount, ArrayList<Vector> population, CrossingFunction crossingFunction,
                      RecoveryFunction recoveryFunction, MutationFunction mutationFunction,
                      SetsMatrix setsMatrix, CreateUnitFunction createUnitFunction, SelectionFunction selectionFunction,
                      ReplacementFunction replacementFunction) {
        this.populationCount = populationCount;
        this.population = population;
        this.crossingFunction = crossingFunction;
        this.recoveryFunction = recoveryFunction;
        this.mutationFunction = mutationFunction;
        this.setsMatrix = setsMatrix;
        this.selectionFunction = selectionFunction;
        this.replacementFunction = replacementFunction;
        population = new ArrayList<>(populationCount);
        for (int i = 0; i < populationCount; i++) {
            Vector newUnit = createUnitFunction.createUnit(setsMatrix);
            if (!checkCover(newUnit)) {
                newUnit = recoveryFunction.recover(newUnit, setsMatrix);
            }
            population.add(newUnit);
        }
    }

    public void nextStep() {
        Parents parents = selectionFunction.select(population);
        Vector newUnit = crossingFunction.cross(parents.getFirstParent(), parents.getSecondParent());
        if (!checkCover(newUnit)) {
            newUnit = recoveryFunction.recover(newUnit, setsMatrix);
        }
        newUnit = mutationFunction.mutate(newUnit);
        if (!checkCover(newUnit)) {
            newUnit = recoveryFunction.recover(newUnit, setsMatrix);
        }
        replacementFunction.replace(population, newUnit);
    }

    private boolean checkCover(Vector vector) {
        return true;
    }
}
