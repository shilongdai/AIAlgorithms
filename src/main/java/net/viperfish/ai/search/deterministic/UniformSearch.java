package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.Candidate;
import net.viperfish.ai.search.State;

import java.util.*;

public class UniformSearch implements ProblemSolver {

    @Override
    public List<Action> solve(State initialState, GoalTester goalStates) {
        if (goalStates.goalReached(initialState)) {
            return new LinkedList<>();
        }
        PriorityQueue<Candidate> frontier = new PriorityQueue<>();
        frontier.offer(new Candidate(initialState, 0));
        Map<State, SearchNode> nodeTracker = new HashMap<>();
        SearchNode init = new SearchNode(null, null, initialState, 0);
        nodeTracker.put(initialState, init);
        while (!frontier.isEmpty()) {
            State toExpand = frontier.poll().getToExpand();
            SearchNode current = nodeTracker.get(toExpand);
            if (goalStates.goalReached(toExpand)) {
                return SolverUtils.collect(current);
            }
            for (Action a : toExpand.availableActions()) {
                State next = a.predict(toExpand);
                double nextCost = current.getCost() + a.cost();
                SearchNode nextNode = new SearchNode(current, a, next, nextCost);
                if (nodeTracker.containsKey(next)) {
                    if (nodeTracker.get(next).getCost() > nextCost) {
                        nodeTracker.put(next, nextNode);
                    } else {
                        continue;
                    }
                }
                nodeTracker.put(next, new SearchNode(nextNode, a, next, nextCost));
                frontier.offer(new Candidate(next, nextCost));
            }
        }
        return new LinkedList<>();
    }

}
