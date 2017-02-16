package ru.ifmo.mudry.coverproblem.geneticalgorithm.implementation.util;

import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.CoverCheckFunction;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.SetsMatrix;
import ru.ifmo.mudry.coverproblem.geneticalgorithm.util.Vector;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public class CoverCheckFunctionImpl implements CoverCheckFunction {
    @Override
    public boolean checkCover(Vector unit, SetsMatrix matrix) {
        Set<Integer> coverSet = new TreeSet<>();
        boolean[] vector = unit.getVector();
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
}
