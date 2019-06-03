package net.viperfish.ai.classicSearch;

import net.viperfish.ai.Candidate;

import java.util.*;

public class UniformSearch<S extends State> implements ProblemSolver<S> {

    @Override
    public List<Action<S>> solve(S initialState, GoalTester<S> goalStates) {
        if(goalStates.goalReached(initialState)) {
            return new LinkedList<>();
        }
        PriorityQueue<Candidate<S>> frontier = new PriorityQueue<>();
        frontier.offer(new Candidate<>(initialState, 0));
        Map<S, SearchNode<S>> nodeTracker = new HashMap<>();
        SearchNode<S> init = new SearchNode<>(null, null, initialState, 0);
        nodeTracker.put(initialState, init);
        while (!frontier.isEmpty()) {
            S toExpand = frontier.poll().getToExpand();
            SearchNode<S> current = nodeTracker.get(toExpand);
            if (goalStates.goalReached(toExpand)) {
                return SolverUtils.collect(current);
            }
            for (Action<? extends State> action : toExpand.availableActions()) {
                @SuppressWarnings("unchecked")
                Action<S> a = (Action<S>) action;
                S next = a.predict(toExpand);
                double nextCost = current.getCost() + a.cost();
                SearchNode<S> nextNode = new SearchNode<>(current, a, next, nextCost);
                if (nodeTracker.containsKey(next)) {
                    if (nodeTracker.get(next).getCost() > nextCost) {
                        nodeTracker.put(next, nextNode);
                    } else {
                        continue;
                    }
                }
                nodeTracker.put(next, new SearchNode<>(nextNode, a, next, nextCost));
                frontier.offer(new Candidate<>(next, nextCost));
            }
        }
        return new LinkedList<>();
    }

}
