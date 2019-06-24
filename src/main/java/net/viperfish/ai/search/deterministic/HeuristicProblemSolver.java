package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.List;

public interface HeuristicProblemSolver {

    List<Action> solve(State initialState, HeuristicGoalTester goalStates);

}
