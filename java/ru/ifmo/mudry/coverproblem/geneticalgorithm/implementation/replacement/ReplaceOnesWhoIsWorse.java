package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.replacement;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.ReplacementFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.ArrayList;

/**
 * Простая функция смены популяции.
 * Принцип: 1. берем всех индивидов из популяции, для которых результат
*                  {@link ru.ifmo.mudry.coverproblem.geneticalgorithm.util.FitnessFunction} больше чем для нового индивида.
*           2. Если таких нет, то ничего не делаем, популяция остается неизменной.
 *           Если есть, то заменяем случайного индивида нашим новым.
 */
public class ReplaceOnesWhoIsWorse implements ReplacementFunction {
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
