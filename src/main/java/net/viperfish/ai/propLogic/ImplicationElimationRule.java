package net.viperfish.ai.propLogic;

import java.util.HashSet;
import java.util.Set;

public class ImplicationElimationRule implements Rule<DisjunctSentence> {

    private ImplicationSentence original;

    public ImplicationElimationRule(ImplicationSentence original) {
        this.original = original;
    }

    @Override
    public DisjunctSentence apply() {
        Set<Sentence> group = new HashSet<>();
        group.add(new NegateSentence(original.getCondition()));
        group.add(original.getAssociate());
        return new DisjunctSentence(group);
    }
}
