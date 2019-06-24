package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.List;

public interface ProblemSolver {

    List<Action> solve(State initialState, GoalTester goalStates);


}
