package net.viperfish.ai.propLogic;

import net.viperfish.ai.search.State;
import net.viperfish.ai.search.deterministic.HeuristicGoalTester;
import net.viperfish.ai.search.deterministic.ObjectiveFunction;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class CNFHeuristic implements HeuristicGoalTester, ObjectiveFunction {

    @Override
    public double heuristic(State state) {
        if (!(state instanceof Sentence)) {
            throw new IllegalArgumentException("This heuristic applies only to propositional sentences");
        }
        Sentence s = (Sentence) state;
        double implicationCount = countSentenceType(s, new HashSet<>(Collections.singletonList(ImplicationSentence.class)));
        double equivalenceCount = countSentenceType(s, new HashSet<>(Collections.singletonList(EquivalenceSentence.class)));
        double depth = depthCount(s);
        double disjunctOverConjunctCount = disjunctOverConjunctCount(s);
        double nonNegNegateCount = nonLiteralNegateCount(s);

        if (equivalenceCount != 0) {
            return 100000 * equivalenceCount;
        }
        if (implicationCount != 0) {
            return 10000 * implicationCount;
        }
        if (nonNegNegateCount != 0) {
            return 1000 * nonNegNegateCount;
        }
        if (disjunctOverConjunctCount != 0) {
            return 100 * disjunctOverConjunctCount;
        }
        return depth;
    }

    @Override
    public boolean goalReached(State toTest) {
        if (!(toTest instanceof Sentence)) {
            throw new IllegalArgumentException("This heuristic applies only to propositional sentences");
        }
        Sentence s = (Sentence) toTest;
        if (!(s instanceof ConjunctSentence)) {
            return false;
        }
        ConjunctSentence cs = (ConjunctSentence) s;
        for (Sentence children : cs.children()) {
            if (!isCNFDisjunct(children)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double evaluate(State state) {
        return -1 * this.heuristic(state);
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

    private double countSentenceType(Sentence s, Collection<Class<? extends Sentence>> types) {
        int current = 0;
        if (types.contains(s.getClass())) {
            current += 1;
        }
        for (Sentence child : s.children()) {
            current += countSentenceType(child, types);
        }
        return current;
    }


    private int disjunctOverConjunctCount(Sentence s) {
        int count = 0;
        for (Sentence c : s.children()) {
            if (c instanceof ConjunctSentence && s instanceof DisjunctSentence) {
                count += 1;
                break;
            }
            count += disjunctOverConjunctCount(c);
        }
        return count;
    }


    private int depthCount(Sentence s) {
        if (s instanceof LiteralSentence) {
            return 1;
        }

        if (s instanceof NegateSentence) {
            if (((NegateSentence) s).original() instanceof LiteralSentence) {
                return 1;
            }
        }

        int max = 0;
        for (Sentence child : s.children()) {
            int depth = depthCount(child);
            if (depth > max) {
                max = depth;
            }
        }

        return max + 1;
    }


    int nonLiteralNegateCount(Sentence s) {
        int count = 0;
        if (s instanceof NegateSentence) {
            if (!(((NegateSentence) s).original() instanceof LiteralSentence)) {
                count += 1;
            }
        }

        for (Sentence c : s.children()) {
            count += nonLiteralNegateCount(c);
        }

        return count;
    }


}
