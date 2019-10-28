package net.viperfish.ai.propLogic;

public class RemoveRedundantConjunctWrap implements Rule<Sentence> {

    private ConjunctSentence original;

    public RemoveRedundantConjunctWrap(ConjunctSentence original) {
        this.original = original;
    }

    @Override
    public Sentence apply() {
        if (original.children().size() == 1) {
            return original.children().iterator().next();
        }
        throw new IllegalArgumentException("Expected Redundant Conjunct");
    }
}
