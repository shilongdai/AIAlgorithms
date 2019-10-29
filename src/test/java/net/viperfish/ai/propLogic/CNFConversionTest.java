package net.viperfish.ai.propLogic;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CNFConversionTest {

    @Test
    public void testProposition() {
        Sentence complexSentence = PropTestUtil.getComplexSentence();
        Map<String, Boolean> assignments = new HashMap<>();
        assignments.put("R", false);
        assignments.put("B", true);
        assignments.put("S", true);
        assignments.put("M", false);
        assignments.put("MT", false);
        ConjunctSentence cnfForm = CNFUtils.toCNF(complexSentence);

        Assert.assertTrue(cnfForm.evaluates(assignments));
        Assert.assertTrue(complexSentence.evaluates(assignments));
    }

    @Test
    public void testSimpleCNFConversion() {
        Sentence aEqualBOrC = PropTestUtil.getSimpleSentence();
        ConjunctSentence result = CNFUtils.toCNF(aEqualBOrC);
        verifyCnf(aEqualBOrC, result);

    }

    @Test
    public void verifyComplexCNFConversion() {
        Sentence finalKB = PropTestUtil.getComplexSentence();

        ConjunctSentence cnf = CNFUtils.toCNF(finalKB);
        verifyCnf(finalKB, cnf);
    }

    private void verifyCnf(Sentence original, ConjunctSentence result) {
        Assert.assertNotEquals(null, result);
        for (Sentence child : result.children()) {
            Assert.assertTrue(isCNFDisjunct(child));
        }
        Assert.assertEquals(ConjunctSentence.class, result.getClass());

        verifyTruthTable(original, result);
    }

    private boolean isCNFDisjunct(Sentence s) {
        if (!(s instanceof DisjunctSentence) && !(s instanceof LiteralSentence) && !(s instanceof NegateSentence)) {
            return false;
        }
        if (isNegateOrLiteral(s)) {
            return true;
        }
        for (Sentence children : s.children()) {
            if (!isNegateOrLiteral(children)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNegateOrLiteral(Sentence s) {
        if (s instanceof NegateSentence) {
            s = ((NegateSentence) s).original();
            return s instanceof LiteralSentence;
        }
        return s instanceof LiteralSentence;
    }

    private void verifyTruthTable(Sentence original, ConjunctSentence cnf) {
        Set<String> cnfSymbols = getSymbols(original);
        Set<String> origSymbols = getSymbols(cnf);

        Assert.assertEquals(cnfSymbols, origSymbols);

        Map<String, Boolean> assignment = new HashMap<>();
        recursiveVerifyTruthTable(original, cnf, assignment, cnfSymbols);
    }

    private void recursiveVerifyTruthTable(Sentence original, ConjunctSentence cnf, Map<String, Boolean> assignment, Set<String> remainingSymbols) {
        if (remainingSymbols.size() == 0) {
            boolean cnfEval = cnf.evaluates(assignment);
            boolean originalEval = original.evaluates(assignment);
            Assert.assertEquals(cnfEval, originalEval);
            return;
        }
        String nextSymbol = remainingSymbols.iterator().next();
        Set<String> newRemaining = new HashSet<>(remainingSymbols);
        newRemaining.remove(nextSymbol);

        assignment.put(nextSymbol, true);
        recursiveVerifyTruthTable(original, cnf, assignment, newRemaining);
        assignment.put(nextSymbol, false);
        recursiveVerifyTruthTable(original, cnf, assignment, newRemaining);
    }

    private Set<String> getSymbols(Sentence sentence) {
        Set<String> result = new HashSet<>();
        if (sentence instanceof LiteralSentence) {
            result.add(((LiteralSentence) sentence).getLiteral());
            return result;
        }

        for (Sentence c : sentence.children()) {
            result.addAll(getSymbols(c));
        }

        return result;
    }

}
