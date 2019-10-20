package net.viperfish.ai.propLogic;

import java.util.HashSet;
import java.util.Set;

public class DeMorganDisjunctRule implements Rule<ConjunctSentence> {

    private NegateSentence negatedDisjunct;

    public DeMorganDisjunctRule(NegateSentence negatedDisjunct) {
        this.negatedDisjunct = negatedDisjunct;
    }

    @Override
    public ConjunctSentence apply() {
        Sentence disjunct = this.negatedDisjunct.original();
        if (disjunct instanceof DisjunctSentence) {
            DisjunctSentence disjunctSentence = (DisjunctSentence) disjunct;
            Set<NegateSentence> negated = new HashSet<>();
            for (Sentence s : disjunctSentence.children()) {
                negated.add(new NegateSentence(s));
            }
            return new ConjunctSentence(negated);
        } else {
            throw new IllegalArgumentException("Expected negated disjunct, got:" + disjunct.getClass().getCanonicalName());
        }
    }
}
