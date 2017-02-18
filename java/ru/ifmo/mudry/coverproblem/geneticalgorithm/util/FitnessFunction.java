package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

/**
 * Функция приспособленности.
 */
public class FitnessFunction {
    /**
     * @param vector генотип особи. (Каждый бит говорит о вхождении конкретного множства).
     * @param cost массив описывающий цену множеств. (Элемент с индексом i содержит цену i-го множества).
     * @return сумма всех описанных в vector множеств.
     */
    public static double calculateFitness(boolean[] vector, double[] cost) {
        double result = 0;
        for (int i = 0; i < vector.length; i++) {
            if (vector[i]) {
                result += cost[i];
            }
        }
        return result;
    }
}
