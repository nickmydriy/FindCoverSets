package ru.ifmo.mudry.coverproblem;

import com.sun.javafx.binding.StringFormatter;

import java.text.DecimalFormat;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class Main {

    static int SIZE = 10;

    public static void main(String[] args) {
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
            System.out.format("%.2f%n", testCost[i]);
        }


    }

}
