package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.selection;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class HalfTournamentSelection implements SelectionFunction {

    @Override
    public ArrayList<Parents> select(List<Vector> population, SetsMatrix matrix, int count) {
        ArrayList<Parents> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++){
            int index1 = Randomizer.random.nextInt(population.size());
            int index2 = Randomizer.random.nextInt(population.size());
            double f1 = FitnessFunction.calculateFitness(population.get(index1).getVector(), matrix.cost);
            double f2 = FitnessFunction.calculateFitness(population.get(index2).getVector(), matrix.cost);

            int index3 = Randomizer.random.nextInt(population.size());

            if (f1 < f2) {
                result.add(new Parents(population.get(index1), population.get(index3)));
            } else {
                result.add(new Parents(population.get(index2), population.get(index3)));
            }
        }
        return result;
    }
}
