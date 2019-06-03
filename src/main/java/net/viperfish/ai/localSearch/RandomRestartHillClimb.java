package net.viperfish.ai.localSearch;

import net.viperfish.ai.classicSearch.GoalTester;
import net.viperfish.ai.classicSearch.State;

public class RandomRestartHillClimb<S extends State> extends HillClimbSearch<S> {

    private HillClimbSearch<S> search;
    private Randomizer<S> randomizer;

    public RandomRestartHillClimb(HillClimbSearch<S> searcher, Randomizer<S> randomizer) {
        this.search = searcher;
        this.randomizer = randomizer;
    }

    @Override
    public S solve(S initialStates, ObjectiveFunction<S> objectiveFunction, GoalTester<S> goalTester) {
        S solution = null;
        while (solution == null) {
            solution = search.solve(initialStates, objectiveFunction, goalTester);
            initialStates = randomizer.randomState();
        }
        return solution;
    }
}
