package net.viperfish.ai.propLogic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConjunctDisjunctDistributionRule implements Rule<ConjunctSentence> {

    private ConjunctSentence originalConjunct;
    private DisjunctSentence disjunctGroup;
    private Sentence toDistribute;

    public ConjunctDisjunctDistributionRule(ConjunctSentence originalConjunct, DisjunctSentence disjunctGroup, Sentence toDistribute) {
        this.originalConjunct = originalConjunct;
        this.disjunctGroup = disjunctGroup;
        this.toDistribute = toDistribute;
    }

    @Override
    public ConjunctSentence apply() {
        Set<Sentence> symbols = new HashSet<>(originalConjunct.children());
        if (symbols.containsAll(Arrays.asList(disjunctGroup, toDistribute))) {
            Set<Sentence> result = new HashSet<>();
            Set<ConjunctSentence> distributedParts = new HashSet<>();
            for (Sentence s : disjunctGroup.children()) {
                Set<Sentence> conjunctPart = new HashSet<>();
                conjunctPart.add(s);
                conjunctPart.add(toDistribute);
                distributedParts.add(new ConjunctSentence(conjunctPart));
            }
            DisjunctSentence distributed = new DisjunctSentence(distributedParts);

            symbols.removeAll(Arrays.asList(disjunctGroup, toDistribute));
            symbols.add(distributed);
            return new ConjunctSentence(symbols);
        } else {
            throw new IllegalArgumentException("Expected all of the parameters to be part of the sentence");
        }
    }
}
