package net.viperfish.ai.propLogic;

import java.util.ArrayList;
import java.util.List;

public class SingleDisjunctElimationRule implements Rule<Sentence> {

    private Sentence original;

    public SingleDisjunctElimationRule(Sentence original) {
        this.original = original;
    }

    @Override
    public Sentence apply() {
        List<Sentence> symbols = new ArrayList<>(original.children());
        if (symbols.size() == 1) {
            return symbols.get(0);
        }
        throw new IllegalArgumentException("Expected a disjunct with a single item, got " + symbols.size());
    }
}
