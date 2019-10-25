package net.viperfish.ai.propLogic;

import java.util.HashSet;
import java.util.Set;

public class UnwrapDisjunctRule implements Rule<DisjunctSentence> {

    private DisjunctSentence toUnwrap;

    public UnwrapDisjunctRule(DisjunctSentence toUnwrap) {
        this.toUnwrap = toUnwrap;
    }

    @Override
    public DisjunctSentence apply() {
        Set<Sentence> newSet = new HashSet<>();

        for (Sentence s : toUnwrap.children()) {
            if (s instanceof DisjunctSentence) {
                newSet.addAll(s.children());
            } else {
                newSet.add(s);
            }
        }
        return new DisjunctSentence(newSet);
    }
}
