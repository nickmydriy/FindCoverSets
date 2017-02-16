package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

import java.util.ArrayList;

/**
 * Created by Nick Mudry on 16.02.2017.
 */
public interface RecoveryFunction {
    Vector recover(Vector unit, SetsMatrix matrix);
}
