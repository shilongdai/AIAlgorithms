package net.viperfish.ai.propLogic;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public abstract class PropositionalLogicSolverTest {

    @Test
    public void testSolverSimple() {
        Sentence aEqualBOrC = PropTestUtil.getSimpleSentence();
        PropositionalLogicSolver solver = getSolver();
        Map<String, Boolean> model = solver.solve(CNFUtils.toCNF(aEqualBOrC));

        Assert.assertTrue(aEqualBOrC.evaluates(model));
    }

    @Test
    public void testSolver() {
        Sentence finalKB = PropTestUtil.getComplexSentence();
        ConjunctSentence cnf = CNFUtils.toCNF(finalKB);

        PropositionalLogicSolver solver = getSolver();
        Map<String, Boolean> model = solver.solve(cnf);

        System.out.println(model);

        Assert.assertNotEquals(null, model);
        Assert.assertTrue(finalKB.evaluates(model));
    }

    protected abstract PropositionalLogicSolver getSolver();

}
