package sk.upjs.ics.mmizak.simfolk.core.utilities;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;

import java.util.Comparator;

/**
 * Sorts the term groups ASCENDING, where the low group ids are first
 * and the null ids are last
 * Let's have A = [3, null, 4, null, 1]
 * then A = [1, 3, 4, null, null]
 * is the result of using Collections.sort(TermGroupIdComparator)
 */
public class TermGroupIdComparator implements Comparator<TermGroup> {

    @Override
    public int compare(TermGroup o1, TermGroup o2) {

        Comparator<TermGroup> termGroupComparator
                = Comparator.comparingLong(TermGroup::getGroupId);

        Comparator<TermGroup> termGroupComparatorNullsLast
                = Comparator.nullsLast(termGroupComparator);

        return termGroupComparatorNullsLast.compare(o1, o2);
    }
}
