package net.viperfish.ai.localSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.GoalTester;
import net.viperfish.ai.classicSearch.State;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class SimulatedAnnealingSearch<S extends State> implements LocalSearch<S> {
    @Override
    public S solve(Iterable<S> initialStates, ObjectiveFunction<S> objectiveFunction, GoalTester<S> goalTester) {
        S current = initialStates.iterator().next();
        int t = 1;
        Random rand = new Random();
        while (true) {
            if (goalTester.goalReached(current)) {
                return current;
            }
            double temperature = schedule(t);
            double currentVal = objectiveFunction.evaluate(current);
            List<Action<?>> actions = new LinkedList<>(current.availableActions());
            @SuppressWarnings("unchecked")
            Action<S> chosenAction = (Action<S>) actions.get(rand.nextInt(actions.size()));
            S successor = chosenAction.predict(current);
            double nextVal = objectiveFunction.evaluate(successor);
            if (nextVal > currentVal) {
                current = successor;
            } else {
                double delta = nextVal - currentVal;
                double prob = Math.pow(Math.E, delta / temperature);
                if (rand.nextDouble() < prob) {
                    current = successor;
                }
            }
        }
    }

    protected abstract double schedule(int t);
}
