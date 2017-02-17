package ru.ifmo.mudry.coverproblem.tests;

import ru.ifmo.mudry.coverproblem.bruteforcealgorithm.BruteforceAlgorithm;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.Population;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.StandardImplementation;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.createunit.RandomCreateUnitsFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.crossing.StandardOnePointCrossOver;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.mutation.OnePointMutation;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.recovery.SimpleGreedyRecover;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.replacement.ReplaceOnesWhosWorse;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.selection.HalfTournamentSelection;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class Test {
    static final int uniqueTestCount = 10;
    static final int testPerUnique = 10;

    static final int[] smallTestSize = {5, 10, 15, 20};
    static final int[] smallTestPopulationSize = {5, 10, 15};
    static final int[] smallTestPopulationGrowth = {4, 8, 12};

    static final int[] bigTestSize = {100, 150, 200};
    static final int[] bigTestPopulationSize = {30, 60, 100};
    static final int[] bigTestPopulationGrowth = {20, 40, 80};

    static String[] margin = {"", "    ", "        ", "            ", "                ", "                    ",
            "                        "};

    static BufferedWriter logWriter;
    static BufferedWriter resultWriter;


    public static void main(String[] args) throws IOException {
        {
            //first tests
            GeneticFunctions functions = new GeneticFunctions(new RandomCreateUnitsFunction(),
                    new StandardOnePointCrossOver(),
                    new OnePointMutation(),
                    new SimpleGreedyRecover(),
                    new ReplaceOnesWhosWorse(),
                    new HalfTournamentSelection());
            test("Test", "Standard Functions", functions);
        }
    }

    static boolean check(SetsMatrix matrix) {
        boolean[] vector = new boolean[matrix.setsCount];
        Arrays.fill(vector, true);
        Set<Integer> coverSet = new TreeSet<>();
        for (int i = 0; i < vector.length; i++) {
            if (vector[i]) {
                for (int j = 0; j < matrix.elementsCount; j++) {
                    if (matrix.matrix[i][j]) {
                        coverSet.add(j);
                    }
                }
            }
            if (coverSet.size() == matrix.elementsCount) {
                return true;
            }
        }
        return false;
    }

    static void writeLogLine(int lvl, String s) throws IOException {
        logWriter.write(margin[lvl] + s + "\n");
    }

    static void writeResultLine(int lvl, String s) throws IOException {
        resultWriter.write(margin[lvl] + s + "\n");
    }

    static void test(String testName, String testDescription, GeneticFunctions functions) throws IOException {
        logWriter = new BufferedWriter(new FileWriter(testName + ".log"));
        resultWriter = new BufferedWriter(new FileWriter(testName + ".result"));
        writeLogLine(0, testName);
        writeResultLine(0, testName);
        writeLogLine(0, testDescription);
        writeResultLine(0, testDescription);
        testAccurateOnSmallData(functions);
        testOnBigData(functions);
        logWriter.close();
        resultWriter.close();
    }

    static void testAccurateOnSmallData(GeneticFunctions functions) throws IOException {
        writeLogLine(0, "Test Accurate. Compare with Bruteforce Algorithm.");
        writeResultLine(0, "Test Accurate. Compare with Bruteforce Algorithm.");
        System.out.println("Test Accurate. Compare with Bruteforce Algorithm.");
        ArrayList<Double> deviation = new ArrayList<>();
        for (int size : smallTestSize) {
            for (int popSize : smallTestPopulationSize) {
                for (int popGrowth : smallTestPopulationGrowth) {
                    writeLogLine(1, "Size: " + size + ", Population size: " + popSize + ", Population growth: " +
                        popGrowth);
                    System.out.println("Size: " + size + ", Population size: " + popSize + ", Population growth: " +
                            popGrowth);
                    writeResultLine(1, "Size: " + size + ", Population size: " + popSize + ", Population growth: " +
                            popGrowth);
                    deviation.add(doSmallTest(functions, size, popSize, popGrowth));
                }
            }
        }
        writeResultLine(0, "Average deviation for this block: " + average(deviation));
        writeResultLine(0, "");
    }

    static double doSmallTest(GeneticFunctions functions, int size, int popSize, int popGrowth) throws IOException {

        double[] deviation = new double[uniqueTestCount];
        long[] aVtime = new long[uniqueTestCount];
        for (int i = 0; i < uniqueTestCount; i++) {
            writeLogLine(2, "" + (i + 1) + " test group.");
            SetsMatrix matrix = generateSets(size);
            BruteforceAlgorithm bruteforceAlgorithm = new BruteforceAlgorithm(matrix.matrix, matrix.cost);

            long beforeBr = System.currentTimeMillis();
            Vector vec = bruteforceAlgorithm.calculate();
            long afterBr = System.currentTimeMillis();
            writeLogLine(3, "Bruteforce result: ");
            writeLogLine(4, "Total time: " + (afterBr - beforeBr));
            writeLogLine(4, "Cost: " + vec.getFitness());

            writeLogLine(3, "Genetic result: ");

            double[] costs = new double[testPerUnique];
            long[] time = new long[testPerUnique];
            for (int j = 0; j < testPerUnique; j++) {
                writeLogLine(4, "" + (j + 1) + " test.");
                StandardImplementation testGenetic = new StandardImplementation();
                long beforeGn = System.currentTimeMillis();
                Population population = testGenetic.calculate(matrix, popSize, popGrowth, 10, functions);
                long afterGn = System.currentTimeMillis();

                Vector vector = population.getTheBestResult();
                time[j] = afterGn - beforeGn;
                costs[j] = vector.getFitness();
                writeLogLine(5, "Total time: " + (afterGn - beforeGn));
                writeLogLine(5, "Cost: " + vector.getFitness());
            }
            aVtime[i] = average(time);
            deviation[i] = percentDeviation(vec.getFitness(), average(costs));
        }
        writeResultLine(2, "Average percent deviation: " + average(deviation));
        writeResultLine(2, "Average time: " + average(aVtime));
        return average(deviation);
    }

    static SetsMatrix generateSets(int size) {
        boolean[][] test = new boolean[size][size];
        double[] testCost = new double[size];

        while (true) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    test[i][j] = Math.random() < 0.5;
                }
            }

            for (int i = 0; i < size; i++) {
                testCost[i] = Math.random() * 100;
            }
            SetsMatrix newMatrix = new SetsMatrix(test, testCost);
            if (check(newMatrix)) {
                return new SetsMatrix(test, testCost);
            }
        }
    }

    static long average(long[] arr) {
        long sum = 0;
        for (long anArr : arr) {
            sum += anArr;
        }
        return (long) sum / arr.length;
    }

    static double average(double[] arr) {
        double sum = 0;
        for (double anArr : arr) {
            sum += anArr;
        }
        return sum / arr.length;
    }


    static double average(ArrayList<Double> arr) {
        double sum = 0;
        for (double anArr : arr) {
            sum += anArr;
        }
        return sum / arr.size();
    }

    static double percentDeviation(double standard, double value) {
        double deviation = (value - standard) / standard;
        if (deviation < 0.00001) {
            return 0;
        } else {
            return 100 * deviation;
        }
    }

    static void testOnBigData(GeneticFunctions functions) throws IOException {
        writeLogLine(0, "Test on big Data. Deviation between max and min value.");
        writeResultLine(0, "Test on big Data. Deviation between max and min value.");
        System.out.println("Test on big Data. Deviation between max and min value.");
        ArrayList<Double> deviation = new ArrayList<>();
        for (int size : bigTestSize) {
            for (int popSize : bigTestPopulationSize) {
                for (int popGrowth : bigTestPopulationGrowth) {
                    writeLogLine(1, "Size: " + size + ", Population size: " + popSize + ", Population growth: " +
                            popGrowth);
                    System.out.println("Size: " + size + ", Population size: " + popSize + ", Population growth: " +
                            popGrowth);
                    writeResultLine(1, "Size: " + size + ", Population size: " + popSize + ", Population growth: " +
                            popGrowth);
                    deviation.add(doBigTest(functions, size, popSize, popGrowth));
                }
            }
        }

        writeResultLine(0, "Average deviation for this block: " + average(deviation));
        writeResultLine(0, "");
    }

    static double doBigTest(GeneticFunctions functions, int size, int popSize, int popGrowth) throws IOException {
        double[] deviation = new double[uniqueTestCount];
        long[] aVtime = new long[uniqueTestCount];
        for (int i = 0; i < uniqueTestCount; i++) {
            writeLogLine(2, "" + (i + 1) + " test group.");
            SetsMatrix matrix = generateSets(size);

            double[] costs = new double[testPerUnique];
            long[] time = new long[testPerUnique];
            for (int j = 0; j < testPerUnique; j++) {
                writeLogLine(3, "" + (j + 1) + " test.");
                StandardImplementation testGenetic = new StandardImplementation();
                long beforeGn = System.currentTimeMillis();
                Population population = testGenetic.calculate(matrix, popSize, popGrowth, 10, functions);
                long afterGn = System.currentTimeMillis();

                Vector vector = population.getTheBestResult();
                time[j] = afterGn - beforeGn;
                costs[j] = vector.getFitness();
                writeLogLine(4, "Total time: " + (afterGn - beforeGn));
                writeLogLine(4, "Cost: " + vector.getFitness());
            }
            aVtime[i] = average(time);
            deviation[i] = percentDeviation(Arrays.stream(costs).min().getAsDouble(),
                    Arrays.stream(costs).max().getAsDouble());
        }
        writeResultLine(2, "Average percent deviation: " + average(deviation));
        writeResultLine(2, "Average time: " + average(aVtime));
        return average(deviation);
    }
}
