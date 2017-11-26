package sk.upjs.ics.mmizak.simfolk.core.utilities;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTerm;

public class WeightedTermAlphabeticalComparator implements java.util.Comparator<WeightedTerm> {

    @Override
    public int compare(WeightedTerm o1, WeightedTerm o2) {
        return o1.getTerm().getLyricsFragment().compareTo(o2.getTerm().getLyricsFragment());
    }
}
