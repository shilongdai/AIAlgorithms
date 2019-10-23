package net.viperfish.ai.propLogic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ActionGeneratingSentence extends CompositeSentence {

    @Override
    public Collection<? extends Action> availableActions() {
        List<Action> result = new ArrayList<>();
        for (Rule<? extends Sentence> r : this.applicableRules()) {
            result.add(new RuleBasedAction(r));
        }
        for (Rule<? extends Sentence> r : this.replacementRules()) {
            result.add(new RuleBasedAction(r));
        }
        return result;
    }

    private static class RuleBasedAction implements Action {

        private Rule<? extends Sentence> rule;

        public RuleBasedAction(Rule<? extends Sentence> rule) {
            this.rule = rule;
        }

        @Override
        public State execute(State current) {
            return rule.apply();
        }

        @Override
        public double cost() {
            return 0;
        }

        @Override
        public Action reverse() {
            return null;
        }
    }

}
