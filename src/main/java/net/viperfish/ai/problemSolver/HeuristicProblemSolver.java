package net.viperfish.ai.problemSolver;

import java.util.List;

public interface HeuristicProblemSolver<S extends State> {

    public List<Action<S>> solve(S initialState, HeuristicGoalTester<S> goalStates);

}
