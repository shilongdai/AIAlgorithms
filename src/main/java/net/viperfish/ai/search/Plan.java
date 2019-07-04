package net.viperfish.ai.search;

public interface Plan<K> {

    Action next(K precept);

    boolean finished();

}
