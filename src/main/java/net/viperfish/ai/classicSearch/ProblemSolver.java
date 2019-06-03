package net.viperfish.ai.classicSearch;

import java.util.List;

public interface ProblemSolver<S extends State> {

    List<Action<S>> solve(S initialState, GoalTester<S> goalStates);


}
