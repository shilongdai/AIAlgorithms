package net.viperfish.ai.queen;

import net.viperfish.ai.search.deterministic.Randomizer;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NQueenRandomizer implements Randomizer {

    private int n;

    public NQueenRandomizer(int n) {
        this.n = n;
    }

    @Override
    public Iterable<NQueenProblem> randomState(int amount) {
        Random rand = new Random();
        Set<NQueenProblem> result = new HashSet<>();
        while (result.size() != amount) {
            NQueenProblem problem = new NQueenProblem(n);
            for (int i = 0; i < n; ++i) {
                problem.placeQueen(i, rand.nextInt(n));
            }
            result.add(problem);
        }
        return result;
    }

    @Override
    public NQueenProblem randomState() {
        return randomState(1).iterator().next();
    }
}
