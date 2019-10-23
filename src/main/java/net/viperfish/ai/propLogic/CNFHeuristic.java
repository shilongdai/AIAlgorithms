package net.viperfish.ai.propLogic;

import net.viperfish.ai.search.State;
import net.viperfish.ai.search.deterministic.HeuristicGoalTester;
import net.viperfish.ai.search.deterministic.ObjectiveFunction;

import java.util.Arrays;
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
        double negateCount = countSentenceType(s, Collections.singletonList(NegateSentence.class));
        double implicationCount = countSentenceType(s, new HashSet<>(Collections.singletonList(ImplicationSentence.class)));
        double equivalenceCount = countSentenceType(s, new HashSet<>(Collections.singletonList(EquivalenceSentence.class)));
        double depth = depthCount(s);
        double literalCount = countSentenceType(s, new HashSet<>(Arrays.asList(LiteralSentence.class)));

        return equivalenceCount * 100 + implicationCount * 5 + depth;
    }

    @Override
    public boolean goalReached(State toTest) {
        System.out.println("Testing: " + toTest.toString());
        System.out.println("Heuristic:" + heuristic(toTest));
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


    private int depthCount(Sentence s) {
        if (s instanceof LiteralSentence) {
            return 1;
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


}
