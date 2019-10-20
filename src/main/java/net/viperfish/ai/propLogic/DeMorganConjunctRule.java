package net.viperfish.ai.propLogic;

import java.util.HashSet;
import java.util.Set;

public class DeMorganConjunctRule implements Rule<DisjunctSentence> {

    private NegateSentence negatedConjunct;

    public DeMorganConjunctRule(NegateSentence negatedConjunct) {
        this.negatedConjunct = negatedConjunct;
    }

    @Override
    public DisjunctSentence apply() {
        Sentence conjunct = this.negatedConjunct.original();
        if (conjunct instanceof ConjunctSentence) {
            ConjunctSentence conjunctSentence = (ConjunctSentence) conjunct;
            Set<NegateSentence> negated = new HashSet<>();
            for (Sentence s : conjunctSentence.children()) {
                negated.add(new NegateSentence(s));
            }
            return new DisjunctSentence(negated);
        } else {
            throw new IllegalArgumentException("Expected negated conjunct, got:" + conjunct.getClass().getCanonicalName());
        }
    }
}
