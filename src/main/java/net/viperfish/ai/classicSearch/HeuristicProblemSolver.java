package net.viperfish.ai.classicSearch;

import java.util.List;

public interface HeuristicProblemSolver<S extends State> {

    List<Action<S>> solve(S initialState, HeuristicGoalTester<S> goalStates);

}
