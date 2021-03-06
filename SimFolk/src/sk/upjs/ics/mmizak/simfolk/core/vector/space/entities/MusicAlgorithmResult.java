package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.util.Map;

/**
 * One result for all vector inclusions of one song songs
 */
public class MusicAlgorithmResult {

    private VectorAlgorithmConfiguration vectorAlgorithmConfiguration;

    private VectorSong vectorSong;

    private Map<AlgorithmConfiguration.VectorInclusion, Map<MelodySong, Double>> inclusionToSongToSimilarityPercentage;

    // private Map<MelodySong, Double> songToSimilarityPercentage;

    private MelodySong melodySong;

    //<editor-fold desc="Getters and setters">
    public MelodySong getMelodySong() {
        return melodySong;
    }

    public void setMelodySong(MelodySong melodySong) {
        this.melodySong = melodySong;
    }

    public VectorSong getVectorSong() {
        return vectorSong;
    }

    public void setVectorSong(VectorSong vectorSong) {
        this.vectorSong = vectorSong;
    }


    public VectorAlgorithmConfiguration getVectorAlgorithmConfiguration() {
        return vectorAlgorithmConfiguration;
    }

    public void setVectorAlgorithmConfiguration(VectorAlgorithmConfiguration vectorAlgorithmConfiguration) {
        this.vectorAlgorithmConfiguration = vectorAlgorithmConfiguration;
    }

    /*public Map<MelodySong, Double> getSongToSimilarityPercentage() {
        return songToSimilarityPercentage;
    }

    public void setSongToSimilarityPercentage(Map<MelodySong, Double> songToSimilarityPercentage) {
        this.songToSimilarityPercentage = songToSimilarityPercentage;
    }*/

    public Map<AlgorithmConfiguration.VectorInclusion, Map<MelodySong, Double>> getInclusionToSongToSimilarityPercentage() {
        return inclusionToSongToSimilarityPercentage;
    }

    public void setInclusionToSongToSimilarityPercentage(Map<AlgorithmConfiguration.VectorInclusion, Map<MelodySong, Double>> inclusionToSongToSimilarityPercentage) {
        this.inclusionToSongToSimilarityPercentage = inclusionToSongToSimilarityPercentage;
    }

    //</editor-fold>

    @Override
    public String toString() {
        return "LyricAlgorithmResult{" +
                "melodySong" + melodySong.getMusicXML().toString() +
                ", songToSimilarityPercentage=" + inclusionToSongToSimilarityPercentage +
                ", vectorAlgorithmConfiguration=" + vectorAlgorithmConfiguration +
                '}';
    }

}
