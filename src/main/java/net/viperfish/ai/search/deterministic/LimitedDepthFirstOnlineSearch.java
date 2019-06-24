package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.Precept;
import net.viperfish.ai.search.State;
import net.viperfish.ai.search.StateActionPair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LimitedDepthFirstOnlineSearch extends OnlineSearch {

    private State previousState;
    private Action previousAction;
    private Map<StateActionPair, State> result;
    private Map<State, List<Action>> untried;
    private Map<State, List<Action>> unbacktracked;
    private GoalTester goalTester;
    private int limit;

    public LimitedDepthFirstOnlineSearch(GoalTester goalTester, int limit) {
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
    public Action next(Precept precept) {
        State preceived = precept.getState();
        if (goalTester.goalReached(preceived)) {
            previousState = preceived;
            return null;
        }
        if (!untried.containsKey(preceived)) {
            List<Action> newList = new LinkedList<>();
            for (Action a : preceived.availableActions()) {
                newList.add(a);
            }
            untried.put(preceived, newList);
        }
        if (previousState != null) {
            result.put(new StateActionPair(previousState, previousAction), preceived);
            if (!unbacktracked.containsKey(preceived)) {
                unbacktracked.put(preceived, new LinkedList<>());
            }
            Action backtrack = previousAction.reverse();
            if (backtrack != null) {
                unbacktracked.get(preceived).add(0, backtrack);
            }
        }
        if (untried.get(preceived).isEmpty()) {
            previousState = preceived;
            if (unbacktracked.get(preceived).isEmpty()) {
                return null;
            } else {
                Action nextAction = unbacktracked.get(preceived).get(0);
                unbacktracked.get(preceived).remove(0);
                limit += 1;
                previousAction = nextAction;
                return nextAction;
            }
        } else {
            Action nextAction = untried.get(preceived).get(0);
            untried.get(preceived).remove(0);
            limit -= 1;
            previousState = preceived;
            previousAction = nextAction;
            return nextAction;
        }
    }

    @Override
    public Map<StateActionPair, State> explored() {
        return result;
    }
}
