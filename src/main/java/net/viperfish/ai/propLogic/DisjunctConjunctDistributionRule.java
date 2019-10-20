package net.viperfish.ai.propLogic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DisjunctConjunctDistributionRule implements Rule<DisjunctSentence> {

    private DisjunctSentence originalDisjunct;
    private ConjunctSentence conjunctGroup;
    private Sentence toDistribute;

    public DisjunctConjunctDistributionRule(DisjunctSentence originalDisjunct, ConjunctSentence conjunctGroup, Sentence toDistribute) {
        this.originalDisjunct = originalDisjunct;
        this.conjunctGroup = conjunctGroup;
        this.toDistribute = toDistribute;
    }

    @Override
    public DisjunctSentence apply() {
        Set<Sentence> symbols = new HashSet<>(originalDisjunct.children());
        if (symbols.containsAll(Arrays.asList(conjunctGroup, toDistribute))) {
            Set<Sentence> result = new HashSet<>();
            Set<DisjunctSentence> distributedParts = new HashSet<>();
            for (Sentence s : conjunctGroup.children()) {
                Set<Sentence> disjunctPart = new HashSet<>();
                disjunctPart.add(s);
                disjunctPart.add(toDistribute);
                distributedParts.add(new DisjunctSentence(disjunctPart));
            }
            ConjunctSentence distributed = new ConjunctSentence(distributedParts);

            symbols.removeAll(Arrays.asList(conjunctGroup, toDistribute));
            symbols.add(distributed);
            return new DisjunctSentence(symbols);
        } else {
            throw new IllegalArgumentException("Expected all of the parameters to be part of the sentence");
        }
    }
}
