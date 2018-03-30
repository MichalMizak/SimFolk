package sk.upjs.ics.mmizak.simfolk.core.vector.space;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorSong;

import java.util.Map;

/**
 * A result of one successful algorithm execution for one song.
 */
public class VectorAlgorithmResult {

    private VectorSong vectorSong;

    // TODO: Proper format of result
    private Map<Long, Double> songToSimilarityPercentage;

    private VectorAlgorithmConfiguration vectorAlgorithmConfiguration;

    //<editor-fold desc="Getters and setters">
    public VectorSong getVectorSong() {
        return vectorSong;
    }

    public void setVectorSong(VectorSong vectorSong) {
        this.vectorSong = vectorSong;
    }

    public Map<Long, Double> getSongToSimilarityPercentage() {
        return songToSimilarityPercentage;
    }

    public void setSongToSimilarityPercentage(Map<Long, Double> songToSimilarityPercentage) {
        this.songToSimilarityPercentage = songToSimilarityPercentage;
    }

    public VectorAlgorithmConfiguration getVectorAlgorithmConfiguration() {
        return vectorAlgorithmConfiguration;
    }

    public void setVectorAlgorithmConfiguration(VectorAlgorithmConfiguration vectorAlgorithmConfiguration) {
        this.vectorAlgorithmConfiguration = vectorAlgorithmConfiguration;
    }
    //</editor-fold>


    @Override
    public String toString() {
        return "VectorAlgorithmResult{" +
                "vectorSong=" + vectorSong +
                ", songToSimilarityPercentage=" + songToSimilarityPercentage +
                ", vectorAlgorithmConfiguration=" + vectorAlgorithmConfiguration +
                '}';
    }
}
