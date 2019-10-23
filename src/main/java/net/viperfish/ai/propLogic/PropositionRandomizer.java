package net.viperfish.ai.propLogic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;
import net.viperfish.ai.search.deterministic.Randomizer;

import java.util.*;

public class PropositionRandomizer implements Randomizer {

    private int randomStep;
    private Sentence original;

    public PropositionRandomizer(int randomStep, Sentence original) {
        this.randomStep = randomStep;
        this.original = original;
    }

    @Override
    public Iterable<? extends State> randomState(int amount) {
        Set<Sentence> result = new HashSet<>();
        while (result.size() != amount) {
            result.add((Sentence) randomState());
        }
        return result;
    }

    @Override
    public State randomState() {
        Random rand = new Random();
        Sentence result = original;
        for (int i = 0; i < randomStep; ++i) {
            List<Action> rules = new ArrayList<>(original.availableActions());
            if (rules.size() == 0) {
                return result;
            }
            Action toApply = rules.get(rand.nextInt(rules.size()));
            result = (Sentence) toApply.execute(result);
        }
        return result;
    }
}
