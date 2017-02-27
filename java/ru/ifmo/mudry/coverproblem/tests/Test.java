package ru.ifmo.mudry.coverproblem.tests;

import javafx.util.Pair;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.Population;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.StandardImplementation;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.createunit.RandomCreateUnitsFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.crossing.OnePointCrossOver;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.crossing.RegularCrossOver;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.crossing.RelativeCrossOver;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.mutation.OnePointMutation;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.mutation.RegularMutation;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.mutation.RelativeMutation;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.recovery.NonRedundantGreedyRecovery;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.recovery.SimpleGreedyRecovery;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.replacement.AverageReplace;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.replacement.ReplaceOnesWhoIsWorse;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.selection.*;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.*;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.io.*;
import java.util.*;

/**
 * Тесты для задачи
 */
public class Test {
    static final int uniqueTestCount = 10; //количество генерируемых тестов
    static final int testPerUnique = 100; //количество проходов каждого теста

    static final int smallTestSize = 25;
    static final int smallTestPopulationSize = 40;
    static final int smallTestPopulationGrowth = 8;

    static final int bigTestSize = 300;
    static final int bigTestPopulationSize = 100;
    static final int bigTestPopulationGrowth = 10;

    static final int orLibTestCount = 100;

    static final int steps = 100;

    static String[] margin = {"", "    ", "        ", "            ", "                ", "                    ",
            "                        "};

    static BufferedWriter logWriter;
    static BufferedWriter resultWriter;


    public static void main(String[] args) throws IOException {

        {
            GeneticFunctions functions = new GeneticFunctions(
                    new RandomCreateUnitsFunction(0.5, null),
                    new OnePointCrossOver(),
                    new OnePointMutation(),
                    new SimpleGreedyRecovery(),
                    new ReplaceOnesWhoIsWorse(),
                    new RouletteSelection());
            test("1", "Bad functions", functions);
        }
        {
            GeneticFunctions functions = new GeneticFunctions(
                    new RandomCreateUnitsFunction(0.5, null),
                    new RegularCrossOver(0.5),
                    new RegularMutation(0.02),
                    new SimpleGreedyRecovery(),
                    new ReplaceOnesWhoIsWorse(),
                    new RouletteSelection());
            test("2", "Mask functions", functions);
        }
        {
            GeneticFunctions functions = new GeneticFunctions(
                    new RandomCreateUnitsFunction(0.5, null),
                    new RegularCrossOver(0.5),
                    new RegularMutation(0.02),
                    new NonRedundantGreedyRecovery(),
                    new ReplaceOnesWhoIsWorse(),
                    new TournamentSelection(2));
            test("3", "NonRedundantRecovery", functions);
        }
        {
            GeneticFunctions functions = new GeneticFunctions(
                    new RandomCreateUnitsFunction(0.5, null),
                    new RegularCrossOver(0.5),
                    new RegularMutation(0.02),
                    new NonRedundantGreedyRecovery(),
                    new AverageReplace(),
                    new TournamentSelection(2));
            test("4", "NonRedundantRecovery", functions);
        }
        {
            GeneticFunctions functions = new GeneticFunctions(
                    new RandomCreateUnitsFunction(0.5, null),
                    new RegularCrossOver(0.5),
                    new RelativeMutation(),
                    new NonRedundantGreedyRecovery(),
                    new AverageReplace(),
                    new TournamentSelection(2));
            test("5", "Relative mutation, mask crossover", functions);
        }
        {
            GeneticFunctions functions = new GeneticFunctions(
                    new RandomCreateUnitsFunction(0.5, null),
                    new RelativeCrossOver(),
                    new RelativeMutation(),
                    new NonRedundantGreedyRecovery(),
                    new AverageReplace(),
                    new TournamentSelection(2));
            test("6", "Relative functions", functions);
        }
    }

    static void writeLogLine(int lvl, String s) throws IOException {
        logWriter.write(margin[lvl] + s + "\n");
    }

    static void writeResultLine(int lvl, String s) throws IOException {
        resultWriter.write(margin[lvl] + s + "\n");
    }

