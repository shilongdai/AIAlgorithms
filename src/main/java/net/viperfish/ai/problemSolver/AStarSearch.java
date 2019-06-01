package net.viperfish.ai.problemSolver;

import java.util.*;

public class AStarSearch<S extends State> implements HeuristicProblemSolver<S> {
    @Override
    public List<Action<S>> solve(S initialState, HeuristicGoalTester<S> goalStates) {
        if(goalStates.goalReached(initialState)) {
            return new LinkedList<>();
        }
        Map<State, HeuristicSearchNode<S>> parentTree = new HashMap<>();
        PriorityQueue<Candidate<S>> frontier = new PriorityQueue<>();
        frontier.offer(new Candidate<>(initialState, 0));
        parentTree.put(initialState, new HeuristicSearchNode<>(null, null, initialState, 0, 0, 0));
        while (!frontier.isEmpty()) {
            S toExpand = frontier.poll().getToExpand();
            HeuristicSearchNode<S> current = parentTree.get(toExpand);
            if (goalStates.goalReached(toExpand)) {
                return SolverUtils.collect(current);
            }
            for (Action<? extends State> action : toExpand.availableActions()) {
                @SuppressWarnings("unchecked")
                Action<S> a = (Action<S>) action;
                S next = a.predict(toExpand);
                double h = goalStates.heuristic(next);
                double g = current.getG() + a.cost();
                HeuristicSearchNode<S> nextNode = new HeuristicSearchNode<>(current, a, next, h + g, g, h);
                if(parentTree.containsKey(next)) {
                    if (parentTree.get(next).getCost() > nextNode.getCost()) {
                        parentTree.put(next, nextNode);
                    } else {
                        continue;
                    }
                }
                parentTree.put(next, nextNode);
                frontier.offer(new Candidate<>(next, nextNode.getCost()));
            }
        }
        return new LinkedList<>();
    }
}
