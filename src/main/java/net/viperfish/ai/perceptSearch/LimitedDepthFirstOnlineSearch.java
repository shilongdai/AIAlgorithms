package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.GoalTester;
import net.viperfish.ai.classicSearch.State;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LimitedDepthFirstOnlineSearch<S extends State> extends OnlineSearch<S> {

    private S previousState;
    private Action<S> previousAction;
    private Map<StateActionPair<S>, S> result;
    private Map<S, List<Action<S>>> untried;
    private Map<S, List<Action<S>>> unbacktracked;
    private GoalTester<S> goalTester;
    private int limit;

    public LimitedDepthFirstOnlineSearch(GoalTester<S> goalTester, int limit) {
        this.goalTester = goalTester;
        previousAction = null;
        previousState = null;
        result = new HashMap<>();
        unbacktracked = new HashMap<>();
        untried = new HashMap<>();
        this.limit = limit;
    }

    @Override
    public boolean finished() {
        if (previousState == null) {
            return false;
        }
        return goalTester.goalReached(previousState);
    }

    @Override
    public Action<S> next(DeterministicPrecept<S> precept) {
        S preceived = precept.getState();
        if (goalTester.goalReached(preceived)) {
            previousState = preceived;
            return null;
        }
        if (!untried.containsKey(preceived)) {
            List<Action<S>> newList = new LinkedList<>();
            for (Action<?> a : preceived.availableActions()) {
                newList.add((Action<S>) a);
            }
            untried.put(preceived, newList);
        }
        if (previousState != null) {
            result.put(new StateActionPair<>(previousState, previousAction), preceived);
            if (!unbacktracked.containsKey(preceived)) {
                unbacktracked.put(preceived, new LinkedList<>());
            }
            Action<S> backtrack = previousAction.reverse();
            if (backtrack != null) {
                unbacktracked.get(preceived).add(0, backtrack);
            }
        }
        if (untried.get(preceived).isEmpty()) {
            previousState = preceived;
            if (unbacktracked.get(preceived).isEmpty()) {
                return null;
            } else {
                Action<S> nextAction = unbacktracked.get(preceived).get(0);
                unbacktracked.get(preceived).remove(0);
                limit += 1;
                previousAction = nextAction;
                return nextAction;
            }
        } else {
            Action<S> nextAction = untried.get(preceived).get(0);
            untried.get(preceived).remove(0);
            limit -= 1;
            previousState = preceived;
            previousAction = nextAction;
            return nextAction;
        }
    }

    @Override
    public Map<StateActionPair<S>, S> explored() {
        return result;
    }
}