    static void test(String testName, String testDescription, GeneticFunctions functions) throws IOException {
        new File("log").mkdir();
        new File("res").mkdir();
        logWriter = new BufferedWriter(new FileWriter("log" + File.separator + testName + ".log"));
        resultWriter = new BufferedWriter(new FileWriter("res" + File.separator + testName + ".result"));
        writeLogLine(0, testName);
        writeResultLine(0, testName);
        writeLogLine(0, testDescription);
        writeResultLine(0, testDescription);
        testAccurateOnSmallData(functions);
//        testOnBigData(functions);
//        testOrLib(functions);
        logWriter.close();
        resultWriter.close();
    }

    static void testAccurateOnSmallData(GeneticFunctions functions) throws IOException {
        writeLogLine(0, "Test Accurate. Compare with Bruteforce Algorithm.");
        writeResultLine(0, "Test Accurate. Compare with Bruteforce Algorithm.");
        System.out.println("Test Accurate. Compare with Bruteforce Algorithm.");
        System.out.println("Size: " + smallTestSize);
        writeLogLine(1, "Size: " + smallTestSize);
        writeResultLine(1, "Size: " + smallTestSize);
        doSmallTest(functions, smallTestSize, smallTestPopulationSize, smallTestPopulationGrowth);
        writeResultLine(0, "");
    }

    static double doSmallTest(GeneticFunctions functions, int size, int popSize, int popGrowth) throws IOException {
        double[] deviation = new double[uniqueTestCount];
        long[] aVtime = new long[uniqueTestCount];
        double[] accurate = new double[uniqueTestCount];
        for (int i = 0; i < uniqueTestCount; i++) {
            writeLogLine(2, "" + (i + 1) + " test group.");
            writeResultLine(2, "" + (i + 1) + " Test.");
            System.out.println("" + (i + 1) + " Test.");
            Pair<SetsMatrix, Double> test = TestReader.getTest(true, i + 1);
            SetsMatrix matrix = test.getKey();
            double answer = test.getValue();
            double[] costs = new double[testPerUnique];
            long[] time = new long[testPerUnique];
            boolean[] acc = new boolean[testPerUnique];
            for (int j = 0; j < testPerUnique; j++) {
                System.out.print((j + 1) + " ");
                writeLogLine(3, "" + (j + 1) + " test.");
                StandardImplementation testGenetic = new StandardImplementation();
                long beforeGn = System.currentTimeMillis();
                Population population = testGenetic.calculate(matrix, popSize, popGrowth, steps, functions);
                long afterGn = System.currentTimeMillis();

                Vector vector = population.getTheBestResult();
                time[j] = afterGn - beforeGn;
                costs[j] = vector.getFitness();
                acc[j] = (vector.getFitness()) == test.getValue();
                writeLogLine(4, "Total time: " + (afterGn - beforeGn));
                writeLogLine(4, "Cost: " + vector.getFitness());
            }
            System.out.println();
            String c = String.format("Value: %.3f. Min: %.3f. Max: %.3f. Average %.3f.", answer, Arrays.stream(costs).min().getAsDouble(),
                    Arrays.stream(costs).max().getAsDouble(), average(costs));
            writeResultLine(3, c);
            writeResultLine(2, "Accurate: " + getPercent(acc));
            aVtime[i] = average(time);
            deviation[i] = percentDeviation(answer, average(costs));
            accurate[i] = getPercent(acc);
        }
        writeResultLine(0, "Average percent deviation: " + average(deviation));
        writeResultLine(0, "Average accurate: " + average(accurate));
        writeResultLine(0, "Average time: " + average(aVtime));
        return average(deviation);
    }

    static long average(long[] arr) {
        return (long)Arrays.stream(arr).average().getAsDouble();
    }

    static double average(double[] arr) {
        return Arrays.stream(arr).average().getAsDouble();
    }

    static double getPercent(boolean[] arr) {
        int r = 0;
        for (boolean anArr : arr) {
            if (anArr) {
                r++;
            }
        }
        return (double)r / arr.length * 100;
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
        System.out.println("Size: " + bigTestSize);
        writeLogLine(1, "Size: " + bigTestSize);
        writeResultLine(1, "Size: " + bigTestSize);
        doBigTest(functions, bigTestSize, bigTestPopulationSize, bigTestPopulationGrowth);
        writeResultLine(0, "");
    }

