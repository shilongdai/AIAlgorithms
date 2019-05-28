package net.viperfish.ai.problemSolver;

import java.util.*;

public class AStarSearch<S extends State> implements HeuristicProblemSolver<S> {
    @Override
    public List<Action<S>> solve(S initialState, HeuristicGoalTester<S> goalStates) {
        if(goalStates.goalReached(initialState)) {
            return new LinkedList<>();
        }
        Map<State, SearchNode<S>> parentTree = new HashMap<>();
        PriorityQueue<Candidate<S>> frontier = new PriorityQueue<>();
        frontier.offer(new Candidate<>(initialState, 0));
        parentTree.put(initialState, new SearchNode<>(null, null, initialState, 0));
        while (!frontier.isEmpty()) {
            S toExpand = frontier.poll().getToExpand();
            if (goalStates.goalReached(toExpand)) {
                parentTree.remove(initialState);
                return SolverUtils.collect(parentTree, toExpand);
            }
            for (Action<? extends State> action : toExpand.availableActions()) {
                @SuppressWarnings("unchecked")
                Action<S> a = (Action<S>) action;
                S next = a.predict(toExpand);
                double nextCost = parentTree.get(toExpand).getCost() + a.cost() + goalStates.heuristic(next);
                if(parentTree.containsKey(next)) {
                    if(parentTree.get(next).getCost() > nextCost) {
                        parentTree.put(next, new SearchNode<>(toExpand, a, next, nextCost));
                    } else {
                        continue;
                    }
                }
                parentTree.put(next, new SearchNode<>(toExpand, a, next, nextCost));
                frontier.offer(new Candidate<>(next, nextCost));
            }
        }
        return new LinkedList<>();
    }
}
