package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.IVectorAlgorithmConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.builders.VectorAlgorithmConfigurationBuilder;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

import java.util.*;
import java.util.stream.Collectors;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class MusicVectorAlgorithmConfigurationService implements IVectorAlgorithmConfigurationService {

    @Override
    public VectorAlgorithmConfiguration generateRandomConfiguration() {
        return new VectorAlgorithmConfigurationBuilder()
                .setId(1L)
                .setTermScheme(TermScheme.MEASURE_NGRAM)
                .setTermDimension(4)
                .setTermWeightType(new TermWeightType(null, TF.AUGMENTED_TF, IDF.IDF))
                .setTermComparisonAlgorithm(TermComparisonAlgorithm.LEVENSHTEIN_DISTANCE)
                .setTermGroupMatchingStrategy(TermGroupMatchingStrategy.MATCH_ONE)
                .setTermGroupMergingStrategy(TermGroupMergingStrategy.MERGE_ANY)
                .setVectorInclusion(Arrays.asList(AlgorithmConfiguration.VectorInclusion.values()))
                .setVectorComparisonAlgorithm(VectorComparisonAlgorithm.COS)
                .setTolerance(Tolerance.LOW)
                .setMusicStringFormat(MusicStringFormat.RHYTHM)
                .createVectorAlgorithmConfiguration();
    }

    @Override
    public List<VectorAlgorithmConfiguration> loadAllConfigurations() {
        Set<VectorAlgorithmConfigurationBuilder> builders = new HashSet<>();

        builders = generateTermSchemeBuilders(builders);
        //builders = generateTermWeightBuilders(builders);
        builders.forEach(builder -> builder.setTermWeightType(new TermWeightType(null, TF.TF_NAIVE, IDF.IDF)));

        builders = generateTermComparisonAlgorithmBuilders(builders);

        builders.forEach(builder -> builder.setTermGroupMatchingStrategy(TermGroupMatchingStrategy.MATCH_ALL));
        // builders = generateTermGroupMatchingStrategy(builders);

        builders.forEach(builder -> builder.setTermGroupMergingStrategy(TermGroupMergingStrategy.MERGE_ANY));

        builders = generateVectorInclusionBuilders(builders);

        //builders = generateVectorComparisonAlgorithmBuilders(builders);
        builders.forEach(builder -> builder.setVectorComparisonAlgorithm(VectorComparisonAlgorithm.COS));

        builders = generateToleranceBuilders(builders);
        builders = generateMusicStringFormatBuilders(builders);


        List<VectorAlgorithmConfiguration> result = builders.stream().map(VectorAlgorithmConfigurationBuilder::createVectorAlgorithmConfiguration).collect(Collectors.toList());

        for (int i = 0; i < result.size(); i++) {
            result.get(i).setId((long) i);
        }

        return result;
    }

    private Set<VectorAlgorithmConfigurationBuilder> generateTermGroupMatchingStrategy(Set<VectorAlgorithmConfigurationBuilder> builders) {
        List<Set<VectorAlgorithmConfigurationBuilder>> copiedBuilders = new ArrayList<>();
        Set<VectorAlgorithmConfigurationBuilder> cachedBuilders = getCachedSet(builders);

        TermGroupMatchingStrategy[] musicStringFormats = TermGroupMatchingStrategy.values();

        for (TermGroupMatchingStrategy termGroupMatchingStrategy : musicStringFormats) {
            Set<VectorAlgorithmConfigurationBuilder> oneTermWeightSet = new HashSet<>();
            for (VectorAlgorithmConfigurationBuilder cachedBuilder : cachedBuilders) {
                oneTermWeightSet.add(cachedBuilder.getBuilderClone().setTermGroupMatchingStrategy(termGroupMatchingStrategy));
            }
            copiedBuilders.add(oneTermWeightSet);
        }

        return flatMapBuilders(builders, copiedBuilders);
    }

    private Set<VectorAlgorithmConfigurationBuilder> generateMusicStringFormatBuilders(Set<VectorAlgorithmConfigurationBuilder> builders) {
        List<Set<VectorAlgorithmConfigurationBuilder>> copiedBuilders = new ArrayList<>();
        Set<VectorAlgorithmConfigurationBuilder> cachedBuilders = getCachedSet(builders);

        MusicStringFormat[] musicStringFormats = MusicStringFormat.values();

        for (MusicStringFormat musicStringFormat : musicStringFormats) {
            Set<VectorAlgorithmConfigurationBuilder> oneTermWeightSet = new HashSet<>();
            for (VectorAlgorithmConfigurationBuilder cachedBuilder : cachedBuilders) {
                oneTermWeightSet.add(cachedBuilder.getBuilderClone().setMusicStringFormat(musicStringFormat));
            }
            copiedBuilders.add(oneTermWeightSet);
        }

        return flatMapBuilders(builders, copiedBuilders);
    }

    private Set<VectorAlgorithmConfigurationBuilder> generateToleranceBuilders(Set<VectorAlgorithmConfigurationBuilder> builders) {
        List<Set<VectorAlgorithmConfigurationBuilder>> copiedBuilders = new ArrayList<>();
        Set<VectorAlgorithmConfigurationBuilder> cachedBuilders = getCachedSet(builders);

        Tolerance[] tolerances = Tolerance.values();

        for (Tolerance tolerance : tolerances) {
            Set<VectorAlgorithmConfigurationBuilder> oneTermWeightSet = new HashSet<>();
            for (VectorAlgorithmConfigurationBuilder cachedBuilder : cachedBuilders) {
                oneTermWeightSet.add(cachedBuilder.getBuilderClone().setTolerance(tolerance));
            }
            copiedBuilders.add(oneTermWeightSet);
        }

        return flatMapBuilders(builders, copiedBuilders);
    }

    private Set<VectorAlgorithmConfigurationBuilder> generateVectorComparisonAlgorithmBuilders(Set<VectorAlgorithmConfigurationBuilder> builders) {
        List<Set<VectorAlgorithmConfigurationBuilder>> copiedBuilders = new ArrayList<>();
        Set<VectorAlgorithmConfigurationBuilder> cachedBuilders = getCachedSet(builders);

        VectorComparisonAlgorithm[] vectorComparisonAlgorithms = VectorComparisonAlgorithm.values();

        for (VectorComparisonAlgorithm vectorComparisonAlgorithm : vectorComparisonAlgorithms) {
            Set<VectorAlgorithmConfigurationBuilder> oneTermWeightSet = new HashSet<>();
            for (VectorAlgorithmConfigurationBuilder cachedBuilder : cachedBuilders) {
                oneTermWeightSet.add(cachedBuilder.getBuilderClone().setVectorComparisonAlgorithm(vectorComparisonAlgorithm));
            }
            copiedBuilders.add(oneTermWeightSet);
        }

        return flatMapBuilders(builders, copiedBuilders);
    }

    private Set<VectorAlgorithmConfigurationBuilder> generateVectorInclusionBuilders(Set<VectorAlgorithmConfigurationBuilder> builders) {
        List<VectorInclusion> inclusions = Arrays.asList(AlgorithmConfiguration.VectorInclusion.values());

        builders.forEach(v -> v.setVectorInclusion(inclusions));
        return builders;
    }

    private Set<VectorAlgorithmConfigurationBuilder> generateTermComparisonAlgorithmBuilders(Set<VectorAlgorithmConfigurationBuilder> builders) {
        List<Set<VectorAlgorithmConfigurationBuilder>> copiedBuilders = new ArrayList<>();
        Set<VectorAlgorithmConfigurationBuilder> cachedBuilders = getCachedSet(builders);

        TermComparisonAlgorithm[] comparisonAlgorithms = TermComparisonAlgorithm.values();

        for (TermComparisonAlgorithm comparisonAlgorithm : comparisonAlgorithms) {
            Set<VectorAlgorithmConfigurationBuilder> oneTermWeightSet = new HashSet<>();
            for (VectorAlgorithmConfigurationBuilder cachedBuilder : cachedBuilders) {
                oneTermWeightSet.add(cachedBuilder.getBuilderClone().setTermComparisonAlgorithm(comparisonAlgorithm));
            }
            copiedBuilders.add(oneTermWeightSet);
        }

        return flatMapBuilders(builders, copiedBuilders);
    }

    private Set<VectorAlgorithmConfigurationBuilder> generateTermWeightBuilders(Set<VectorAlgorithmConfigurationBuilder> builders) {
        List<Set<VectorAlgorithmConfigurationBuilder>> copiedBuilders = new ArrayList<>();

        Set<VectorAlgorithmConfigurationBuilder> cachedBuilders = getCachedSet(builders);

        Set<TermWeightType> termWeightsTypes = generateTermWeights();

        for (TermWeightType termWeightType : termWeightsTypes) {
            Set<VectorAlgorithmConfigurationBuilder> oneTermWeightSet = new HashSet<>();
            for (VectorAlgorithmConfigurationBuilder cachedBuilder : cachedBuilders) {
                oneTermWeightSet.add(cachedBuilder.getBuilderClone().setTermWeightType(termWeightType));
            }
            copiedBuilders.add(oneTermWeightSet);
        }

        return flatMapBuilders(builders, copiedBuilders);
    }


    private Set<TermWeightType> generateTermWeights() {
        Set<TermWeightType> termWeightTypes = new HashSet<>();

        TF[] tfs = VectorAlgorithmConfiguration.TF.values();
        IDF[] idfs = VectorAlgorithmConfiguration.IDF.values();

        for (TF tf : tfs) {
            for (IDF idf : idfs) {
                termWeightTypes.add(new TermWeightType(null, tf, idf));
            }
        }
        return termWeightTypes;
    }


    /**
     * Expect empty builders, init all term schemes.
     *
     * @param builders
     * @return
     */
    private Set<VectorAlgorithmConfigurationBuilder> generateTermSchemeBuilders(Set<VectorAlgorithmConfigurationBuilder> builders) {

        for (TermScheme termScheme : TermScheme.getMusicTermSchemes()) {
            if (termScheme.isDependantOnN()) {
                builders.add(new VectorAlgorithmConfigurationBuilder().setTermScheme(termScheme).setTermDimension(3));
                builders.add(new VectorAlgorithmConfigurationBuilder().setTermScheme(termScheme).setTermDimension(4));
            } else {
                builders.add(new VectorAlgorithmConfigurationBuilder().setTermScheme(termScheme));
            }
            if (termScheme.equals(TermScheme.NOTE_NGRAM)) {
                builders.add(new VectorAlgorithmConfigurationBuilder().setTermScheme(termScheme).setTermDimension(1));
            }
        }
        return builders;
    }

    private Set<VectorAlgorithmConfigurationBuilder> flatMapBuilders(Set<VectorAlgorithmConfigurationBuilder> builders, List<Set<VectorAlgorithmConfigurationBuilder>> copiedBuilders) {
        builders.clear();

        copiedBuilders.forEach(builders::addAll);
        return builders;
    }

    private Set<VectorAlgorithmConfigurationBuilder> getCachedSet(Set<VectorAlgorithmConfigurationBuilder> builders) {
        Set<VectorAlgorithmConfigurationBuilder> cachedBuilders = new HashSet<>();
        builders.forEach(builder -> cachedBuilders.add(builder.getBuilderClone()));
        return cachedBuilders;
    }
}
