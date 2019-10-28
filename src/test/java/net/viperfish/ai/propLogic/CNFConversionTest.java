package net.viperfish.ai.propLogic;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class CNFConversionTest {

    @Test
    public void testSimpleCNFConversion() {
        Sentence a = new LiteralSentence("a");
        Sentence b = new LiteralSentence("b");
        Sentence c = new LiteralSentence("c");

        Sentence bOrC = new DisjunctSentence(Arrays.asList(b, c));
        EquivalenceSentence aEqualBOrC = new EquivalenceSentence(a, bOrC);

        ConjunctSentence result = CNFUtils.toCNF(aEqualBOrC);
        verifyCnf(aEqualBOrC, result);

    }

    @Test
    public void verifyDisjunctCNFConversion() {
        Sentence a = new ConjunctSentence(Arrays.asList(new LiteralSentence("a"), new LiteralSentence("b"), new LiteralSentence("c")));
        Sentence b = new ConjunctSentence(Arrays.asList(new LiteralSentence("d"), new LiteralSentence("e"), new LiteralSentence("f")));
        Sentence c = new ConjunctSentence(Arrays.asList(new LiteralSentence("g"), new LiteralSentence("h"), new LiteralSentence("i")));

        Sentence disjunct = new DisjunctSentence(Arrays.asList(a, b, c));

        ConjunctSentence cnf = CNFUtils.toCNF(disjunct);

        verifyCnf(disjunct, cnf);
    }

    @Test
    public void verifyComplexCNFConversion() {
        Sentence michael = new LiteralSentence("M");
        Sentence bill = new LiteralSentence("B");
        Sentence richard = new LiteralSentence("R");
        Sentence sam = new LiteralSentence("S");
        Sentence matt = new LiteralSentence("MT");

        Sentence notMichael = new NegateSentence(michael);
        Sentence notBill = new NegateSentence(bill);
        Sentence notRichard = new NegateSentence(richard);
        Sentence notSam = new NegateSentence(sam);
        Sentence notMatt = new NegateSentence(matt);

        Sentence mXorB = new DisjunctSentence(Arrays.asList(new ConjunctSentence(Arrays.asList(michael, notBill)), new ConjunctSentence(Arrays.asList(notMichael, bill))));
        Sentence rXorS = new DisjunctSentence(Arrays.asList(new ConjunctSentence(Arrays.asList(richard, notSam)), new ConjunctSentence(Arrays.asList(notRichard, sam))));
        Sentence matXorM = new DisjunctSentence(Arrays.asList(new ConjunctSentence(Arrays.asList(matt, notMichael)), new ConjunctSentence(Arrays.asList(notMatt, michael))));
        Sentence bXorMat = new DisjunctSentence(Arrays.asList(new ConjunctSentence(Arrays.asList(bill, notMatt)), new ConjunctSentence(Arrays.asList(notBill, matt))));
        Sentence bXorR = new DisjunctSentence(Arrays.asList(new ConjunctSentence(Arrays.asList(bill, notRichard)), new ConjunctSentence(Arrays.asList(notRichard, bill))));

        Set<Sentence> possibleGroups = new HashSet<>(Arrays.asList(mXorB, rXorS, matXorM, bXorMat, bXorR));
        Set<Sentence> possiblilityDisjunctSet = new HashSet<>();
        for (Sentence s : possibleGroups) {
            Set<Sentence> conjunctClause = new HashSet<>();
            conjunctClause.add(new NegateSentence(s));
            for (Sentence c : possibleGroups) {
                if (c != s) {
                    conjunctClause.add(c);
                }
            }
            possiblilityDisjunctSet.add(new ConjunctSentence(conjunctClause));
        }
        Sentence possibilityDisjunct = new DisjunctSentence(possiblilityDisjunctSet);
        Sentence implication = new ImplicationSentence(richard, bill);
        ConjunctSentence finalKB = new ConjunctSentence(Arrays.asList(possibilityDisjunct, implication));
        System.out.println(finalKB);

        ConjunctSentence cnf = CNFUtils.toCNF(finalKB);
        System.out.println(cnf);

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
            Assert.assertEquals(cnf.evaluates(assignment), original.evaluates(assignment));
            return;
        }
        String nextSymbol = remainingSymbols.iterator().next();
        remainingSymbols.remove(nextSymbol);
        assignment.put(nextSymbol, true);
        recursiveVerifyTruthTable(original, cnf, assignment, remainingSymbols);
        assignment.put(nextSymbol, false);
        recursiveVerifyTruthTable(original, cnf, assignment, remainingSymbols);
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
