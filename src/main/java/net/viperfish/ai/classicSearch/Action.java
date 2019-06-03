package net.viperfish.ai.classicSearch;

public interface Action<S extends State> {

    S predict(S current);

    S execute(S current) throws Exception;

    double cost();

}
