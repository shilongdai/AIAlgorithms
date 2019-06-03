package net.viperfish.ai.localSearch;

import net.viperfish.ai.classicSearch.GoalTester;
import net.viperfish.ai.classicSearch.State;

public abstract class HillClimbSearch<S extends State> implements LocalSearch<S> {

    public abstract S solve(S initialStates, ObjectiveFunction<S> objectiveFunction, GoalTester<S> goalTester);

    @Override
    public S solve(Iterable<S> initialStates, ObjectiveFunction<S> objectiveFunction, GoalTester<S> goalTester) {
        if (!initialStates.iterator().hasNext()) {
            throw new IllegalArgumentException("The initial states must not be empty");
        }
        return solve(initialStates.iterator().next(), objectiveFunction, goalTester);
    }
}
