package net.viperfish.ai.classicSearch;

public interface GoalTester<S extends State> {

    boolean goalReached(S toTest);

}
