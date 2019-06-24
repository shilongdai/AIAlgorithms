package net.viperfish.ai.search;

public interface Plan {

    Action next(Precept precept);

    boolean finished();

}
