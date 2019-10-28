package net.viperfish.ai.propLogic;

import java.util.*;

public final class CNFUtils {

    private CNFUtils() {
    }

    public static ConjunctSentence toCNF(Sentence sentence) {
        if (sentence instanceof LiteralSentence) {
            return literalToCNF((LiteralSentence) sentence);
        }

        if (sentence instanceof ConjunctSentence) {
            return conjunctToCNF((ConjunctSentence) sentence);
        }
        if (sentence instanceof DisjunctSentence) {
            return disjunctToCNF((DisjunctSentence) sentence);
        }

        if (sentence instanceof NegateSentence) {
            return negateToCNF((NegateSentence) sentence);
        }
        if (sentence instanceof ImplicationSentence) {
            return implicationToCNF((ImplicationSentence) sentence);
        }
        if (sentence instanceof EquivalenceSentence) {
            return equivalenceToCNF((EquivalenceSentence) sentence);
        }
        throw new IllegalArgumentException("Unidentified propositional sentence type: " + sentence);
    }

    public static ConjunctSentence literalToCNF(LiteralSentence sentence) {
        return new ConjunctSentence(Collections.singletonList(sentence));
    }

    public static ConjunctSentence conjunctToCNF(ConjunctSentence sentence) {
        Set<Sentence> conjunctSet = new HashSet<>();
        for (Sentence c : sentence.children()) {
            conjunctSet.addAll(CNFUtils.toCNF(c).children());
        }
        return new ConjunctSentence(conjunctSet);
    }

    public static ConjunctSentence disjunctToCNF(DisjunctSentence sentence) {
        Set<Sentence> conjunctSet = new HashSet<>();
        int depth = sentence.children().size();
        Map<Integer, List<Sentence>> tracker = new HashMap<>();
        Map<Integer, Integer> progressTracker = new HashMap<>();
        List<Sentence> sentenceChildren = new ArrayList<>(sentence.children());
        Stack<Integer> processingStack = new Stack<>();
        for (int i = 0; i < depth; ++i) {
            processingStack.push(i);
            Sentence children = sentenceChildren.get(i);
            tracker.put(i, new ArrayList<>(CNFUtils.toCNF(children).children()));
            progressTracker.put(i, 0);
        }

        while (!processingStack.empty()) {
            int next = processingStack.pop();
            if (next == depth - 1) {
                Set<Sentence> disjunctSet = new HashSet<>();
                for (Map.Entry<Integer, List<Sentence>> e : tracker.entrySet()) {
                    List<Sentence> cnfDisjuncts = e.getValue();
                    int progress = progressTracker.get(e.getKey());
                    Sentence cnfPart = cnfDisjuncts.get(progress);
                    if (cnfPart instanceof LiteralSentence || cnfPart instanceof NegateSentence) {
                        disjunctSet.add(cnfPart);
                    }
                    if (cnfPart instanceof DisjunctSentence) {
                        disjunctSet.addAll(cnfPart.children());
                    }
                }
                conjunctSet.add(new DisjunctSentence(disjunctSet));
            }
            progressTracker.put(next, progressTracker.get(next) + 1);

            if (progressTracker.get(next) != tracker.get(next).size()) {
                if (next != depth - 1) {
                    processingStack.push(next + 1);
                } else {
                    processingStack.push(next);
                }
            } else {
                progressTracker.put(next, 0);
            }
        }
        return new ConjunctSentence(conjunctSet);
    }

    public static ConjunctSentence negateToCNF(NegateSentence sentence) {
        Sentence original = sentence.original();
        if (original instanceof NegateSentence) {
            Sentence doubleNegative = ((NegateSentence) original).original();
            return CNFUtils.toCNF(doubleNegative);
        } else if (original instanceof LiteralSentence) {
            return new ConjunctSentence(Collections.singletonList(sentence));
        } else if (original instanceof DisjunctSentence) {
            DeMorganDisjunctRule deMorganDisjunctRule = new DeMorganDisjunctRule(sentence);
            return CNFUtils.toCNF(deMorganDisjunctRule.apply());
        } else if (original instanceof ConjunctSentence) {
            DeMorganConjunctRule deMorganConjunctRule = new DeMorganConjunctRule(sentence);
            return CNFUtils.toCNF(deMorganConjunctRule.apply());
        }
        throw new IllegalArgumentException("Negate Sentence contains unidentified sentence type: " + original);
    }

    public static ConjunctSentence implicationToCNF(ImplicationSentence sentence) {
        ImplicationElimationRule implicationElimationRule = new ImplicationElimationRule(sentence);
        return CNFUtils.toCNF(implicationElimationRule.apply());
    }

    public static ConjunctSentence equivalenceToCNF(EquivalenceSentence sentence) {
        BiconditionalEliminationRule biconditionalEliminationRule = new BiconditionalEliminationRule(sentence);
        return CNFUtils.toCNF(biconditionalEliminationRule.apply());
    }

}
