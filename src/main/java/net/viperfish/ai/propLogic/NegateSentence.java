package net.viperfish.ai.propLogic;

import java.util.*;

public class NegateSentence extends ActionGeneratingSentence {

    private Sentence toNegate;

    public NegateSentence(Sentence toNegate) {
        if (toNegate == null) {
            throw new IllegalArgumentException("the parameter cannot be null");
        }
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

    @Override
    public Sentence replace(Sentence original, Sentence replaceWith) {
        if (original.equals(this.toNegate)) {
            return new NegateSentence(replaceWith);
        }
        throw new IllegalArgumentException("Expected the specified sentence to match the sentence to negate, it did not");
    }

    @Override
    public String toString() {
        return "¬" + this.toNegate.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NegateSentence that = (NegateSentence) o;
        return toNegate.equals(that.toNegate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toNegate);
    }
}
