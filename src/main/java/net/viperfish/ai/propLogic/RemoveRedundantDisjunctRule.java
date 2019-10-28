package net.viperfish.ai.propLogic;

public class RemoveRedundantDisjunctRule implements Rule<Sentence> {

    private DisjunctSentence disjunctSentence;

    public RemoveRedundantDisjunctRule(DisjunctSentence disjunctSentence) {
        this.disjunctSentence = disjunctSentence;
    }

    @Override
    public Sentence apply() {
        if (disjunctSentence.children().size() == 1) {
            return disjunctSentence.children().iterator().next();
        }
        throw new IllegalArgumentException("Expected Redundant Disjunct");
    }
}
