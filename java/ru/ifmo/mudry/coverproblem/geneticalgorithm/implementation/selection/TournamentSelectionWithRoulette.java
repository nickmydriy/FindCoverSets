package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.selection;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
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
public class TournamentSelectionWithRoulette extends TournamentSelection {

    public TournamentSelectionWithRoulette(int tournamentUnits) {
        super(tournamentUnits);
    }

    @Override
    protected Vector select(List<Vector> population, int count) {
        ArrayList<Vector> selection = new ArrayList<>(count);
        Roulette<Vector> roulette = new Roulette<>(population, vector -> 1 / vector.getFitness());
        for (int j = 0; j < count; j++) {
            selection.add(population.get(roulette.getIndex()));
        }
        return selection.stream().min((o1, o2) ->
                o1.getFitness() == o2.getFitness() ? 0 : o1.getFitness() > o2.getFitness() ? 1 : -1).get();
    }
}
