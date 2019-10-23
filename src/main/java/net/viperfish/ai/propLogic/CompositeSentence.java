package net.viperfish.ai.propLogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CompositeSentence implements Sentence {

    public abstract Sentence replace(Sentence original, Sentence replaceWith);

    public Collection<Rule<? extends Sentence>> replacementRules() {
        List<Rule<? extends Sentence>> rules = new ArrayList<>();

        for (Sentence s : this.children()) {
            for (Rule<? extends Sentence> r : s.applicableRules()) {
                rules.add(new ReplacementRule(r, this, s));
            }

            if (s instanceof CompositeSentence) {
                for (Rule<? extends Sentence> replacement : ((CompositeSentence) s).replacementRules()) {
                    rules.add(new ReplacementRule(replacement, this, s));
                }
            }
        }

        return rules;

    }

    public static class ReplacementRule implements Rule<Sentence> {

        private Rule<? extends Sentence> toApply;
        private CompositeSentence parent;
        private Sentence toReplace;

        public ReplacementRule(Rule<? extends Sentence> toApply, CompositeSentence parent, Sentence toReplace) {
            this.toApply = toApply;
            this.parent = parent;
            this.toReplace = toReplace;
        }

        @Override
        public Sentence apply() {
            Sentence replaceWith = toApply.apply();
            return parent.replace(toReplace, replaceWith);
        }
    }

}
