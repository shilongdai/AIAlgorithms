package net.viperfish.ai.problemSolver;

public interface GoalTester<S extends State> {

    public boolean goalReached(S toTest);

}
