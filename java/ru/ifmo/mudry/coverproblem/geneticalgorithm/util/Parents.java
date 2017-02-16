package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class Parents {
    final Vector firstParent, secondParent;

    public Parents(Vector firstParent, Vector secondParent) {
        this.firstParent = firstParent;
        this.secondParent = secondParent;
    }

    public Vector getFirstParent() {
        return firstParent;
    }

    public Vector getSecondParent() {
        return secondParent;
    }
}
