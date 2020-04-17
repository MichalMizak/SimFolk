package sk.upjs.ics.mmizak.simfolk.melody;

import org.audiveris.proxymusic.ScorePartwise;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermBuilder;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MusicTermBuilder implements ITermBuilder {


    private MelodyToStringConverter melodyToStringConverter = new MelodyToStringConverter();

    @Override
    public List<Term> buildTerms(VectorAlgorithmConfiguration vectorAlgorithmConfiguration, Object melodySongInput) {

        if (!(melodySongInput instanceof MelodySong)) {
            throw new RuntimeException("MusicTermBuilder cant process other object than MelodySong");
        }

        MelodySong melodySong = (MelodySong) melodySongInput;
        AlgorithmConfiguration.TermScheme termScheme = vectorAlgorithmConfiguration.getTermScheme();

        switch (termScheme) {
            case WHOLE_SONG:
                return buildWholeSongTerms(vectorAlgorithmConfiguration, melodySong);
            case MEASURE:
                return buildMeasureTerms(vectorAlgorithmConfiguration, melodySong);
           // case NOTE_NGRAM:
            //    return buildNoteNGrams(vectorAlgorithmConfiguration, melodySong);
            case MEASURE_NGRAM:
                return buildMeasureNGrams(vectorAlgorithmConfiguration, melodySong);
            default:
                throw new UnsupportedOperationException("Unsupported TermScheme @" + this.getClass());
        }
    }

    private List<Term> buildWholeSongTerms(VectorAlgorithmConfiguration vectorAlgorithmConfiguration, MelodySong melodySong) {
        List<ScorePartwise.Part.Measure> melodyInMeasures = melodySong.getMelodyInMeasures();

        List<String> measureStrings = melodyToStringConverter.getMelodyInMeasuresAsString(melodyInMeasures,
                vectorAlgorithmConfiguration.getMusicStringFormat());

        List<Term> result = new ArrayList<>();
        StringBuilder resultMelodyString = new StringBuilder();

        for (String measureString : measureStrings) {
            resultMelodyString.append(measureString);
        }

        result.add(new Term(null, resultMelodyString.toString().trim(), measureStrings,
                vectorAlgorithmConfiguration.getTermScheme()));

        return result;
    }

    private List<Term> buildMeasureNGrams(VectorAlgorithmConfiguration vectorAlgorithmConfiguration, MelodySong melodySong) {
        List<ScorePartwise.Part.Measure> melodyInMeasures = melodySong.getMelodyInMeasures();

        if (melodyInMeasures.size() <= vectorAlgorithmConfiguration.getTermDimension()) {
            return buildWholeSongTerms(vectorAlgorithmConfiguration, melodySong);
        }

        List<String> measureStrings = melodyToStringConverter.getMelodyInMeasuresAsString(melodyInMeasures,
                vectorAlgorithmConfiguration.getMusicStringFormat());

        List<Term> result = new ArrayList<>();
        //
        for (int i = 0; i < measureStrings.size() - vectorAlgorithmConfiguration.getTermDimension() + 1; i++) {
            List<String> tokenizedStringFragment = new ArrayList<>();
            StringBuilder concatenatedMeasures = new StringBuilder();

            for (int j = i; j < i + vectorAlgorithmConfiguration.getTermDimension(); j++) {
                String measureString = measureStrings.get(j);
                tokenizedStringFragment.add(measureString);
                // the measures come with a delimiter at the end
                concatenatedMeasures.append(measureString);
            }

            if (concatenatedMeasures.toString().isEmpty()) {
                continue;
            }

            result.add(new Term(null, concatenatedMeasures.toString().trim(), tokenizedStringFragment, vectorAlgorithmConfiguration.getTermScheme()));
        }

        return result;
    }


    private List<Term> buildNoteNGrams(VectorAlgorithmConfiguration vectorAlgorithmConfiguration, MelodySong melodySong) {
        List<ScorePartwise.Part.Measure> melodyInMeasures = melodySong.getMelodyInMeasures();

        melodyToStringConverter.getMelodyInMeasuresAsString(melodyInMeasures,
                vectorAlgorithmConfiguration.getMusicStringFormat());
        return null;
    }

    private List<Term> buildMeasureTerms(VectorAlgorithmConfiguration vectorAlgorithmConfiguration, MelodySong melodySong) {
        List<ScorePartwise.Part.Measure> melodyInMeasures = melodySong.getMelodyInMeasures();

        List<String> measureStrings = melodyToStringConverter.getMelodyInMeasuresAsString(melodyInMeasures,
                vectorAlgorithmConfiguration.getMusicStringFormat());

        List<Term> result = new ArrayList<>();

        for (String measureString : measureStrings) {
            List<String> tokenizedStringFragment = new ArrayList<>();
            tokenizedStringFragment.add(measureString);

            result.add(new Term(null, measureString.trim(), tokenizedStringFragment, vectorAlgorithmConfiguration.getTermScheme()));
        }

        return result;
    }
}
