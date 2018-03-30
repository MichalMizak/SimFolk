package sk.upjs.ics.mmizak.simfolk.core.vector.space;


public abstract class AlgorithmConfiguration {

    public enum AlgorithmType {
        VECTOR
    }

    public enum TermWeightType {
        TF_NAIVE, TF, IDF, TFIDF
    }

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
        NAIVE
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
        A, B, AB, ALL
    }

    public enum VectorComparisonAlgorithm {
        COS
    }

    public enum Tolerance {
        NONE, LOW, MEDIUM, HIGH
    }
}