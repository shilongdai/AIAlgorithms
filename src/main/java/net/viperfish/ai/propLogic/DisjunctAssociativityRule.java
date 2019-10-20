package net.viperfish.ai.propLogic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DisjunctAssociativityRule implements Rule<DisjunctSentence> {

    private DisjunctSentence disjunct;
    private DisjunctSentence disjunctGroup;
    private Sentence toReplace;
    private Sentence replaceWith;

    public DisjunctAssociativityRule(DisjunctSentence disjunct, DisjunctSentence disjunctGroup, Sentence toReplace, Sentence replaceWith) {
        this.disjunct = disjunct;
        this.disjunctGroup = disjunctGroup;
        this.toReplace = toReplace;
        this.replaceWith = replaceWith;
    }

    @Override
    public DisjunctSentence apply() {
        Set<Sentence> original = new HashSet<>(disjunct.children());
        if (original.containsAll(Arrays.asList(disjunctGroup, replaceWith))) {
            Set<Sentence> group = new HashSet<>(disjunctGroup.children());
            if (group.contains(toReplace)) {
                group.remove(toReplace);
                group.add(replaceWith);
                original.remove(replaceWith);
                original.add(toReplace);
                original.remove(disjunctGroup);

                DisjunctSentence newConjunct = new DisjunctSentence(group);
                original.add(newConjunct);

                DisjunctSentence result = new DisjunctSentence(original);
                return result;
            } else {
                throw new IllegalArgumentException("Expected toReplace to be in the Disjunct group");
            }
        } else {
            throw new IllegalArgumentException("Expected the parameters to be part of the sentence");
        }
    }
}
