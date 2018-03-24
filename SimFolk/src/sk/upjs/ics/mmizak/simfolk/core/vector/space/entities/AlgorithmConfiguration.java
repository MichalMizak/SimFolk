package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;


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
