package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.GoalTester;
import net.viperfish.ai.classicSearch.State;

import java.util.Map;

public class DeepeningOnlineDepthFirstSearch<S extends State> extends OnlineSearch<S> {

    private GoalTester<S> goalTester;
    private LimitedDepthFirstOnlineSearch<S> current;
    private int limit;

    public DeepeningOnlineDepthFirstSearch(GoalTester<S> goalTester) {
        this.goalTester = goalTester;
        current = new LimitedDepthFirstOnlineSearch<>(goalTester, 1);
        limit = 2;
    }

    @Override
    public Action<S> next(DeterministicPrecept<S> precept) {
        Action<S> next = current.next(precept);
        if (finished()) {
            return null;
        }
        if (next == null) {
            current = new LimitedDepthFirstOnlineSearch<>(goalTester, limit++);
            next = current.next(precept);
        }
        return next;
    }

    @Override
    public Map<StateActionPair<S>, S> explored() {
        return current.explored();
    }

    @Override
    public boolean finished() {
        return current.finished();
    }
}
