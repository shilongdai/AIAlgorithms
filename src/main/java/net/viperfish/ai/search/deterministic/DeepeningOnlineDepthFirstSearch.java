package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.Precept;
import net.viperfish.ai.search.State;
import net.viperfish.ai.search.StateActionPair;

import java.util.Map;

public class DeepeningOnlineDepthFirstSearch extends OnlineSearch {

    private GoalTester goalTester;
    private LimitedDepthFirstOnlineSearch current;
    private int limit;

    public DeepeningOnlineDepthFirstSearch(GoalTester goalTester) {
        this.goalTester = goalTester;
        current = new LimitedDepthFirstOnlineSearch(goalTester, 1);
        limit = 2;
    }

    @Override
    public Action next(Precept precept) {
        Action next = current.next(precept);
        if (finished()) {
            return null;
        }
        if (next == null) {
            current = new LimitedDepthFirstOnlineSearch(goalTester, limit++);
            next = current.next(precept);
        }
        return next;
    }

    @Override
    public Map<StateActionPair, State> explored() {
        return current.explored();
    }

    @Override
    public boolean finished() {
        return current.finished();
    }
}
