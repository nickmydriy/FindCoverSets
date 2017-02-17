package ru.ifmo.mudry.coverproblem.geneticalgorithm;


import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.ArrayList;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class Population {

    int populationCount;

    public int step = 0;

    public ArrayList<Vector> population;

    final CrossingFunction crossingFunction;
    final RecoveryFunction recoveryFunction;
    final MutationFunction mutationFunction;
    final SelectionFunction selectionFunction;
    final ReplacementFunction replacementFunction;
    final CoverCheckFunction coverCheckFunction;
    public final SetsMatrix setsMatrix;
    final int populationGrowth;

    public Population(SetsMatrix setsMatrix, int populationCount, int populationGrowth, CrossingFunction crossingFunction,
                      RecoveryFunction recoveryFunction, MutationFunction mutationFunction,
                      CreateUnitFunction createUnitFunction, SelectionFunction selectionFunction,
                      ReplacementFunction replacementFunction, CoverCheckFunction coverCheckFunction) {
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

    public Population(SetsMatrix setsMatrix, int populationCount, int populationGrowth, GeneticFunctions functions,
                      CoverCheckFunction coverCheckFunction) {
        this.populationCount = populationCount;
        this.crossingFunction = functions.crossingFunction;
        this.recoveryFunction = functions.recoveryFunction;
        this.mutationFunction = functions.mutationFunction;
        this.setsMatrix = setsMatrix;
        this.selectionFunction = functions.selectionFunction;
        this.replacementFunction = functions.replacementFunction;
        this.coverCheckFunction = coverCheckFunction;
        this.populationGrowth = populationGrowth;
        population = new ArrayList<>(populationCount);
        for (int i = 0; i < populationCount; i++) {
            Vector newUnit = functions.createUnitFunction.createUnit(setsMatrix);
            if (!coverCheckFunction.checkCover(newUnit, setsMatrix)) {
                newUnit = recoveryFunction.recover(newUnit, setsMatrix);
            }
            population.add(newUnit);
        }
    }

    public void nextStep() {
        step++;
        ArrayList<Parents> parentsArray = selectionFunction.select(population, setsMatrix, populationGrowth);
        for (Parents parents : parentsArray) {
            ArrayList<Vector> children = crossingFunction.cross(parents, population, setsMatrix);
            for (Vector newUnit : children) {
                if (!coverCheckFunction.checkCover(newUnit, setsMatrix)) {
                    newUnit = recoveryFunction.recover(newUnit, setsMatrix);
                }
                newUnit = mutationFunction.mutate(newUnit, setsMatrix);
                if (!coverCheckFunction.checkCover(newUnit, setsMatrix)) {
                    newUnit = recoveryFunction.recover(newUnit, setsMatrix);
                }
                replacementFunction.replace(population, newUnit);
            }
        }
    }

    public Vector getTheBestResult() {
        return population.stream().min((o1, o2) -> o1.getFitness() == o2.getFitness() ? 0 :
                o1.getFitness() > o2.getFitness() ? 1 : -1).get();
    }
}
