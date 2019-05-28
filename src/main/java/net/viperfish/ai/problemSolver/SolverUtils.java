package net.viperfish.ai.problemSolver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

final class SolverUtils {

    static <S extends State> List<Action<S>> collect(Map<State, SearchNode<S>> parentTree, State goalState) {
        SearchNode<S> parent = parentTree.get(goalState);
        List<Action<S>> result = new LinkedList<>();
        while(parent != null) {
            result.add(parent.getActionTaken());
            parent = parentTree.get(parent.getParent());
        }
        Collections.reverse(result);
        return result;
    }

}
