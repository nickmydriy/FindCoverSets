package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.recovery;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.FitnessFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.*;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class NonRedundantGreedyRecovery extends SimpleGreedyRecovery {
    @Override
    public Vector recover(Vector unit, SetsMatrix matrix) {
        boolean[] vector = getRecovered(unit.getVector(), matrix);
        vector = makeNonRedundant(vector, matrix);
        return new Vector(vector, FitnessFunction.calculateFitness(vector, matrix.cost));
    }

    private boolean[] makeNonRedundant(boolean[] vector, SetsMatrix matrix) {
        int[] w = getSetsCountForElement(vector, matrix);
        ArrayList<Integer> cover = getCover(vector, matrix);
        sortSet(cover, matrix);
        for (int i : cover) {
            if (checkForRedundant(matrix.matrix[i], w)) {
                w = deleteSet(matrix.matrix[i], w);
                vector[i] = false;
            }
        }
        return vector;
    }

    private int[] deleteSet(boolean[] set, int[] w) {
        for (int i = 0; i < set.length; i++) {
            if (set[i]) {
                w[i]--;
            }
        }
        return w;
    }

    private boolean checkForRedundant(boolean[] set, int[] w) {
        for (int i = 0; i < set.length; i++) {
            if (set[i]) {
                if (w[i] <= 1) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void sortSet(ArrayList<Integer> cover, SetsMatrix matrix) {
        boolean[] coverVector = new boolean[matrix.elementsCount];
        Arrays.fill(coverVector, true);
        HashMap<Integer, Double> param = new HashMap<>();
        for (Integer cur : cover) {
            int power = setsIntersectionPower(coverVector, matrix.matrix[cur]);
            double p = matrix.cost[cur] / power;
            param.put(cur, p);
        }
        Comparator<Integer> comparator = (first, second) -> {
            double f = param.get(first), s = param.get(second);
            if (f > s) {
                return -1;
            }
            if (s > f) {
                return 1;
            }
            return 0;
        };
        Collections.sort(cover, comparator);
    }

    private ArrayList<Integer> getCover(boolean[] vector, SetsMatrix matrix) {
        ArrayList<Integer> cover = new ArrayList<>();
        for (int i = 0; i < vector.length; i++) {
            if (vector[i]) {
                cover.add(i);
            }
        }
        return cover;
    }

    private int[] getSetsCountForElement(boolean[] vector, SetsMatrix matrix) {
        int[] answer = new int[matrix.elementsCount];
        for (int i = 0; i < vector.length; i++) {
            if (vector[i]) {
                for (int j = 0; j < matrix.elementsCount; j++) {
                    if (matrix.matrix[i][j]) {
                        answer[j]++;
                    }
                }
            }
        }
        return answer;
    }

}
