package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.GoalTester;
import net.viperfish.ai.classicSearch.State;

public interface PlanSearch<S extends State> {

    Plan<S> solve(S initialState, GoalTester<S> goalTester, PossibilityGenerator<S> possibilityGenerator);

}
