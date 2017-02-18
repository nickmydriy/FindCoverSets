package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Класс описывающий особь.
 */
public class Vector {
    final boolean[] vector;
    final double fitness;

    /**
     * Стандартный конструктор.
     *
     * @param vector генотип особи, где каждый бит i описывает входит ли множество i в покрытие или нет.
     * @param fitness приспособленность особи.
     */
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
