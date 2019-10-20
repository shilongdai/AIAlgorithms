package net.viperfish.ai.propLogic;

import net.viperfish.ai.search.State;

import java.util.Collection;
import java.util.Map;

public interface Sentence extends State {

    boolean evaluates(Map<String, Boolean> literals);

    Collection<Rule<? extends Sentence>> applicableRules();

    Collection<Sentence> children();
}
