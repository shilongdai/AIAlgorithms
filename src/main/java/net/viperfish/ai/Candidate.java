package net.viperfish.ai;

import net.viperfish.ai.classicSearch.State;

import java.util.Objects;

public class Candidate<S extends State> implements Comparable<Candidate> {

        private S toExpand;
        private double cost;

        public Candidate(S toExpand, double cost) {
            this.toExpand = toExpand;
            this.cost = cost;
        }

        public S getToExpand() {
            return toExpand;
        }

        public void setToExpand(S toExpand) {
            this.toExpand = toExpand;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Candidate candidate = (Candidate) o;
            return Double.compare(candidate.cost, cost) == 0 &&
                    toExpand.equals(candidate.toExpand);
        }

        @Override
        public int hashCode() {
            return Objects.hash(toExpand, cost);
        }

        @Override
        public int compareTo(Candidate candidate) {
            return Double.compare(this.cost, candidate.cost);
        }
    }