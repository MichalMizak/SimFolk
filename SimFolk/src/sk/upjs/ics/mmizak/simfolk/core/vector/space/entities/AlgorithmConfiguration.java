package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

public class AlgorithmConfiguration {

    public enum AlgorithmType {
        VECTOR
    }

    //<editor-fold desc="Term weight type">
    public enum NonTFIDFTermWeightType {
        NONE

    }

    public enum TF {
        TF_NAIVE, LOG_TF, IDF, NONE, AUGMENTED_TF
    }

    public enum IDF {
        NO, IDF, NONE, PROB_IDF
    }
    //</editor-fold>

    public enum TermScheme {
        UNGRAM, BIGRAM, TRIGRAM, NGRAM;

        public boolean isDependantOnN() {
            switch (this) {
                case NGRAM:
                    return true;
                default:
                    return false;
            }
        }
    }

    public enum TermComparisonAlgorithm {
        LEVENSHTEIN_DISTANCE, NAIVE
    }

    public enum TermGroupMatchingStrategy {
        MATCH_ALL, MATCH_ONE
    }

    public enum TermGroupMergingStrategy {
        MERGE_ANY, MERGE_ALL
    }

    /**
     * Only vector algorithm enums
     */
    public enum VectorInclusion {
        A, B, INTERSECTION, UNIFICATION, ALL
    }

    public enum VectorComparisonAlgorithm {
        COS
    }

    public enum Tolerance {
        NONE, LOW, MEDIUM, HIGH
    }
}
