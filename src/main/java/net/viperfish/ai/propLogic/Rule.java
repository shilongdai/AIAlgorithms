package net.viperfish.ai.propLogic;

public interface Rule<S extends Sentence> {

    S apply();

}
