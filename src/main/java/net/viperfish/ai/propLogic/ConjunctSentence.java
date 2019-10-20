package net.viperfish.ai.propLogic;

import java.util.*;

public class ConjunctSentence extends ActionGeneratingSentence {

    private Set<Sentence> conjuncts;

    public ConjunctSentence(Collection<? extends Sentence> conjuncts) {
        if (conjuncts == null || conjuncts.size() == 0) {
            throw new IllegalArgumentException("Expected non empty conjunct set");
        }
        this.conjuncts = new HashSet<>(conjuncts);
    }

    @Override
    public boolean evaluates(Map<String, Boolean> literals) {
        for (Sentence s : this.conjuncts) {
            if (!s.evaluates(literals)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Collection<Rule<? extends Sentence>> applicableRules() {
        List<Rule<? extends Sentence>> result = new ArrayList<>();

        if (this.conjuncts.size() == 1) {
            result.add(new SingleConjunctElimationRule(this));
            return result;
        }

        Set<ConjunctSentence> conjunctGroups = new HashSet<>();
        Set<DisjunctSentence> disjunctGroups = new HashSet<>();

        for (Sentence s : conjuncts) {
            if (s instanceof ConjunctSentence) {
                conjunctGroups.add((ConjunctSentence) s);
            }
            if (s instanceof DisjunctSentence) {
                disjunctGroups.add((DisjunctSentence) s);
            }
        }

        for (Sentence s : conjuncts) {
            for (ConjunctSentence cs : conjunctGroups) {
                if (cs != s) {
                    for (Sentence child : cs.children()) {
                        result.add(new ConjunctAssociativityRule(this, cs, child, s));
                    }
                }
            }

            for (DisjunctSentence ds : disjunctGroups) {
                if (ds != s) {
                    result.add(new ConjunctDisjunctDistributionRule(this, ds, s));
                }
            }
        }
        return result;
    }

    @Override
    public Collection<Sentence> children() {
        return new HashSet<>(conjuncts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConjunctSentence that = (ConjunctSentence) o;
        return conjuncts.equals(that.conjuncts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conjuncts);
    }
}
