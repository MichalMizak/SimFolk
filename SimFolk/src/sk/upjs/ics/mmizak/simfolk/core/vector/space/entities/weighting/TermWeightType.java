package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TF;

import java.util.NoSuchElementException;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.IDF;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.NonTFIDFTermWeightType;

public class TermWeightType {

    private boolean isTFIDF;

    private TF tf;
    private IDF idf;

    private NonTFIDFTermWeightType nonTFIDFTermWeightType;
    private Integer id;

    public TermWeightType(Integer id, TF tf, IDF idf) {
        this.id = id;
        this.tf = tf;
        this.idf = idf;
        isTFIDF = true;

        //default values
        this.nonTFIDFTermWeightType = NonTFIDFTermWeightType.NONE;
    }

    public TermWeightType(Integer id, NonTFIDFTermWeightType nonTFIDFTermWeightType) {
        this.id = id;
        this.nonTFIDFTermWeightType = nonTFIDFTermWeightType;
        isTFIDF = false;

        // default values
        this.tf = TF.TF_NAIVE;
        this.idf = IDF.NONE;
    }

    //<editor-fold desc="Getters">
    public boolean isTFIDF() {
        return isTFIDF;
    }

    public TF getTf() {
        if (isTFIDF) {
            return tf;
        }
        throw new NoSuchElementException();
    }

    public IDF getIdf() {
        if (isTFIDF) {
            return idf;
        }
        throw new NoSuchElementException();
    }

    public NonTFIDFTermWeightType getNonTFIDFTermWeight() {
        return nonTFIDFTermWeightType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static TermWeightType getFrequencyWeight() {
        return new TermWeightType(1, TF.TF_NAIVE, IDF.NONE);
    }

    @Override
    public String toString() {
        return "TermWeightType{" +
                "isTFIDF=" + isTFIDF +
                ", tf=" + tf +
                ", idf=" + idf +
                ", nonTFIDFTermWeightType=" + nonTFIDFTermWeightType +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TermWeightType that = (TermWeightType) o;

        if (isTFIDF() != that.isTFIDF()) return false;
        if (getTf() != that.getTf()) return false;
        if (getIdf() != that.getIdf()) return false;
        if (nonTFIDFTermWeightType != that.nonTFIDFTermWeightType) return false;
        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        int result = (isTFIDF() ? 1 : 0);
        result = 31 * result + (getTf() != null ? getTf().hashCode() : 0);
        result = 31 * result + (getIdf() != null ? getIdf().hashCode() : 0);
        result = 31 * result + (nonTFIDFTermWeightType != null ? nonTFIDFTermWeightType.hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }

    //</editor-fold>
}