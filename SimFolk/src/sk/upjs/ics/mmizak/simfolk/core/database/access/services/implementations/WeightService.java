package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.IWeightedVectorDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.IWeightService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;

// TODO: implement reset weights and weight calculations
// TODO: TEST
public class WeightService implements IWeightService {

    private IWeightedVectorDao weightedVectorDao;
    //  private IWeightedTermGroupDao weightedTermGroupDao;

    public WeightService(IWeightedVectorDao weightedVectorDao) {
        this.weightedVectorDao = weightedVectorDao;
    }

    /**
     * Resets weights for all termWeightTypes
     */
    @Override
    public void resetWeights() {
        /*List<AlgorithmConfiguration.TermWeightTypeDao> termWeightTypes = Arrays.asList(TermWeightTypeDao.values());

        resetWeights(termWeightTypes);*/
    }

    /**
     * Resets weights for chosen termWeightTypes
     *
     * @param termWeightTypes
     */
    @Override
    public void resetWeights(List<TermWeightType> termWeightTypes) {
        for (TermWeightType type : termWeightTypes) {
            resetWeights(type);
        }
    }

    /**
     * Resets weights for this one termWeightType
     *
     * @param termWeightType
     */
    @Override
    public void resetWeights(TermWeightType termWeightType) {
        // weightedTermGroupDao.deleteAll(termWeightType);

    }

    @Override
    public WeightedTermGroup getWeightedTermById(Term term, TermWeightType termWeightType, Integer songId) {
        return null;
    }

    @Override
    public WeightedTermGroup resetAndCalculateWeight(Term term, TermWeightType termWeightType, Integer songId) {
        return null;
    }

    @Override
    public WeightedVector resetAndCalculateWeight(List<Term> terms, TermWeightType termWeightType, Integer songId) {
        return null;
    }

    /**
     * TF_NAIVE = term frequency in a document
     * LOG_TF =
     * IDF = inverse document frequency,
     * <p>
     * <p>
     * <p>
     * sources: An Introduction to Information Retrieval,
     * Christopher D. Manning,
     * Prabhakar Raghavan,
     * Hinrich Schütze,
     * Cambridge University Press,
     * Cambridge
     *
     * @param songId
     * @param frequencyWeightedGroups
     * @param vectorConfig
     * @return
     */
    @Override
    public WeightedVector calculateNewWeightedVector(Long songId, List<WeightedTermGroup> frequencyWeightedGroups, VectorAlgorithmConfiguration vectorConfig) {

        // init termWeightType and songId
        TermWeightType termWeightType = vectorConfig.getTermWeightType();

        frequencyWeightedGroups.forEach(w -> {
            w.setTermWeightType(termWeightType);
            w.setSongId(songId);
        });

        if (termWeightType.isTFIDF()) {
            return calculateTFIDF(songId, frequencyWeightedGroups, vectorConfig);
        }


        switch (termWeightType.getNonTFIDFTermWeight()) {
            case NONE:
                break;
            default:
                throw new RuntimeException("Unknown termWeightType");
        }
        return new WeightedVector(songId, frequencyWeightedGroups);
    }

    private WeightedVector calculateTFIDF(Long songId, List<WeightedTermGroup> frequencyWeightedGroups, VectorAlgorithmConfiguration vectorConfig) {
        TermWeightType termWeightType = vectorConfig.getTermWeightType();

        List<Double> termFrequencies = frequencyWeightedGroups.parallelStream().map(WeightedTermGroup::getTermWeight)
                .collect(Collectors.toList());

        switch (termWeightType.getTf()) {
            case TF_NAIVE:
                // DO NOTHING
                break;
            case LOG_TF: {
                frequencyWeightedGroups = logTF(frequencyWeightedGroups);
                break;
            }
            case AUGMENTED_TF: {
                frequencyWeightedGroups = augmentedTF(frequencyWeightedGroups);
                break;
            }
            default:
                throw new RuntimeException("Unknown termWeightType");
        }

        switch (termWeightType.getIdf()) {
            case NONE: {
                break;
            }
            case IDF: {
                frequencyWeightedGroups = idf(frequencyWeightedGroups, termFrequencies);
                break;
            }
        }

        return new WeightedVector(songId, frequencyWeightedGroups);
    }

    private List<WeightedTermGroup> idf(List<WeightedTermGroup> frequencyWeightedGroups, List<Double> termFrequencies) {
        for (int i = 0; i < termFrequencies.size(); i++) {
            Double frequency = termFrequencies.get(i);
            WeightedTermGroup wtg = frequencyWeightedGroups.get(i);

            double idf = Math.log10(1.0 * wtg.getDatabaseIncidenceCount() / frequency);
            wtg.setTermWeight(idf * wtg.getTermWeight());
        }

        return frequencyWeightedGroups;
    }

    private List<WeightedTermGroup> logTF(List<WeightedTermGroup> frequencyWeightedGroups) {
        frequencyWeightedGroups.parallelStream().forEach(wtg -> {
            double newWeight = 1 + Math.log10(wtg.getTermWeight());
            wtg.setTermWeight(newWeight);
        });
        return frequencyWeightedGroups;
    }

    private List<WeightedTermGroup> augmentedTF(List<WeightedTermGroup> frequencyWeightedGroups) {
        WeightedTermGroup mostFrequentGroup = frequencyWeightedGroups.parallelStream()
                .max(Comparator.comparing(WeightedTermGroup::getTermWeight))
                .orElse(new WeightedTermGroup(null, TermWeightType.getDummy(), 0D));

        double maxFrequency = mostFrequentGroup.getTermWeight();

        frequencyWeightedGroups.parallelStream().forEach(wtg -> {
            final double a = 0.5;
            double newWeight = a + a * wtg.getTermWeight() / maxFrequency;
            wtg.setTermWeight(newWeight);
        });
        return frequencyWeightedGroups;
    }

    @Override
    public WeightedVector getFittingWeightedTermVectorBySongId(Long songId, VectorAlgorithmConfiguration vectorConfig, double tolerance) {
        return weightedVectorDao.getFittingWeightedVectorBySongId(songId, vectorConfig, tolerance);
    }

    @Override
    public List<WeightedVector> getAllFittingWeightedVectors(VectorAlgorithmConfiguration vectorConfig, double tolerance) {
        return weightedVectorDao.getAllFittingWeightedVectors(vectorConfig, tolerance);
    }

    //<editor-fold desc="Methods delegated to weightedVectorDao">

    @Override
    public List<WeightedVector> getAllWeightedTermVectors(TermWeightType termWeightType,
                                                          TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        return weightedVectorDao.getAllWeightedVectors(termWeightType, termComparisonAlgorithm, tolerance);
    }

    @Override
    public void saveOrEdit(WeightedVector weightedVector) {
        weightedVectorDao.saveOrEdit(weightedVector);
    }

    @Override
    public void delete(WeightedVector weightedVector) {
        weightedVectorDao.delete(weightedVector);
    }

    //</editor-fold>
}
