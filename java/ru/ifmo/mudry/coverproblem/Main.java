package ru.ifmo.mudry.coverproblem;

import ru.ifmo.mudry.coverproblem.bruteforcealgorithm.BruteforceAlgorithm;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.Population;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.StandardImplementation;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class Main {

    static int SIZE = 5;

    public static void main(String[] args) throws IOException {
        DecimalFormat myFormatter = new DecimalFormat("#.##");
        int[][] test = new int[SIZE][SIZE];
        double[] testCost = new double[SIZE];

//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                test[i][j] = Math.random() < 0.5 ? 1 : 0;
//            }
//        }
//
//        for (int i = 0; i < SIZE; i++) {
//            testCost[i] = Math.random() * 100;
//        }
//
//        BufferedWriter writer = new BufferedWriter(new FileWriter("test.in"));
//        writer.write(SIZE + " ");
//        writer.newLine();
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                writer.write(test[i][j] + " ");
//            }
//            writer.write(testCost[i] + " ");
//            writer.newLine();
//        }
//        writer.close();

        BufferedReader reader = new BufferedReader(new FileReader("test.in"));
        reader.readLine();
        for (int i = 0; i < SIZE; i++) {
            String[] line = reader.readLine().split(" ");
            for (int j = 0; j < SIZE; j++) {
                test[i][j] = Integer.parseInt(line[j]);
            }
            testCost[i] = Double.parseDouble(line[line.length - 1].replace(",", "."));
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(test[i][j] + " ");
            }
            System.out.print("    ");
            System.out.println(myFormatter.format(testCost[i]));
        }

        boolean[][] testBool = new boolean[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                testBool[i][j] = test[i][j] == 1;
            }
        }

        BruteforceAlgorithm bruteforceAlgorithm = new BruteforceAlgorithm(testBool, testCost);

        long beforeBr = System.currentTimeMillis();
        Vector vec = bruteforceAlgorithm.calculate();
        long afterBr = System.currentTimeMillis();

        StandardImplementation testAlgo = new StandardImplementation();
        long beforeGn = System.currentTimeMillis();
        Population population = testAlgo.oldCalculate(testBool, testCost);
        long afterGn = System.currentTimeMillis();

        Vector vector = population.getTheBestResult();

        System.out.println("Bruteforce result: ");
        System.out.println("Total time with multithreaded: " + (afterBr - beforeBr));
        System.out.println("Cost: " + vec.getFitness());
        System.out.println("Vector: " + Arrays.toString(vec.getVector()));

        System.out.println("Genetic result: ");
        System.out.println("Total time: " + (afterGn - beforeGn));
        System.out.println("Population step: " + population.step);
        System.out.println("Cost: " + vector.getFitness());
        System.out.println("Vector: " + Arrays.toString(vector.getVector()));
    }
}
