package net.viperfish.ai.propLogic;

public class ContrapositionRule implements Rule<ImplicationSentence> {

    private ImplicationSentence original;

    public ContrapositionRule(ImplicationSentence original) {
        this.original = original;
    }

    @Override
    public ImplicationSentence apply() {
        ImplicationSentence result = new ImplicationSentence(new NegateSentence(original.getAssociate()), new NegateSentence(original.getCondition()));
        return result;
    }
}
