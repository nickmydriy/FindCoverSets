package ru.ifmo.mudry.coverproblem.tests;

import ru.ifmo.mudry.coverproblem.bruteforcealgorithm.BruteforceAlgorithm;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class TestGenerator {

    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= Test.uniqueTestCount; i++) {
            System.out.println("Generate small test № " + i);
            SetsMatrix matrix = generateSets(Test.smallTestSize);
            BruteforceAlgorithm bf = new BruteforceAlgorithm(matrix.matrix, matrix.cost);
            writeTest(true, i, bf.calculate().getFitness(), matrix);
        }
        for (int i = 1; i <= Test.uniqueTestCount; i++) {
            System.out.println("Generate big test № " + i);
            SetsMatrix matrix = generateSets(Test.bigTestSize);
            writeTest(false, i, 0, matrix);
        }
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

    static void writeTest(boolean smallTest, int testNumber, double answer, SetsMatrix matrix) throws IOException {
        String file = (smallTest ? "small" : "big") + File.separator + testNumber + ".test";
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        int size = matrix.setsCount;
        writer.write(size + "");
        writer.newLine();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                writer.write((matrix.matrix[i][j] ? "1" : "0") + " ");
            }
            writer.write(matrix.cost[i] + " ");
            writer.newLine();
        }
        writer.write("" + answer);
        writer.close();
    }
}
