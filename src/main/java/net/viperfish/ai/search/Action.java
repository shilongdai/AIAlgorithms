package net.viperfish.ai.search;

public interface Action {

    State execute(State current);

    double cost();

    Action reverse();

}
