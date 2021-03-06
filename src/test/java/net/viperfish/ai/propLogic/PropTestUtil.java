package net.viperfish.ai.propLogic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class PropTestUtil {

    public static Sentence getSimpleSentence() {
        Sentence a = new LiteralSentence("a");
        Sentence b = new LiteralSentence("b");
        Sentence c = new LiteralSentence("c");

        Sentence bOrC = new DisjunctSentence(Arrays.asList(b, c));
        EquivalenceSentence aEqualBOrC = new EquivalenceSentence(a, bOrC);
        return aEqualBOrC;
    }

    public static Sentence getComplexSentence() {
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
        return finalKB;
    }

}
