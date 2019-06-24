package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.State;

public interface GoalTester {

    boolean goalReached(State toTest);

}
