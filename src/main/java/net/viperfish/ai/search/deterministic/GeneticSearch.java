package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticSearch implements LocalSearch {

    private double mutationChance;
    private Genetic gene;
    private int limit;

    public GeneticSearch(double mutationChance, Genetic gene, int limit) {
        this.mutationChance = mutationChance;
        this.limit = limit;
        this.gene = gene;
    }

    @Override
    public State solve(Iterable<? extends State> initialStates, ObjectiveFunction objectiveFunction, GoalTester goalTester) {
        List<State> population = new ArrayList<>();
        Random rand = new Random();
        initialStates.forEach(population::add);
        int current = 0;
        while (current++ < limit) {
            RandomCollection<State> selector = new RandomCollection<>(rand);
            for (State s : population) {
                if (goalTester.goalReached(s)) {
                    return s;
                }
                selector.add(objectiveFunction.evaluate(s), s);
            }
            int k = population.size();
            population.clear();
            for (int i = 0; i < k; ++i) {
                State x = selector.next();
                State y = selector.next();
                State offspring = gene.reproduce(x, y);
                if (rand.nextDouble() < mutationChance) {
                    offspring = gene.mutate(offspring);
                }
                population.add(offspring);
            }
        }
        return bestOfPop(population, objectiveFunction);
    }

    private State bestOfPop(List<State> pop, ObjectiveFunction f) {
        State best = null;
        double bestVal = Double.NEGATIVE_INFINITY;
        for (State s : pop) {
            double v = f.evaluate(s);
            if (v > bestVal) {
                best = s;
                bestVal = v;
            }
        }
        return best;
    }
}
