package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class TermGroup {

    // With change on fields review setter in WeightedTermGroup
    private Long groupId;
    private TermScheme termScheme;
    private List<Term> terms;
    private Integer databaseIncidenceCount;
    private TermComparisonAlgorithm termComparisonAlgorithm;
    private Double tolerance;

    public TermGroup(Long groupId, TermScheme termScheme, List<Term> terms, Integer databaseIncidenceCount,
                     TermComparisonAlgorithm termComparisonAlgorithm, Double tolerance) {
        this.groupId = groupId;
        this.termScheme = termScheme;
        this.terms = terms;
        this.databaseIncidenceCount = databaseIncidenceCount;
        this.termComparisonAlgorithm = termComparisonAlgorithm;
        this.tolerance = tolerance;
    }

    //<editor-fold desc="Getters and setters">
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public Integer getDatabaseIncidenceCount() {
        return databaseIncidenceCount;
    }

    public void setDatabaseIncidenceCount(Integer databaseIncidenceCount) {
        this.databaseIncidenceCount = databaseIncidenceCount;
    }

    public TermComparisonAlgorithm getTermComparisonAlgorithm() {
        return termComparisonAlgorithm;
    }

    public void setTermComparisonAlgorithm(TermComparisonAlgorithm termComparisonAlgorithm) {
        this.termComparisonAlgorithm = termComparisonAlgorithm;
    }

    public Double getTolerance() {
        return tolerance;
    }

    public void setTolerance(Double tolerance) {
        this.tolerance = tolerance;
    }
    //</editor-fold>


    @Override
    public String toString() {
        return "TermGroup{" +
                "groupId=" + groupId +
                ", terms=" + terms +
                ", databaseIncidenceCount=" + databaseIncidenceCount +
                ", termComparisonAlgorithm=" + termComparisonAlgorithm +
                ", tolerance=" + tolerance +
                '}';
    }

    public TermScheme getTermScheme() {
        return termScheme;
    }

    public void setTermScheme(TermScheme termScheme) {
        this.termScheme = termScheme;
    }
}
