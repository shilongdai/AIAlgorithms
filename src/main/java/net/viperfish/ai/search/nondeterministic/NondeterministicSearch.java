package net.viperfish.ai.search.nondeterministic;

import net.viperfish.ai.search.Plan;
import net.viperfish.ai.search.deterministic.GoalTester;

public interface NondeterministicSearch {

    Plan<NondeterministicState> search(NondeterministicState initial, GoalTester goalReached);

}
