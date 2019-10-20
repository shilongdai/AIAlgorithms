package net.viperfish.ai.propLogic;

import java.util.*;

public class NegateSentence extends ActionGeneratingSentence {

    private Sentence toNegate;

    public NegateSentence(Sentence toNegate) {
        this.toNegate = toNegate;
    }

    @Override
    public boolean evaluates(Map<String, Boolean> literals) {
        return !toNegate.evaluates(literals);
    }

    @Override
    public Collection<Rule<? extends Sentence>> applicableRules() {
        List<Rule<? extends Sentence>> result = new ArrayList<>();
        if (toNegate instanceof DisjunctSentence) {
            result.add(new DeMorganDisjunctRule(this));
        }
        if (toNegate instanceof ConjunctSentence) {
            result.add(new DeMorganConjunctRule(this));
        }
        if (toNegate instanceof NegateSentence) {
            result.add(new DoubleNegativeEliminationRule(this));
        }
        return result;
    }

    @Override
    public Collection<Sentence> children() {
        return Collections.singletonList(toNegate);
    }

    public Sentence original() {
        return this.toNegate;
    }
}
