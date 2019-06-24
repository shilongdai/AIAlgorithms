package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.State;

public interface ObjectiveFunction {

    double evaluate(State state);

}
