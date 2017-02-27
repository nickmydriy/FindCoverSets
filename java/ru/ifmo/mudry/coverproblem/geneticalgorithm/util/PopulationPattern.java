package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class PopulationPattern {
    public final double[] p0;
    public final double[] p1;
    public final double[] f;

    public PopulationPattern(double[] p0, double[] p1, double[] f) {
        this.p0 = p0;
        this.p1 = p1;
        this.f = f;
    }
}
