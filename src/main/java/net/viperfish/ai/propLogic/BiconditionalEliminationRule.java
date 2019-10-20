package net.viperfish.ai.propLogic;

import java.util.HashSet;
import java.util.Set;

public class BiconditionalEliminationRule implements Rule<ConjunctSentence> {

    private EquivalenceSentence equivalenceSentence;

    public BiconditionalEliminationRule(EquivalenceSentence equivalenceSentence) {
        this.equivalenceSentence = equivalenceSentence;
    }

    @Override
    public ConjunctSentence apply() {
        Set<Sentence> implications = new HashSet<>();
        ImplicationSentence impl1 = new ImplicationSentence(equivalenceSentence.getSide1(), equivalenceSentence.getSide2());
        ImplicationSentence impl2 = new ImplicationSentence(equivalenceSentence.getSide2(), equivalenceSentence.getSide1());
        implications.add(impl1);
        implications.add(impl2);

        return new ConjunctSentence(implications);
    }
}
