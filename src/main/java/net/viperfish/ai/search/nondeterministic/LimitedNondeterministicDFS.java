package net.viperfish.ai.search.nondeterministic;

import net.viperfish.ai.search.*;
import net.viperfish.ai.search.deterministic.GoalTester;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LimitedNondeterministicDFS extends LimitedDepthFirstAndOrSearch<NondeterministicState> implements NondeterministicSearch {

    private int limit;

    public LimitedNondeterministicDFS(int limit) {
        this.limit = limit;
    }

    @Override
    public Plan<NondeterministicState> search(NondeterministicState initial, GoalTester goalReached) {
        ANDNode<NondeterministicState> initialNode = new ANDNode<>(null, initial);
        ORNode<NondeterministicState> result = this.searchAndNode(initialNode, goalReached, new HashSet<>(), limit);
        initialNode.setNext(result);
        if (result != null) {
            return new AndOrPlan<>(initialNode);
        }
        return null;
    }

    @Override
    protected Map<NondeterministicState, ? extends State> getPossibility(State current, Action actionTaken) {
        NondeterministicAction actualAction = (NondeterministicAction) actionTaken;
        Map<NondeterministicState, NondeterministicState> result = new HashMap<>();
        for (NondeterministicState s : ((NondeterministicAction) actionTaken).possibilities((NondeterministicState) current)) {
            result.put(s, s);
        }
        return result;
    }

}
