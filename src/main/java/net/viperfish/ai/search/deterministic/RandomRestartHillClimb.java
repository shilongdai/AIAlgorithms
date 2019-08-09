package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.State;

import java.util.ArrayList;
import java.util.List;

public class RandomRestartHillClimb implements LocalSearch {

    private LocalSearch search;
    private Randomizer randomizer;
    private int limit;

    public RandomRestartHillClimb(LocalSearch searcher, Randomizer randomizer, int limit) {
        this.search = searcher;
        this.randomizer = randomizer;
        this.limit = limit;
    }

    @Override
    public State solve(Iterable<? extends State> initialStates, ObjectiveFunction objectiveFunction, GoalTester goalTester) {
        State solution = null;
        List<State> initial = new ArrayList<>();
        initialStates.forEach(initial::add);
        int current = 0;
        while (solution == null || !goalTester.goalReached(solution)) {
            if (current++ > limit) {
                return solution;
            }
            solution = search.solve(initialStates, objectiveFunction, goalTester);
            initialStates = randomizer.randomState(initial.size());
        }
        return solution;
    }
}
