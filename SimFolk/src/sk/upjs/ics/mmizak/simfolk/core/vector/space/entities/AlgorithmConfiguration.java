package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

public interface AlgorithmConfiguration {


    enum AlgorithmType {
        STRING, MUSIC
    }

    //<editor-fold desc="Term weight type">
    enum NonTFIDFTermWeightType {
        NONE
    }

    enum TF {
        TF_NAIVE, LOG_TF, IDF, NONE, AUGMENTED_TF
    }

    enum IDF {
        NO, IDF, NONE, PROB_IDF
    }
    //</editor-fold>

    enum TermScheme {
        UNGRAM, BIGRAM, TRIGRAM, NGRAM,
        // music - specifics
        // MEASURE_NGRAM (is tokenized by measures), NOTE_NGRAM and MEASURE is tokenized by notes
        WHOLE_SONG, MEASURE_NGRAM, NOTE_NGRAM, MEASURE;

        public boolean isDependantOnN() {
            return this != WHOLE_SONG && this != MEASURE;
        }
    }

    enum TermComparisonAlgorithm {
        LEVENSHTEIN_DISTANCE, NAIVE
    }

    enum TermGroupMatchingStrategy {
        MATCH_ALL, MATCH_ONE
    }

    enum TermGroupMergingStrategy {
        MERGE_ANY, MERGE_ALL
    }

    /**
     * Only vector algorithm enums
     */
    enum VectorInclusion {
        A, B, INTERSECTION, UNIFICATION, ALL
    }

    enum VectorComparisonAlgorithm {
        COS
    }

    enum Tolerance {
        NONE, LOW, MEDIUM, HIGH
    }

    // music specifics
    enum MusicStringFormat {
        ABSOLUTE, CONTOUR, RHYTHM, RELATIVE
    }
}
