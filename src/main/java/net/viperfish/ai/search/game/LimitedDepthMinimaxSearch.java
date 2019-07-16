package net.viperfish.ai.search.game;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;
import net.viperfish.ai.search.deterministic.HeuristicGoalTester;

public class LimitedDepthMinimaxSearch implements GameSearch {

    private int limit;

    public LimitedDepthMinimaxSearch(int limit) {
        this.limit = limit;
    }

    @Override
    public Action search(State current, HeuristicGoalTester goalTester, boolean max) {
        Action result = null;
        double currentBest;
        if (max) {
            currentBest = Double.NEGATIVE_INFINITY;
        } else {
            currentBest = Double.POSITIVE_INFINITY;
        }
        for (Action a : current.availableActions()) {
            State candidate = a.execute(current);
            double heuristic = searchMax(candidate, goalTester, this.limit);
            if (max) {
                if (heuristic > currentBest) {
                    result = a;
                    currentBest = heuristic;
                }
            } else {
                if (heuristic < currentBest) {
                    result = a;
                    currentBest = heuristic;
                }
            }
        }
        return result;
    }

    private double searchMin(State current, HeuristicGoalTester goalTester, int limit) {
        if (goalTester.goalReached(current)) {
            return handleGameOver(current, goalTester);
        }
        if (limit == 0) {
            return goalTester.heuristic(current);
        }

        double currentMin = Double.POSITIVE_INFINITY;
        for (Action a : current.availableActions()) {
            State candidate = a.execute(current);
            double minimax = searchMax(candidate, goalTester, limit - 1);
            if (minimax < currentMin) {
                currentMin = minimax;
            }
        }
        return currentMin;
    }

    private double searchMax(State current, HeuristicGoalTester goalTester, int limit) {
        if (goalTester.goalReached(current)) {
            return handleGameOver(current, goalTester);
        }
        if (limit == 0) {
            return goalTester.heuristic(current);
        }

        double currentMax = Double.NEGATIVE_INFINITY;
        for (Action a : current.availableActions()) {
            State candidate = a.execute(current);
            double minimax = searchMin(candidate, goalTester, limit - 1);
            if (minimax > currentMax) {
                currentMax = minimax;
            }
        }
        return currentMax;
    }

    private double handleGameOver(State current, HeuristicGoalTester goalTester) {
        double h = goalTester.heuristic(current);
        if (h > 0) {
            return Double.POSITIVE_INFINITY;
        } else if (h < 0) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return 0;
        }
    }

}
