package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.GeneticAlgorithm;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.Population;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.CoverCheckFunctionImpl;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util.Randomizer;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.sql.Time;
import java.util.*;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class FirstImplementation implements GeneticAlgorithm {
    @Override
    public Population calculate(boolean[][] matrix, double[] cost) {
        SetsMatrix setsMatrix = new SetsMatrix(matrix, cost);
        Population population = new Population(10, new CrossingFunctionImpl(), new RecoveryFunctionImpl(),
                new MutationFunctionImpl(), setsMatrix, new CreateUnitFunctionImpl(), new SelectionFunctionImpl(),
                new ReplacementFunctionImpl(), new CoverCheckFunctionImpl(), 2);

        int stepsWithoutProgress = 0;

        int stepsWOPtoStop = 10;

        double currentProgress = population.getTheBestResult().getFitness();

        while (true) {
            population.nextStep();
            double stepProgress = population.getTheBestResult().getFitness();
            if (currentProgress == stepProgress) {
                stepsWithoutProgress++;
                if (stepsWithoutProgress >= stepsWOPtoStop) {
                    break;
                }
            } else {
                stepsWithoutProgress = 0;
            }
        }
        return population;
    }

    private class CreateUnitFunctionImpl implements CreateUnitFunction {
        @Override
        public Vector createUnit(SetsMatrix setsMatrix) {
            boolean[] vector = new boolean[setsMatrix.setsCount];
            for (int i = 0; i < vector.length; i++) {
                vector[i] = Math.random() < 0.5;
            }
            return new Vector(vector, FitnessFunction.calculateFitness(vector, setsMatrix.cost));
        }
    }

    private class MutationFunctionImpl implements MutationFunction {
        @Override
        public Vector mutate(Vector unit) {
            int pos = Math.abs(Randomizer.random.nextInt()) % unit.getVector().length;
            unit.getVector()[pos] = !unit.getVector()[pos];
            return unit;
        }
    }

    private class RecoveryFunctionImpl implements RecoveryFunction {

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

    private class ReplacementFunctionImpl implements ReplacementFunction {

        @Override
        public void replace(ArrayList<Vector> population, Vector newUnit) {
            ArrayList<Integer> candidates = new ArrayList<>(population.size());
            for (int i = 0; i < population.size(); i++) {
                if (population.get(i).getFitness() > newUnit.getFitness()) {
                    candidates.add(i);
                }
            }
            int pos = Randomizer.random.nextInt(candidates.size());
            population.set(pos, newUnit);
        }
    }

    private class SelectionFunctionImpl implements SelectionFunction {

        @Override
        public ArrayList<Parents> select(List<Vector> population, SetsMatrix matrix, int count) {
            ArrayList<Parents> result = new ArrayList<>(count);
            for (int i = 0; i < count; i++){
                int index1 = Randomizer.random.nextInt(population.size());
                int index2 = Randomizer.random.nextInt(population.size());
                double f1 = FitnessFunction.calculateFitness(population.get(index1).getVector(), matrix.cost);
                double f2 = FitnessFunction.calculateFitness(population.get(index2).getVector(), matrix.cost);

                int index3 = Randomizer.random.nextInt(population.size());

                if (f1 < f2) {
                    result.add(new Parents(population.get(index1), population.get(index3)));
                } else {
                    result.add(new Parents(population.get(index2), population.get(index3)));
                }
            }
            return result;
        }
    }

    private class CrossingFunctionImpl implements CrossingFunction {

        @Override
        public Vector cross(Parents parents, ArrayList<Vector> population, SetsMatrix matrix) {
            Vector first = parents.getFirstParent();
            Vector second = parents.getSecondParent();
            int pos = Math.abs(Randomizer.random.nextInt()) % first.getVector().length;
            boolean[] vector = new boolean[first.getVector().length];
            System.arraycopy(first.getVector(), 0, vector, 0, pos);
            System.arraycopy(second.getVector(), pos, vector, pos, first.getVector().length - pos);
            return new Vector(vector, FitnessFunction.calculateFitness(vector, matrix.cost));
        }
    }
}
