package net.viperfish.ai.classicSearch;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

final class SolverUtils {

    private SolverUtils() {

    }

    static <S extends State> List<Action<S>> collect(SearchNode<S> goalState) {
        SearchNode<S> parent = goalState.getParent();
        List<Action<S>> result = new LinkedList<>();
        result.add(goalState.getActionTaken());
        while (parent != null && parent.getActionTaken() != null) {
            result.add(parent.getActionTaken());
            parent = parent.getParent();
        }
        Collections.reverse(result);
        return result;
    }

}
