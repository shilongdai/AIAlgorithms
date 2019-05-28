package net.viperfish.ai.problemSolver;

public interface Action<S extends State> {

    public S predict(S current);

    public S execute(S current) throws Exception;

    public double cost();

}
