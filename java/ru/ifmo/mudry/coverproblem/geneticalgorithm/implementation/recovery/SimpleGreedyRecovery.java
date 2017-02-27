package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.recovery;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.FitnessFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.RecoveryFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.*;

/**
 * Простой жадный алгоритм восстановления покрытия.
 * Принцип: 1. Составим множество M, которое будет сотсоять из тех элементов, которых нет в покрытии нашего индивида.
 *          2. На каждом следующем шаге будем добавлять к индивиду множество T, которое не включает наш индивид, такое что
 *          стоимость множества T разделенная на мощьность обьединения множеств T и М минимальна.
 *          3. Процесс заканивается, когда все элементы М покрыты.
 */
public class SimpleGreedyRecovery implements RecoveryFunction {

    @Override
    public Vector recover(Vector unit, SetsMatrix matrix) {
        boolean[] vector = getRecovered(unit.getVector(), matrix);
        return new Vector(vector, FitnessFunction.calculateFitness(vector, matrix.cost));
    }

    protected boolean[] getRecovered(boolean[] vector, SetsMatrix matrix) {
        while (true) {
            boolean[] nonCovered = getNonCoveredVertices(vector, matrix);
            ArrayList<Integer> nonUsedSets = getNonUsedSets(vector);
            vector[getMin(nonUsedSets, nonCovered, matrix)] = true;
            if (check(vector, matrix)) {
                break;
            }
        }
        return vector;
    }

    protected boolean check(boolean[] unit, SetsMatrix matrix) {
        boolean[] nonCovered = getNonCoveredVertices(unit, matrix);
        for (int i = 0; i < matrix.elementsCount; i++) {
            if (nonCovered[i]) {
                return false;
            }
        }
        return true;
    }

    protected boolean[] getNonCoveredVertices(boolean[] unit, SetsMatrix matrix) {
        boolean[] nonCovered = new boolean[matrix.elementsCount];
        boolean[] covered = new boolean[matrix.elementsCount];
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
        for (int i = 0; i < matrix.elementsCount; i++) {
            nonCovered[i] = !covered[i];
        }
        return nonCovered;
    }

    protected ArrayList<Integer> getNonUsedSets(boolean[] unit) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < unit.length; i++) {
            if (!unit[i]) {
                array.add(i);
            }
        }
        return array;
    }

    protected int getMin(ArrayList<Integer> array, boolean[] nonCovered, SetsMatrix matrix) {
        HashMap<Integer, Double> param = new HashMap<>();
        for (Integer cur : array) {
            int power = setsIntersectionPower(nonCovered, matrix.matrix[cur]);
            double p = matrix.cost[cur] / power;
            param.put(cur, p);
        }
        Comparator<Integer> comparator = (first, second) -> {
            double f = param.get(first), s = param.get(second);
            if (f > s) {
                return 1;
            }
            if (s > f) {
                return -1;
            }
            return 0;
        };
        return array.stream().min(comparator).get();
    }

    protected int setsIntersectionPower(boolean[] a, boolean[] b) {
        int power = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] && a[i] == b[i]) {
                power++;
            }
        }
        return power;
    }
}
