package net.viperfish.ai.propLogic;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CNFLocalSearchTest {

    @Test
    public void testSimpleCNFConversion() {
        Sentence a = new LiteralSentence("a");
        Sentence b = new LiteralSentence("b");
        Sentence c = new LiteralSentence("c");

        Sentence bOrC = new DisjunctSentence(Arrays.asList(b, c));
        EquivalenceSentence aEqualBOrC = new EquivalenceSentence(a, bOrC);

        Sentence result = CNFUtils.toCNF(aEqualBOrC);
        verifyCnf(result);

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

        Sentence cnf = CNFUtils.toCNF(finalKB);

        verifyCnf(cnf);
    }

    private void verifyCnf(Sentence result) {
        Assert.assertNotEquals(null, result);
        for (Sentence child : result.children()) {
            Assert.assertTrue(isCNFDisjunct(child));
        }
        Assert.assertEquals(ConjunctSentence.class, result.getClass());
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

}
