package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class Vector {
    final boolean[] vector;
    final double fitness;

    public Vector(boolean[] vector, double fitness) {
        this.vector = vector;
        this.fitness = fitness;
    }

    public boolean[] getVector() {
        return vector;
    }

    public double getFitness() {
        return fitness;
    }
}
