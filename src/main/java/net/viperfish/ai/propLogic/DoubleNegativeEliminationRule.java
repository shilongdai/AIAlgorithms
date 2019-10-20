package net.viperfish.ai.propLogic;

public class DoubleNegativeEliminationRule implements Rule<Sentence> {

    private NegateSentence doubleNegative;

    public DoubleNegativeEliminationRule(NegateSentence doubleNegative) {
        this.doubleNegative = doubleNegative;
    }

    @Override
    public Sentence apply() {
        Sentence firstNegate = doubleNegative.original();
        if (firstNegate instanceof NegateSentence) {
            Sentence original = ((NegateSentence) firstNegate).original();
            return original;
        }
        return doubleNegative;
    }
}
