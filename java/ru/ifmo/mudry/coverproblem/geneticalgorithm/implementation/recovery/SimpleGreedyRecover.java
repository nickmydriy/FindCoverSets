package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.recovery;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.FitnessFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.RecoveryFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Простой жадный алгоритм восстановления покрытия.
 * Принцип: 1. Составим множество M, которое будет сотсоять из тех элементов, которых нет в покрытии нашего индивида.
 *          2. На каждом следующем шаге будем добавлять к индивиду множество T, которое не включает наш индивид, такое что
 *          стоимость множества T разделенная на мощьность обьединения множеств T и М минимальна.
 *          3. Процесс заканивается, когда все элементы М покрыты.
 */
public class SimpleGreedyRecover implements RecoveryFunction {

    @Override
    public Vector recover(Vector unit, SetsMatrix matrix) {
        boolean[] answer = unit.getVector();
        while (true) {
            boolean[] nonCovered = getNonCoveredVertices(answer, matrix);
            ArrayList<Integer> nonUsedSets = getNonUsedSets(answer);
            sortNonUsedSets(nonUsedSets, nonCovered, matrix);
            answer[nonUsedSets.get(0)] = true;
            if (check(answer, matrix)) {
                break;
            }
        }
        return new Vector(answer, FitnessFunction.calculateFitness(answer, matrix.cost));
    }

    private boolean check(boolean[] unit, SetsMatrix matrix) {
        boolean[] nonCovered = getNonCoveredVertices(unit, matrix);
        for (int i = 0; i < unit.length; i++) {
            if (nonCovered[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean[] getNonCoveredVertices(boolean[] unit, SetsMatrix matrix) {
        boolean[] nonCovered = new boolean[unit.length];
        boolean[] covered = new boolean[unit.length];
        Arrays.fill(covered, false);
        for (int i = 0; i < unit.length; i++) {
            if (unit[i]) {
                for (int j = 0; j < matrix.elementsCount; j++) {
                    if (matrix.matrix[i][j]) {
                        covered[j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < unit.length; i++) {
            nonCovered[i] = !covered[i];
        }
        return nonCovered;
    }

    private ArrayList<Integer> getNonUsedSets(boolean[] unit) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < unit.length; i++) {
            if (!unit[i]) {
                array.add(i);
            }
        }
        return array;
    }

    private void sortNonUsedSets(ArrayList<Integer> array, boolean[] nonCovered, SetsMatrix matrix) {
        HashMap<Integer, Double> param = new HashMap<>();
        for (Integer cur : array) {
            int power = setsIntersectionPower(nonCovered, matrix.matrix[cur]);
            double p = matrix.cost[cur] / power;
            param.put(cur, p);
        }
        Collections.sort(array, (first, second) -> {
            double f = param.get(first), s = param.get(second);
            if (f > s) {
                return 1;
            }
            if (s > f) {
                return -1;
            }
            return 0;
        });
    }

    private int setsIntersectionPower(boolean[] a, boolean[] b) {
        int power = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] && a[i] == b[i]) {
                power++;
            }
        }
        return power;
    }
}
