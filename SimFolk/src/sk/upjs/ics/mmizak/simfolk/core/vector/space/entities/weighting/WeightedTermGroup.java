package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;


/**
 * Class represents a group of terms which are similar to each other
 * within a given tolerance
 */
public class WeightedTermGroup extends TermGroup {

    // song-specific fields
    private Integer songId;
    private Double termWeight;
    private TermWeightType termWeightType;

    /**
     * WeightedTermGroup with uninitialized fields of TermGroup
     * @param songId
     * @param termWeightType
     * @param weight
     */
    public WeightedTermGroup(Integer songId, TermWeightType termWeightType, Double weight) {
        super(null, null, null, null, null);

        this.songId = songId;
        this.termWeightType = termWeightType;
        this.termWeight = weight;
    }

    public WeightedTermGroup(TermGroup termGroup, Integer songId, TermWeightType termWeightType, Double weight) {
        this(songId, termGroup.getGroupId(), termGroup.getTerms(), termGroup.getDatabaseIncidenceCount(),
                weight, termWeightType, termGroup.getTermComparisonAlgorithm(), termGroup.getTolerance());
        this.songId = songId;
        this.termWeight = weight;
        this.termWeightType = termWeightType;
    }


    public WeightedTermGroup(Integer songId, Integer groupId, List<Term> terms, Integer databaseIncidenceCount, Double weight,
                             TermWeightType termWeightType, TermComparisonAlgorithm termComparisonAlgorithm, Double tolerance) {
        super(groupId, terms, databaseIncidenceCount, termComparisonAlgorithm, tolerance);

        this.songId = songId;
        this.termWeight = weight;
        this.termWeightType = termWeightType;
    }

    //<editor-fold desc="Getters and setters">
    public void setTermGroup(TermGroup termGroup) {
        setGroupId(termGroup.getGroupId());
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

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
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
