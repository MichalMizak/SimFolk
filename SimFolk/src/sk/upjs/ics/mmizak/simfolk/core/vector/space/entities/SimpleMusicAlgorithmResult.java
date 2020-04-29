package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.util.Map;

public class SimpleMusicAlgorithmResult {
    private VectorAlgorithmConfiguration vectorAlgorithmConfiguration;

    private VectorSong vectorSong;

    private Map<MelodySong, Double> songToSimilarityPercentage;

    private MelodySong melodySong;

    public SimpleMusicAlgorithmResult(MusicAlgorithmResult inclusionResult, Map<MelodySong, Double> melodySongDoubleMap) {
        this.vectorAlgorithmConfiguration = inclusionResult.getVectorAlgorithmConfiguration();
        this.vectorSong = inclusionResult.getVectorSong();
        this.songToSimilarityPercentage = melodySongDoubleMap;
        this.melodySong = inclusionResult.getMelodySong();
    }

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

    public Map<MelodySong, Double> getSongToSimilarityPercentage() {
        return songToSimilarityPercentage;
    }

    public void setSongToSimilarityPercentage(Map<MelodySong, Double> songToSimilarityPercentage) {
        this.songToSimilarityPercentage = songToSimilarityPercentage;
    }

    //</editor-fold>

    @Override
    public String toString() {
        return "LyricAlgorithmResult{" +
                "melodySong" + melodySong.getMusicXML().toString() +
                ", songToSimilarityPercentage=" + songToSimilarityPercentage +
                ", vectorAlgorithmConfiguration=" + vectorAlgorithmConfiguration +
                '}';
    }
}
