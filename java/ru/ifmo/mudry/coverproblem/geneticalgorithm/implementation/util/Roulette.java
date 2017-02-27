package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class Roulette<T> {

    final ArrayList<Pair<Integer, Double>> values;
    final double valuesSum;

    public Roulette(List<T> elements, Function<T, Double> valueProvider) {
        values = new ArrayList<>(elements.size());
        for (int i = 0; i < elements.size(); i++) {
            values.add(new Pair<>(i, valueProvider.apply(elements.get(i))));
        }
        valuesSum = elements.stream().mapToDouble(valueProvider::apply).sum();
    }

    public int getIndex() {
        double val = Randomizer.random.nextDouble() * valuesSum;
        double cur = 0;
        for (Pair<Integer, Double> v : values) {
            cur += v.getValue();
            if (cur > val) {
                return v.getKey();
            }
        }
        return values.get(values.size() - 1).getKey();
    }


}
