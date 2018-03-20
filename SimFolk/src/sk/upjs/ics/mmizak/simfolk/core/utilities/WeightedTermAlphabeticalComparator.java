package sk.upjs.ics.mmizak.simfolk.core.utilities;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;

public class WeightedTermAlphabeticalComparator implements java.util.Comparator<WeightedTermGroup> {

    @Override
    public int compare(WeightedTermGroup o1, WeightedTermGroup o2) {
        return o1.getTerms().getLyricsFragment().compareTo(o2.getTerms().getLyricsFragment());
    }
}
