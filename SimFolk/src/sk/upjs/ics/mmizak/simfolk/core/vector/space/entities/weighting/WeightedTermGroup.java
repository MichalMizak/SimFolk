package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;


/**
 * Class represents a group of terms which are similar to each other
 * within a given tolerance
 */
public class WeightedTermGroup {

    // group-specific fields
    private Long groupId;
    private List<Term> terms;
    private Long databaseIncidenceCount;

    // song-specific fields
    private Long songId;
    private Double termWeight;
    private TermWeightType termWeightType;

    public WeightedTermGroup(Long songId, Long groupId, List<Term> terms, Long databaseIncidenceCount, double weight, TermWeightType termWeightType) {
        this.songId = songId;
        this.groupId = groupId;
        this.terms = terms;
        this.databaseIncidenceCount = databaseIncidenceCount;

        this.termWeight = weight;
        this.termWeightType = termWeightType;
    }

    //<editor-fold desc="Getters and setters">


    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public void setTermWeight(Double termWeight) {
        this.termWeight = termWeight;
    }

    public void setTermWeightType(TermWeightType termWeightType) {
        this.termWeightType = termWeightType;
    }

    public List<Term> getTerms() {
        return terms;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getDatabaseIncidenceCount() {
        return databaseIncidenceCount;
    }

    public void setDatabaseIncidenceCount(Long databaseIncidenceCount) {
        this.databaseIncidenceCount = databaseIncidenceCount;
    }
    //</editor-fold>

}