    static double doBigTest(GeneticFunctions functions, int size, int popSize, int popGrowth) throws IOException {
        double[] deviation = new double[uniqueTestCount];
        long[] aVtime = new long[uniqueTestCount];
        for (int i = 0; i < uniqueTestCount; i++) {
            writeLogLine(2, "" + (i + 1) + " test group.");
            writeResultLine(2, "" + (i + 1) + " Test.");
            System.out.println("" + (i + 1) + " Test.");
            SetsMatrix matrix = TestReader.getTest(false, i + 1).getKey();

            double[] costs = new double[testPerUnique];
            long[] time = new long[testPerUnique];
            for (int j = 0; j < testPerUnique; j++) {
                writeLogLine(3, "" + (j + 1) + " test.");
                System.out.print((j + 1) + " ");
                StandardImplementation testGenetic = new StandardImplementation();
                long beforeGn = System.currentTimeMillis();
                Population population = testGenetic.calculate(matrix, popSize, popGrowth, steps, functions);
                long afterGn = System.currentTimeMillis();

                Vector vector = population.getTheBestResult();
                time[j] = afterGn - beforeGn;
                costs[j] = vector.getFitness();
                writeLogLine(4, "Total time: " + (afterGn - beforeGn));
                writeLogLine(4, "Cost: " + vector.getFitness());
            }
            System.out.println();
            String c = String.format("Min: %.3f. Max: %.3f. Average %.3f.", Arrays.stream(costs).min().getAsDouble(),
                    Arrays.stream(costs).max().getAsDouble(), average(costs));
            writeResultLine(3, c);
            aVtime[i] = average(time);
            deviation[i] = percentDeviation(Arrays.stream(costs).min().getAsDouble(),
                    Arrays.stream(costs).max().getAsDouble());
        }
        writeResultLine(0, "Average percent deviation: " + average(deviation));
        writeResultLine(0, "Average time: " + average(aVtime));
        return average(deviation);
    }

    static void testOrLib(GeneticFunctions functions) throws IOException {
        writeLogLine(0, "Test OR Library.");
        writeResultLine(0, "Test OR Library.");
        System.out.println("Test OR Library.");

        doTestOrLib(functions, bigTestPopulationSize, bigTestPopulationGrowth);
        writeResultLine(0, "");
    }

    static void doTestOrLib(GeneticFunctions functions, int popSize, int popGrowth) throws IOException {
        double[] deviation = new double[9];
        double[] accurate = new double[9];
        for (int i = 0; i < 9; i++) {
            System.out.println("" + (i + 1) + " Test.");
            writeLogLine(1, "" + (i + 1) + " test group.");
            writeResultLine(1, "" + (i + 1) + " Test.");
            Pair<SetsMatrix, Integer> test = TestReader.getOrLibrarySets(i + 1);
            SetsMatrix matrix = test.getKey();
            double[] costs = new double[orLibTestCount];
            double[] time = new double[orLibTestCount];
            boolean[] acc = new boolean[orLibTestCount];
            for (int j = 0; j < orLibTestCount; j++) {
                writeLogLine(2, "" + (j + 1) + " test.");
                System.out.print((j + 1) + " ");
                StandardImplementation testGenetic = new StandardImplementation();
                long beforeGn = System.currentTimeMillis();
                Population population = testGenetic.calculate(matrix, popSize, popGrowth, steps, functions);
                long afterGn = System.currentTimeMillis();

                Vector vector = population.getTheBestResult();
                costs[j] = vector.getFitness();
                time[j] = afterGn - beforeGn;
                acc[j] = ((int)vector.getFitness()) == test.getValue();
                writeLogLine(3, "Total time: " + (afterGn - beforeGn));
                writeLogLine(3, "Cost: " + vector.getFitness());
                writeLogLine(3, "Steps: " + (population.step - steps));
            }
            System.out.println();
            String c = String.format("Value: %d. Min: %.3f. Max: %.3f. Average %.3f.", test.getValue(),
                    Arrays.stream(costs).min().getAsDouble(), Arrays.stream(costs).max().getAsDouble(), average(costs));
            writeResultLine(2, c);
            writeResultLine(2, "Accurate: " + getPercent(acc));
            writeResultLine(2, "Average time: " + average(time));
            deviation[i] = percentDeviation(test.getValue(), average(costs));
            accurate[i] = getPercent(acc);
        }
        writeResultLine(0, "Average percent deviation: " + average(deviation));
        writeResultLine(0, "Average accurate: " + average(accurate));
    }

}
