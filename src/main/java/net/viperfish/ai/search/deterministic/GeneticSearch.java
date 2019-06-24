package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticSearch implements LocalSearch {

    private double mutationChance;
    private Genetic gene;

    public GeneticSearch(double mutationChance, Genetic gene) {
        this.mutationChance = mutationChance;
        this.gene = gene;
    }

    @Override
    public State solve(Iterable<? extends State> initialStates, ObjectiveFunction objectiveFunction, GoalTester goalTester) {
        List<State> population = new ArrayList<>();
        Random rand = new Random();
        initialStates.forEach(population::add);
        while (true) {
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
    }
}
