package net.viperfish.ai.problemSolver;

import java.util.List;

public interface ProblemSolver<S extends State> {

    public List<Action<S>> solve(S initialState, GoalTester<S> goalStates);


}
