package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.State;

import java.util.ArrayList;
import java.util.List;

public class RandomRestartHillClimb implements LocalSearch {

    private LocalSearch search;
    private Randomizer randomizer;

    public RandomRestartHillClimb(LocalSearch searcher, Randomizer randomizer) {
        this.search = searcher;
        this.randomizer = randomizer;
    }

    @Override
    public State solve(Iterable<? extends State> initialStates, ObjectiveFunction objectiveFunction, GoalTester goalTester) {
        State solution = null;
        List<State> initial = new ArrayList<>();
        initialStates.forEach(initial::add);
        while (solution == null) {
            solution = search.solve(initialStates, objectiveFunction, goalTester);
            initialStates = randomizer.randomState(initial.size());
        }
        return solution;
    }
}
