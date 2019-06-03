package net.viperfish.ai.localSearch;

import net.viperfish.ai.classicSearch.GoalTester;
import net.viperfish.ai.classicSearch.State;

public interface LocalSearch<S extends State> {

    S solve(Iterable<S> initialStates, ObjectiveFunction<S> objectiveFunction, GoalTester<S> goalTester);

}
