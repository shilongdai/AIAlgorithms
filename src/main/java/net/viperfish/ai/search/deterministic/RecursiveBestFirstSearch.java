package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class RecursiveBestFirstSearch implements HeuristicProblemSolver {

    @Override
    public List<Action> solve(State initialState, HeuristicGoalTester goalStates) {
        HeuristicSearchNode result = recursive_BFS(new HeuristicSearchNode(null, null, initialState, 0, 0, 0), goalStates, Double.POSITIVE_INFINITY);
        if (result.getCurrent() == null) {
            return new LinkedList<>();
        }
        return SolverUtils.collect(result);
    }

    private HeuristicSearchNode recursive_BFS(HeuristicSearchNode current, HeuristicGoalTester goalTester, double fLimit) {
        if (goalTester.goalReached(current.getCurrent())) {
            return current;
        }
        PriorityQueue<HeuristicSearchNode> successors = new PriorityQueue<>();
        for (Action action : current.getCurrent().availableActions()) {
            State next = action.predict(current.getCurrent());
            double g = current.getG() + action.cost();
            double h = goalTester.heuristic(next);
            HeuristicSearchNode nextNode = new HeuristicSearchNode(current, action, next, Math.max(current.getCost(), g + h), g, h);
            successors.add(nextNode);
        }
        while (!successors.isEmpty()) {
            HeuristicSearchNode best = successors.poll();
            HeuristicSearchNode alternative = successors.peek();
            if (alternative == null) {
                alternative = new HeuristicSearchNode(null, null, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            }
            if (best.getCost() > fLimit) {
                best.setCurrent(null);
                return best;
            }
            HeuristicSearchNode result = recursive_BFS(best, goalTester, Math.min(fLimit, alternative.getCost()));
            if (result.getCurrent() != null) {
                return result;
            }
            best.setCost(result.getCost());
            successors.add(best);
        }
        return new HeuristicSearchNode(null, null, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

}
