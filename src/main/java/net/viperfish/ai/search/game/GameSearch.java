package net.viperfish.ai.search.game;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;
import net.viperfish.ai.search.deterministic.HeuristicGoalTester;

public interface GameSearch {

    Action search(State current, HeuristicGoalTester goalTester, boolean max);

}
