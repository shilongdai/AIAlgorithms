package net.viperfish.ai.propLogic;

import java.util.HashSet;
import java.util.Set;

public class UnwrapConjunctRule implements Rule<ConjunctSentence> {

    private ConjunctSentence toUnwrap;

    public UnwrapConjunctRule(ConjunctSentence toUnwrap) {
        this.toUnwrap = toUnwrap;
    }

    @Override
    public ConjunctSentence apply() {
        Set<Sentence> newSet = new HashSet<>();

        for (Sentence c : toUnwrap.children()) {
            if (c instanceof ConjunctSentence) {
                newSet.addAll(c.children());
            } else {
                newSet.add(c);
            }
        }

        return new ConjunctSentence(newSet);
    }
}
