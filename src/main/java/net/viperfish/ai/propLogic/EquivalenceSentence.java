package net.viperfish.ai.propLogic;

import java.util.*;

public class EquivalenceSentence extends ActionGeneratingSentence {

    private Sentence side1;
    private Sentence side2;

    public EquivalenceSentence(Sentence side1, Sentence side2) {
        if (side1 == null || side2 == null) {
            throw new IllegalArgumentException("Expected non-null parameters");
        }

        this.side1 = side1;
        this.side2 = side2;
    }

    @Override
    public boolean evaluates(Map<String, Boolean> literals) {
        boolean side1 = this.side1.evaluates(literals);
        boolean side2 = this.side2.evaluates(literals);
        return side1 == side2;
    }

    @Override
    public Collection<Rule<? extends Sentence>> applicableRules() {
        return Collections.singletonList(new BiconditionalEliminationRule(this));
    }

    @Override
    public Collection<Sentence> children() {
        return Arrays.asList(side1, side2);
    }

    public Sentence getSide1() {
        return side1;
    }

    public Sentence getSide2() {
        return side2;
    }

    @Override
    public Sentence replace(Sentence original, Sentence replaceWith) {
        if (side1.equals(original)) {
            return new EquivalenceSentence(replaceWith, side2);
        }
        if (side2.equals(original)) {
            return new EquivalenceSentence(side1, replaceWith);
        }

        throw new IllegalArgumentException("Neither of the sentence in the equivalence relation matches the specified sentence to replace");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquivalenceSentence that = (EquivalenceSentence) o;
        return side1.equals(that.side1) &&
                side2.equals(that.side2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(side1, side2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(side1.toString());
        sb.append('⇔');
        sb.append(side2.toString());
        sb.append(')');
        return sb.toString();
    }
}
