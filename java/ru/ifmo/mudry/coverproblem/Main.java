package ru.ifmo.mudry.coverproblem;

import com.sun.javafx.binding.StringFormatter;
import ru.ifmo.mudry.coverproblem.bruteforcealgorithm.BruteforceAlgorithm;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.Population;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.FirstImplementation;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class Main {

    static int SIZE = 10;

    public static void main(String[] args) throws IOException {
        DecimalFormat myFormatter = new DecimalFormat("#.##");
        int[][] test = new int[SIZE][SIZE];
        double[] testCost = new double[SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                test[i][j] = Math.random() < 0.5 ? 1 : 0;
            }
        }

        for (int i = 0; i < SIZE; i++) {
            testCost[i] = Math.random() * 10;
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

        Vector vec = bruteforceAlgorithm.calculate();

        FirstImplementation testAlgo = new FirstImplementation();

        Population population = testAlgo.calculate(testBool, testCost);

        Vector vector = population.getTheBestResult();

        System.out.println("Bruteforce result: ");
        System.out.println("Cost: " + vec.getFitness());
        System.out.println("Vector: " + Arrays.toString(vec.getVector()));

        System.out.println("Genetic result: ");
        System.out.println("Population step: " + population.step);
        System.out.println("Power: " + vector.getFitness());
        System.out.println("Vector: " + Arrays.toString(vector.getVector()));
    }
}
