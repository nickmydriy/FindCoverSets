package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.selection;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Roulette;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Parents;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SelectionFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class RouletteSelection implements SelectionFunction {
    @Override
    public ArrayList<Parents> select(List<Vector> population, SetsMatrix matrix, int count) {
        ArrayList<Parents> result = new ArrayList<>(count);
        Roulette<Vector> roulette = new Roulette<>(population, vector -> 1 / vector.getFitness());
        for (int i = 0; i < count; i++){
            int index1 = roulette.getIndex();
            int index2 = roulette.getIndex();
            result.add(new Parents(population.get(index1), population.get(index2)));
        }
        return result;
    }
}
