package net.viperfish.ai.problemSolver;

public interface State {

    public Iterable<? extends Action<? extends State>> availableActions();

}
