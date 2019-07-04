package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.Candidate;
import net.viperfish.ai.search.State;

import java.util.*;

public class AStarSearch implements HeuristicProblemSolver {

    @Override
    public List<Action> solve(State initialState, HeuristicGoalTester goalStates) {
        if(goalStates.goalReached(initialState)) {
            return new LinkedList<>();
        }
        Map<State, HeuristicSearchNode> parentTree = new HashMap<>();
        PriorityQueue<Candidate> frontier = new PriorityQueue<>();
        frontier.offer(new Candidate(initialState, 0));
        parentTree.put(initialState, new HeuristicSearchNode(null, null, initialState, 0, 0, 0));
        while (!frontier.isEmpty()) {
            State toExpand = frontier.poll().getToExpand();
            HeuristicSearchNode current = parentTree.get(toExpand);
            if (goalStates.goalReached(toExpand)) {
                return SolverUtils.collect(current);
            }
            for (Action a : toExpand.availableActions()) {
                State next = a.execute(toExpand);
                double h = goalStates.heuristic(next);
                double g = current.getG() + a.cost();
                HeuristicSearchNode nextNode = new HeuristicSearchNode(current, a, next, h + g, g, h);
                if(parentTree.containsKey(next)) {
                    if (parentTree.get(next).getCost() > nextNode.getCost()) {
                        parentTree.put(next, nextNode);
                    } else {
                        continue;
                    }
                }
                parentTree.put(next, nextNode);
                frontier.offer(new Candidate(next, nextNode.getCost()));
            }
        }
        return new LinkedList<>();
    }
}
