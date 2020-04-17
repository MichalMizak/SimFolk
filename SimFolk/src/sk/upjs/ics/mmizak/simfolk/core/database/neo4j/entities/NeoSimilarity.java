package sk.upjs.ics.mmizak.simfolk.core.database.neo4j.entities;

import org.neo4j.ogm.annotation.*;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

@RelationshipEntity(type = "IS_SIMILAR")
public class NeoSimilarity {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private Double percentage;

    @StartNode
    private NeoSong startSong;

    @EndNode
    private NeoSong endSong;

    @Property
    private boolean aggregationResult;

    @Property
    private AlgorithmConfiguration.Tolerance tolerance;

    @Property
    private AlgorithmConfiguration.VectorInclusion vectorInclusion;

    @Property
    private AlgorithmConfiguration.TermScheme termScheme;

    @Property
    private Integer termDimension;

    @Property
    private String termWeightType;

    @Property
    private AlgorithmConfiguration.MusicStringFormat musicStringFormat;

    @Property
    private AlgorithmConfiguration.TermComparisonAlgorithm termComparisonAlgorithm;

    @Property
    private AlgorithmConfiguration.VectorComparisonAlgorithm vectorComparisonAlgorithm;
    // configuration properties


    public NeoSimilarity(Double percentage, VectorAlgorithmConfiguration vectorAlgorithmConfiguration, NeoSong startSong, NeoSong endSong) {
        this.percentage = percentage;
        this.startSong = startSong;
        this.endSong = endSong;

        aggregationResult = false;

        tolerance = vectorAlgorithmConfiguration.getTolerance();
        vectorInclusion = vectorAlgorithmConfiguration.getVectorInclusion();

        termScheme = vectorAlgorithmConfiguration.getTermScheme();
        termDimension = vectorAlgorithmConfiguration.getTermDimension();

        termWeightType = vectorAlgorithmConfiguration.getTermWeightType().toString();
        musicStringFormat = vectorAlgorithmConfiguration.getMusicStringFormat();

        termComparisonAlgorithm = vectorAlgorithmConfiguration.getTermComparisonAlgorithm();
        vectorComparisonAlgorithm = vectorAlgorithmConfiguration.getVectorComparisonAlgorithm();
    }

    /**
     * Constructor for algorithm aggregated result
     *
     * @param percentage
     * @param startSong
     * @param endSong
     */
    public NeoSimilarity(Double percentage, NeoSong startSong, NeoSong endSong) {
        this.percentage = percentage;
        this.startSong = startSong;
        this.endSong = endSong;
        aggregationResult = true;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public NeoSong getStartSong() {
        return startSong;
    }

    public void setStartSong(NeoSong startSong) {
        this.startSong = startSong;
    }

    public NeoSong getEndSong() {
        return endSong;
    }

    public void setEndSong(NeoSong endSong) {
        this.endSong = endSong;
    }

    @Override
    public String toString() {
        return "NeoSimilarity{" +
                "id=" + id +
                ", percentage=" + percentage +
                ", startSong=" + startSong +
                ", endSong=" + endSong +
                '}';
    }
}
