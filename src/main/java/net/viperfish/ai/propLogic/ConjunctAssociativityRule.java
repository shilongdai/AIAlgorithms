package net.viperfish.ai.propLogic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConjunctAssociativityRule implements Rule<ConjunctSentence> {

    private ConjunctSentence conjunct;
    private ConjunctSentence groupedConjunct;
    private Sentence toReplace;
    private Sentence replaceWith;

    public ConjunctAssociativityRule(ConjunctSentence conjunct, ConjunctSentence groupedConjunct, Sentence toReplace, Sentence replaceWith) {
        this.conjunct = conjunct;
        this.groupedConjunct = groupedConjunct;
        this.toReplace = toReplace;
        this.replaceWith = replaceWith;
    }

    @Override
    public ConjunctSentence apply() {
        Set<Sentence> original = new HashSet<>(conjunct.children());
        if (original.containsAll(Arrays.asList(groupedConjunct, replaceWith))) {
            Set<Sentence> group = new HashSet<>(groupedConjunct.children());
            if (group.contains(toReplace)) {
                group.remove(toReplace);
                group.add(replaceWith);
                original.remove(replaceWith);
                original.add(toReplace);
                original.remove(groupedConjunct);

                ConjunctSentence newConjunct = new ConjunctSentence(group);
                original.add(newConjunct);

                ConjunctSentence result = new ConjunctSentence(original);
                return result;
            } else {
                throw new IllegalArgumentException("Expected toReplace to be in the conjunct group");
            }
        } else {
            throw new IllegalArgumentException("Expected parameters to be part of the sentence");
        }
    }
}
