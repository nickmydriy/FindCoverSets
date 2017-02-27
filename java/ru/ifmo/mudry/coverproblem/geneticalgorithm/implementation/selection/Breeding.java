package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.selection;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Parents;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SelectionFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public abstract class Breeding implements SelectionFunction {
    @Override
    public ArrayList<Parents> select(List<Vector> population, SetsMatrix matrix, int count) {
        ArrayList<Parents> parents = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int index = Randomizer.random.nextInt(population.size());
            Vector vec = getPair(population, population.get(index));
            parents.add(new Parents(population.get(index), vec));
        }
        return parents;
    }

    protected abstract Vector getPair(List<Vector> population, Vector vec);

    protected double distance(Vector a, Vector b) {
//        int distance = a.getVector().length;
//        for (int i = 0; i < a.getVector().length; i++) {
//            if (a.getVector()[i] == b.getVector()[i]) {
//                distance--;
//            }
//        }
        return Math.abs(a.getFitness() - b.getFitness());
    }
}
