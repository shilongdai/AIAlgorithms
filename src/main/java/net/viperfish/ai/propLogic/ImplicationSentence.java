package net.viperfish.ai.propLogic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class ImplicationSentence extends ActionGeneratingSentence {

    private Sentence condition;
    private Sentence associate;

    public ImplicationSentence(Sentence condition, Sentence associate) {
        if (condition == null || associate == null) {
            throw new IllegalArgumentException("Expected non-null parameters");
        }

        this.condition = condition;
        this.associate = associate;
    }

    @Override
    public boolean evaluates(Map<String, Boolean> literals) {
        boolean condition = this.condition.evaluates(literals);
        if (!condition) {
            return true;
        }
        return this.associate.evaluates(literals);
    }

    @Override
    public Collection<Rule<? extends Sentence>> applicableRules() {
        return Arrays.asList(new ImplicationElimationRule(this), new ContrapositionRule(this));
    }

    @Override
    public Collection<Sentence> children() {
        return Arrays.asList(this.associate, this.condition);
    }

    public Sentence getCondition() {
        return condition;
    }

    public Sentence getAssociate() {
        return associate;
    }

    @Override
    public Sentence replace(Sentence original, Sentence replaceWith) {
        if (condition.equals(original)) {
            return new ImplicationSentence(replaceWith, associate);
        }
        if (associate.equals(original)) {
            return new ImplicationSentence(condition, replaceWith);
        }
        throw new IllegalArgumentException("Expected the given sentence to match condition or associate. It matched neither");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImplicationSentence that = (ImplicationSentence) o;
        return condition.equals(that.condition) &&
                associate.equals(that.associate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, associate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(condition.toString()).append("⇒").append(associate.toString()).append(')');
        return sb.toString();
    }
}
