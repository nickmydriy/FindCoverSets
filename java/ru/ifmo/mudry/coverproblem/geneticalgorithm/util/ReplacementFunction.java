package ru.ifmo.mudry.coverproblem.geneticalgorithm.util;

import java.util.ArrayList;

/**
 * Функция смены индивидов популяции.
 */
public interface ReplacementFunction {

    /**
     * @param population популяция.
     * @param newUnit новый индивид, который может сменить какого-нибудь индивида из популяции.
     */
    void replace(ArrayList<Vector> population, Vector newUnit);
}
