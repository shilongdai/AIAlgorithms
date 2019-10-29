package net.viperfish.ai.propLogic;

import java.util.*;

public final class CNFUtils {

    private CNFUtils() {
    }

    public static Set<Map<String, CNFSymbol>> asCNFSymbols(ConjunctSentence cnf) {
        Set<Map<String, CNFSymbol>> result = new HashSet<>();

        for (Sentence s : cnf.children()) {
            Map<String, CNFSymbol> clauseSet = new HashMap<>();
            if (s instanceof DisjunctSentence) {
                DisjunctSentence clauseSentence = (DisjunctSentence) s;
                boolean contradiction = false;
                for (Sentence c : clauseSentence.children()) {
                    if (c instanceof LiteralSentence) {
                        LiteralSentence literal = (LiteralSentence) c;
                        if (!clauseSet.containsKey(literal.getLiteral())) {
                            clauseSet.put(literal.getLiteral(), new CNFSymbol(literal.getLiteral(), false));
                        } else {
                            CNFSymbol prev = clauseSet.get(literal.getLiteral());
                            if (prev.isNegate()) {
                                contradiction = true;
                                break;
                            }
                        }
                    } else if (c instanceof NegateSentence) {
                        NegateSentence negateSentence = (NegateSentence) c;
                        if (negateSentence.original() instanceof LiteralSentence) {
                            LiteralSentence literal = (LiteralSentence) negateSentence.original();
                            if (!clauseSet.containsKey(literal.getLiteral())) {
                                clauseSet.put(literal.getLiteral(), new CNFSymbol(literal.getLiteral(), true));
                            } else {
                                CNFSymbol prev = clauseSet.get(literal.getLiteral());
                                if (!prev.isNegate()) {
                                    contradiction = true;
                                    break;
                                }
                            }

                        } else {
                            throw new IllegalArgumentException("Expected CNF Sentence");
                        }
                    } else {
                        throw new IllegalArgumentException("Expected CNF Sentence");
                    }
                }
                if (!contradiction) {
                    result.add(clauseSet);
                }
            } else if (s instanceof LiteralSentence) {
                Map<String, CNFSymbol> singleMap = new HashMap<>();
                LiteralSentence literalCNF = (LiteralSentence) s;
                singleMap.put(literalCNF.getLiteral(), new CNFSymbol(literalCNF.getLiteral(), false));
                result.add(singleMap);
            } else if (s instanceof NegateSentence) {
                Map<String, CNFSymbol> singleNegate = new HashMap<>();
                NegateSentence literalCNF = (NegateSentence) s;
                LiteralSentence orig = (LiteralSentence) literalCNF.original();
                singleNegate.put(orig.getLiteral(), new CNFSymbol(orig.getLiteral(), true));
                result.add(singleNegate);
            } else {
                throw new IllegalArgumentException("Expected CNF Sentence");
            }
        }
        return result;
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

        while (true) {
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

                if (disjunctSet.size() != 1) {
                    conjunctSet.add(new DisjunctSentence(disjunctSet));
                } else {
                    conjunctSet.add(disjunctSet.iterator().next());
                }
                progressTracker.put(next, progressTracker.get(next) + 1);
            }

            if (progressTracker.get(next) != tracker.get(next).size()) {
                if (next != depth - 1) {
                    processingStack.push(next);
                    processingStack.push(next + 1);
                } else {
                    processingStack.push(next);
                }
            } else {
                if (processingStack.empty()) {
                    break;
                }
                progressTracker.put(next, 0);
                int recurseTarget = processingStack.peek();
                progressTracker.put(recurseTarget, progressTracker.get(recurseTarget) + 1);
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
