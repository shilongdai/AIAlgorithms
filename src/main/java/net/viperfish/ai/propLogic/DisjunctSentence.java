package net.viperfish.ai.propLogic;

import java.util.*;

public class DisjunctSentence extends ActionGeneratingSentence {

    private Set<Sentence> disjoints;

    public DisjunctSentence(Set<? extends Sentence> disjoints) {
        if (disjoints == null || disjoints.size() == 0) {
            throw new IllegalArgumentException("Expected non-empty disjoint set");
        }
        this.disjoints = new HashSet<>(disjoints);
    }

    @Override
    public boolean evaluates(Map<String, Boolean> literals) {
        for (Sentence s : this.disjoints) {
            if (s.evaluates(literals)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Rule<? extends Sentence>> applicableRules() {
        List<Rule<? extends Sentence>> result = new ArrayList<>();

        if (this.disjoints.size() == 1) {
            result.add(new SingleDisjunctElimationRule(this));
            return result;
        }

        Set<ConjunctSentence> conjunctGroups = new HashSet<>();
        Set<DisjunctSentence> disjunctGroups = new HashSet<>();

        for (Sentence s : disjoints) {
            if (s instanceof ConjunctSentence) {
                conjunctGroups.add((ConjunctSentence) s);
            }
            if (s instanceof DisjunctSentence) {
                disjunctGroups.add((DisjunctSentence) s);
            }
        }

        for (Sentence s : disjoints) {
            for (ConjunctSentence cs : conjunctGroups) {
                if (cs != s) {
                    result.add(new DisjunctConjunctDistributionRule(this, cs, s));

                }
            }

            for (DisjunctSentence ds : disjunctGroups) {
                if (ds != s) {
                    for (Sentence child : ds.children()) {
                        result.add(new DisjunctAssociativityRule(this, ds, child, s));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Collection<Sentence> children() {
        return new HashSet<>(this.disjoints);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisjunctSentence that = (DisjunctSentence) o;
        return disjoints.equals(that.disjoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disjoints);
    }
}
