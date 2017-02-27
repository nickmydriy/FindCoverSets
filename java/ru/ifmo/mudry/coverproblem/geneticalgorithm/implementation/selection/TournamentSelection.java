package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.selection;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class TournamentSelection implements SelectionFunction {
    final int tournamentUnits;

    public TournamentSelection(int tournamentUnits) {
        this.tournamentUnits = tournamentUnits;
    }

    @Override
    public ArrayList<Parents> select(List<Vector> population, SetsMatrix matrix, int count) {
        ArrayList<Parents> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++){
            result.add(new Parents(select(population, tournamentUnits), select(population, tournamentUnits)));
        }
        return result;
    }

    protected Vector select(List<Vector> population, int count) {
        ArrayList<Vector> selection = new ArrayList<>(count);
        for (int j = 0; j < count; j++) {
            selection.add(population.get(Randomizer.random.nextInt(population.size())));
        }
        return selection.stream().min((o1, o2) ->
                o1.getFitness() == o2.getFitness() ? 0 : o1.getFitness() > o2.getFitness() ? 1 : -1).get();
    }
}
