package net.viperfish.ai.propLogic;

import java.util.ArrayList;
import java.util.List;

public class SingleConjunctElimationRule implements Rule<Sentence> {

    private ConjunctSentence original;

    public SingleConjunctElimationRule(ConjunctSentence original) {
        this.original = original;
    }

    @Override
    public Sentence apply() {
        List<Sentence> symbols = new ArrayList<>(this.original.children());
        if (symbols.size() == 1) {
            return symbols.get(0);
        }
        throw new IllegalArgumentException("Expected the given conjunct to have one item, found " + symbols.size());
    }
}
