package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.*;


/**
 * Class represents a group of terms which are similar to each other
 * within a given tolerance
 */
public class WeightedTermGroup extends TermGroup {

    // song-specific fields
    private Long songId;
    private Double termWeight;
    private TermWeightType termWeightType;

    /**
     * WeightedTermGroup with uninitialized fields of TermGroup
     * @param songId
     * @param termWeightType
     * @param weight
     */
    public WeightedTermGroup(Long songId, TermWeightType termWeightType, Double weight) {
        super(null, null, null, null, null, null);

        this.songId = songId;
        this.termWeightType = termWeightType;
        this.termWeight = weight;
    }

    /**
     * initializes term group fields and sets term weight to 1.0
     * @param termGroup
     */
    public WeightedTermGroup(TermGroup termGroup) {
        super(termGroup.getGroupId(), termGroup.getTermScheme(), termGroup.getTerms(), termGroup.getDatabaseIncidenceCount(),
                termGroup.getTermComparisonAlgorithm(), termGroup.getTolerance());
        setTermWeight(1.0);

    }

    public WeightedTermGroup(TermGroup termGroup, Long songId, TermWeightType termWeightType, Double weight) {
        this(songId, termWeightType, weight, termGroup.getGroupId(), termGroup.getTermScheme(), termGroup.getTerms(),
                termGroup.getTermComparisonAlgorithm(), termGroup.getTolerance(), termGroup.getDatabaseIncidenceCount()
        );
        this.songId = songId;
        this.termWeight = weight;
        this.termWeightType = termWeightType;
    }


    public WeightedTermGroup(Long songId, TermWeightType termWeightType, Double weight, Long groupId, TermScheme termScheme,
                             List<Term> terms, TermComparisonAlgorithm termComparisonAlgorithm, Double tolerance,
                             Integer databaseIncidenceCount) {
        super(groupId, termScheme, terms, databaseIncidenceCount, termComparisonAlgorithm, tolerance);

        this.songId = songId;
        this.termWeight = weight;
        this.termWeightType = termWeightType;
    }

    //<editor-fold desc="Getters and setters">
    public void setTermGroup(TermGroup termGroup) {
        setGroupId(termGroup.getGroupId());
        setTermScheme(termGroup.getTermScheme());
        setTerms(termGroup.getTerms());
        setDatabaseIncidenceCount(termGroup.getDatabaseIncidenceCount());
        setTermComparisonAlgorithm(termGroup.getTermComparisonAlgorithm());
        setTolerance(termGroup.getTolerance());
    }

    public void setTermWeight(Double termWeight) {
        this.termWeight = termWeight;
    }

    public void setTermWeightType(TermWeightType termWeightType) {
        this.termWeightType = termWeightType;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Double getTermWeight() {
        return termWeight;
    }

    public TermWeightType getTermWeightType() {
        return termWeightType;
    }

    //</editor-fold>


    @Override
    public String toString() {

        String termGroup = "\n{" +
                "groupId=" + getGroupId() +
                "termScheme" + getTermScheme() +
                ", terms=" + getTerms() +
                ", databaseIncidenceCount=" + getDatabaseIncidenceCount() +
                ", termComparisonAlgorithm=" + getTermComparisonAlgorithm() +
                ", tolerance=" + getTolerance() +
                '}';

        String weightedTermGroup = "WeightedTermGroup{" +
                "songId=" + songId +
                ", termWeight=" + termWeight +
                ", termWeightType=" + termWeightType +
                '}';

        return weightedTermGroup + termGroup;
    }
}
