package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.replacement;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.ReplacementFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.ArrayList;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class AverageReplace implements ReplacementFunction {
    @Override
    public void replace(ArrayList<Vector> population, Vector newUnit) {
        for (Vector unit : population) {
            int distance = newUnit.getVector().length;
            for (int j = 0; j < newUnit.getVector().length; j++) {
                if (unit.getVector()[j] == newUnit.getVector()[j]) {
                    distance--;
                }
            }
            if (distance == 0) {
                return;
            }
        }
        ArrayList<Integer> candidates = new ArrayList<>(population.size());
        Double average = population.stream().mapToDouble(Vector::getFitness).average().getAsDouble();
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).getFitness() > average) {
                candidates.add(i);
            }
        }
        if (candidates.isEmpty()) {
            return;
        }
        int pos = Randomizer.random.nextInt(candidates.size());
        population.set(candidates.get(pos), newUnit);
    }
}
