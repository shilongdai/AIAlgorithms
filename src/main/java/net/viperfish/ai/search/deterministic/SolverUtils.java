package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

final class SolverUtils {

    private SolverUtils() {

    }

    static List<Action> collect(SearchNode goalState) {
        SearchNode parent = goalState.getParent();
        List<Action> result = new LinkedList<>();
        result.add(goalState.getActionTaken());
        while (parent != null && parent.getActionTaken() != null) {
            result.add(parent.getActionTaken());
            parent = parent.getParent();
        }
        Collections.reverse(result);
        return result;
    }

}
