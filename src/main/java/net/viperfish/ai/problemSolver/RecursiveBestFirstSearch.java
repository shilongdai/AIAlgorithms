package net.viperfish.ai.problemSolver;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class RecursiveBestFirstSearch<S extends State> implements HeuristicProblemSolver<S> {

    @Override
    public List<Action<S>> solve(S initialState, HeuristicGoalTester<S> goalStates) {
        HeuristicSearchNode<S> result = recursive_BFS(new HeuristicSearchNode<>(null, null, initialState, 0, 0, 0), goalStates, Double.POSITIVE_INFINITY);
        if (result.getCurrent() == null) {
            return new LinkedList<>();
        }
        return SolverUtils.collect(result);
    }

    private HeuristicSearchNode<S> recursive_BFS(HeuristicSearchNode<S> current, HeuristicGoalTester<S> goalTester, double fLimit) {
        if (goalTester.goalReached(current.getCurrent())) {
            return current;
        }
        PriorityQueue<HeuristicSearchNode<S>> successors = new PriorityQueue<>();
        for (Action<?> a : current.getCurrent().availableActions()) {
            @SuppressWarnings("unchecked")
            Action<S> action = (Action<S>) a;
            S next = action.predict(current.getCurrent());
            double g = current.getG() + action.cost();
            double h = goalTester.heuristic(next);
            HeuristicSearchNode<S> nextNode = new HeuristicSearchNode<>(current, action, next, Math.max(current.getCost(), g + h), g, h);
            successors.add(nextNode);
        }
        while (!successors.isEmpty()) {
            HeuristicSearchNode<S> best = successors.poll();
            HeuristicSearchNode<S> alternative = successors.peek();
            if (alternative == null) {
                alternative = new HeuristicSearchNode<>(null, null, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            }
            if (best.getCost() > fLimit) {
                best.setCurrent(null);
                return best;
            }
            HeuristicSearchNode<S> result = recursive_BFS(best, goalTester, Math.min(fLimit, alternative.getCost()));
            if (result.getCurrent() != null) {
                return result;
            }
            best.setCost(result.getCost());
            successors.add(best);
        }
        return new HeuristicSearchNode<>(null, null, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

}
