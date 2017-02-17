package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class GeneticFunctions {
    public final CreateUnitFunction createUnitFunction;
    public final CrossingFunction crossingFunction;
    public final MutationFunction mutationFunction;
    public final RecoveryFunction recoveryFunction;
    public final ReplacementFunction replacementFunction;
    public final SelectionFunction selectionFunction;

    public GeneticFunctions(CreateUnitFunction createUnitFunction, CrossingFunction crossingFunction,
                            MutationFunction mutationFunction, RecoveryFunction recoveryFunction,
                            ReplacementFunction replacementFunction, SelectionFunction selectionFunction) {
        this.createUnitFunction = createUnitFunction;
        this.crossingFunction = crossingFunction;
        this.mutationFunction = mutationFunction;
        this.recoveryFunction = recoveryFunction;
        this.replacementFunction = replacementFunction;
        this.selectionFunction = selectionFunction;
    }
}
