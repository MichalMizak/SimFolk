package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.Collections;
import java.util.Objects;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;


/**
 * Takes two terms, takes the lyrics fragment string and determines whether they are equal or not
 * One for equal, 0 for unequal.
 */
public class TermComparator implements ITermComparator {

    /**
     * Compares the ids of the terms, otherwise computes whether the terms are similar
     * enough to respect the given tolerance in the given termComparisonAlgorithm
     *
     * @param t1
     * @param t2
     * @param tolerance
     * @param vectorAlgorithmConfiguration
     * @return
     */
    @Override
    public boolean compare(Term t1, Term t2, double tolerance, VectorAlgorithmConfiguration vectorAlgorithmConfiguration) {

        TermComparisonAlgorithm termComparisonAlgorithm = vectorAlgorithmConfiguration.getTermComparisonAlgorithm();

        if (t1.getId() != null && t1.getId().equals(t2.getId())) {
            return true;
        }

        double similarity = 0;

        switch (termComparisonAlgorithm) {
            case LEVENSHTEIN_DISTANCE:
                if (vectorAlgorithmConfiguration.getMusicStringFormat() == MusicStringFormat.RHYTHM)
                    similarity = normalizedRhythmLevenshteinDistance(t1.getLyricsFragment(), t2.getLyricsFragment());
                else
                    similarity = normalizedLevenshteinDistance(t1.getLyricsFragment(), t2.getLyricsFragment());
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return similarity >= tolerance;
    }

    private double normalizedRhythmLevenshteinDistance(String leftString, String rightString) {

        Object[] leftIntArray = parseNumericString(leftString);
        Object[] rightIntArray = parseNumericString(rightString);

        double maxLength = Math.max(leftIntArray.length, rightIntArray.length);

        return 1 - objectLevenshteinDistance(leftIntArray, rightIntArray) / maxLength;

    }

    private double objectLevenshteinDistance(Object[] leftIntArray, Object[] rightIntArray) {
        int leftLength = leftIntArray.length + 1;
        int rightLength = rightIntArray.length + 1;

        // the array of distances
        int[] cost = new int[leftLength];
        int[] newCost = new int[leftLength];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < leftLength; i++)
            cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < rightLength; j++) {
            // initial cost of skipping prefix in String s1
            newCost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < leftLength; i++) {
                // matching current letters in both strings
                int match = Objects.equals(leftIntArray[i - 1], rightIntArray[j - 1]) ? 0 : 1;

                // computing cost for each transformation
                int costReplace = cost[i - 1] + match;
                int costInsert = cost[i] + 1;
                int costDelete = newCost[i - 1] + 1;

                // keep minimum cost
                newCost[i] = Math.min(Math.min(costInsert, costDelete), costReplace);
            }

            // swap cost/newCost arrays
            int[] swap = cost;
            cost = newCost;
            newCost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[leftLength - 1];
    }

    private Integer[] parseNumericString(String leftString) {
        String[] leftStringArray = leftString.split(" ");
        Integer[] leftIntArray = new Integer[leftStringArray.length];
        for (int i = 0; i < leftStringArray.length; i++) {
            leftIntArray[i] = Integer.parseInt(leftStringArray[i]);
        }
        return leftIntArray;
    }

    /**
     * @param leftString
     * @param rightString
     * @return Normalized levenshteinDistance  so that for the same strings the similarity
     * is 1 and the less similar two strings are the lower the value is
     */
    public double normalizedLevenshteinDistance(String leftString, String rightString) {

        double maxLength = Math.max(leftString.length(), rightString.length());
        return 1 - levenshteinDistance(leftString, rightString) / maxLength;
    }

    /**
     * Calculates the Levenshtein distance between two given strings. The code is taken from:
     * https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
     *
     * @param leftString  The left string.
     * @param rightString The right string.
     * @return The Levenshtein distance between given strings.
     */
    public int levenshteinDistance(String leftString, String rightString) {

        int leftLength = leftString.length() + 1;
        int rightLength = rightString.length() + 1;

        // the array of distances
        int[] cost = new int[leftLength];
        int[] newCost = new int[leftLength];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < leftLength; i++)
            cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < rightLength; j++) {
            // initial cost of skipping prefix in String s1
            newCost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < leftLength; i++) {
                // matching current letters in both strings
                int match = Objects.equals(leftString.charAt(i - 1), rightString.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int costReplace = cost[i - 1] + match;
                int costInsert = cost[i] + 1;
                int costDelete = newCost[i - 1] + 1;

                // keep minimum cost
                newCost[i] = Math.min(Math.min(costInsert, costDelete), costReplace);
            }

            // swap cost/newCost arrays
            int[] swap = cost;
            cost = newCost;
            newCost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[leftLength - 1];
    }

    //<editor-fold desc="Group comparison">
    @Override
    public boolean compareGroups(TermGroup aTermGroup, TermGroup bTermGroup,
                                 VectorAlgorithmConfiguration vectorConfiguration,
                                 double tolerance) {

        switch (vectorConfiguration.getTermGroupMatchingStrategy()) {
            case MATCH_ONE:
                return matchOne(aTermGroup, bTermGroup, vectorConfiguration, tolerance);
            case MATCH_ALL:
                return matchAll(aTermGroup, bTermGroup, vectorConfiguration, tolerance);
            default:
                return false;
        }
    }

    private boolean matchAll(TermGroup aTermGroup, TermGroup bTermGroup,
                             VectorAlgorithmConfiguration vectorConfiguration, double tolerance) {
        for (Term aTerm : aTermGroup.getTerms()) {
            for (Term bTerm : bTermGroup.getTerms()) {
                if (!compare(aTerm, bTerm, tolerance, vectorConfiguration)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean matchOne(TermGroup aTermGroup, TermGroup bTermGroup,
                             VectorAlgorithmConfiguration vectorConfiguration, double tolerance) {
        for (Term aTerm : aTermGroup.getTerms()) {
            for (Term bTerm : bTermGroup.getTerms()) {
                if (compare(aTerm, bTerm, tolerance, vectorConfiguration)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean naiveTermComparison(Term t1, Term t2) {
        return t1.getLyricsFragment().equals(t2.getLyricsFragment());
    }
    //</editor-fold>

}
