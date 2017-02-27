package ru.ifmo.mudry.coverproblem.tests;

import javafx.util.Pair;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Nick Mudry on 22.02.2017.
 */
public class TestReader {

    public static Pair<SetsMatrix, Double> getTest(boolean small, int number) throws IOException {
        String file = "testfiles" + File.separator + (small ? "small" : "big") + File.separator + number + ".test";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int size = Integer.parseInt(reader.readLine());
        boolean[][] test = new boolean[size][size];
        double[] testCost = new double[size];
        for (int i = 0; i < size; i++) {
            String[] line = reader.readLine().split(" ");
            for (int j = 0; j < size; j++) {
                test[i][j] = Integer.parseInt(line[j]) == 1;
            }
            testCost[i] = Double.parseDouble(line[line.length - 1].replace(",", "."));
        }
        double answer = Double.parseDouble(reader.readLine().replace(",", "."));
        return new Pair<>(new SetsMatrix(test, testCost), answer);
    }

    public static Pair<SetsMatrix, Integer> getOrLibrarySets(int number) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("testfiles" + File.separator + "or"
                + File.separator + number + ".txt"));
        Input input = new Input(reader);

        int n = input.nextInt(), m = input.nextInt();
        boolean[][] test = new boolean[m][n];
        for (boolean[] c : test) {
            Arrays.fill(c, false);
        }
        double[] testCost = new double[m];

        for (int i = 0; i < m; i++) {
            testCost[i] = input.nextInt();
        }
        for (int i = 0; i < n; i++) {
            int r = input.nextInt();
            for (int j = 0; j < r; j++) {
                test[input.nextInt() - 1][i] = true;
            }
        }
        int res = input.nextInt();
        reader.close();
        return new Pair<>(new SetsMatrix(test, testCost), res);
    }

    static class Input {
        BufferedReader reader;
        StringTokenizer line = null;

        public Input(BufferedReader reader) {
            this.reader = reader;
        }

        public int nextInt() throws IOException {
            if (line == null || !line.hasMoreTokens()) {
                try {
                    line = new StringTokenizer(reader.readLine());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            return Integer.valueOf(line.nextToken());
        }
    }
}
