package net.viperfish.ai.search.partialObserve;

import net.viperfish.ai.search.State;
import net.viperfish.ai.search.deterministic.GoalTester;

public class BeliefStateGoalTester implements GoalTester {

    private GoalTester goalTester;

    public BeliefStateGoalTester(GoalTester goalTester) {
        this.goalTester = goalTester;
    }

    @Override
    public boolean goalReached(State toTest) {
        BeliefState beliefState = (BeliefState) toTest;
        for (ObservableState s : beliefState.possibilities()) {
            if (!goalTester.goalReached(s)) {
                return false;
            }
        }
        return true;
    }
}
