package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Пара состоящая из двух {@link Vector}. Не более.
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
