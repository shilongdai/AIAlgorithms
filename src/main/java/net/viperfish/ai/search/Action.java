package net.viperfish.ai.search;

public interface Action {

    State predict(State current);

    State execute(State current) throws Exception;

    double cost();

    Action reverse();

}
