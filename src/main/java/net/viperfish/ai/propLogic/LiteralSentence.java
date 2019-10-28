package net.viperfish.ai.propLogic;

import net.viperfish.ai.search.Action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class LiteralSentence implements Sentence {

    private String literal;

    public LiteralSentence(String literal) {
        if (literal == null) {
            throw new IllegalArgumentException("Expected non-null parameter");
        }
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public boolean evaluates(Map<String, Boolean> literals) {
        if (literals.containsKey(this.literal)) {
            return literals.get(literal);
        }
        throw new IllegalArgumentException("The given literal map is incomplete, " + this.literal);
    }

    @Override
    public Collection<Rule<? extends Sentence>> applicableRules() {
        return new ArrayList<>();
    }

    @Override
    public Collection<Sentence> children() {
        return new ArrayList<>();
    }

    @Override
    public Collection<? extends Action> availableActions() {
        return new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteralSentence that = (LiteralSentence) o;
        return literal.equals(that.literal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(literal);
    }

    @Override
    public String toString() {
        return this.literal;
    }
}
