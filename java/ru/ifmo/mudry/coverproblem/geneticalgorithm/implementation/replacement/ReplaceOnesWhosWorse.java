package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.replacement;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.ReplacementFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.ArrayList;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class ReplaceOnesWhosWorse implements ReplacementFunction {
    @Override
    public void replace(ArrayList<Vector> population, Vector newUnit) {
        ArrayList<Integer> candidates = new ArrayList<>(population.size());
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).getFitness() > newUnit.getFitness()) {
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
