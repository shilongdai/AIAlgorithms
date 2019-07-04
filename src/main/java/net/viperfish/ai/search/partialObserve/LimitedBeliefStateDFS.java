package net.viperfish.ai.search.partialObserve;

import net.viperfish.ai.search.*;
import net.viperfish.ai.search.deterministic.GoalTester;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LimitedBeliefStateDFS extends LimitedDepthFirstAndOrSearch<Precept> implements BeliefStateSearch {

    private int limit;

    public LimitedBeliefStateDFS(int limit) {
        this.limit = limit;
    }

    @Override
    protected Map<Precept, ? extends State> getPossibility(State current, Action actionTaken) {
        Map<Precept, BeliefState> result = new HashMap<>();
        BeliefState currentBelief = (BeliefState) current;
        Set<ObservableState> stateBuffer = new HashSet<>();
        for (ObservableState s : currentBelief.possibilities()) {
            stateBuffer.add((ObservableState) actionTaken.execute(s));
        }
        currentBelief = new BeliefState(stateBuffer);

        for (Precept p : currentBelief.possiblePrecepts()) {
            Set<ObservableState> buffer = new HashSet<>();
            for (ObservableState s : currentBelief.possibilities()) {
                if (s.possibleObservations().contains(p)) {
                    buffer.add(s);
                }
            }
            result.put(p, new BeliefState(buffer));
        }
        return result;
    }

    @Override
    public Plan<Precept> solve(BeliefState initial, GoalTester goalTester) {
        ANDNode<Precept> initialNode = new ANDNode<>(null, initial);
        ORNode<Precept> result = this.searchAndNode(initialNode, goalTester, new HashSet<>(), limit);
        initialNode.setNext(result);
        if (result != null) {
            return new AndOrPlan<>(initialNode);
        }
        return null;
    }
}
