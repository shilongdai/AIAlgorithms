package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class SimulatedAnnealingSearch implements LocalSearch {

    private int maxTemp;

    public SimulatedAnnealingSearch(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    @Override
    public State solve(Iterable<? extends State> initialStates, ObjectiveFunction objectiveFunction, GoalTester goalTester) {
        if (!initialStates.iterator().hasNext()) {
            throw new IllegalArgumentException("The initial states cannot be empty");
        }
        State current = initialStates.iterator().next();
        int t = 1;
        Random rand = new Random();
        while (t++ < maxTemp) {
            if (goalTester.goalReached(current)) {
                return current;
            }
            double temperature = schedule(t);
            double currentVal = objectiveFunction.evaluate(current);
            List<Action> actions = new LinkedList<>(current.availableActions());
            Action chosenAction = actions.get(rand.nextInt(actions.size()));
            State successor = chosenAction.execute(current);
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
        return current;
    }

    protected abstract double schedule(int t);
}
