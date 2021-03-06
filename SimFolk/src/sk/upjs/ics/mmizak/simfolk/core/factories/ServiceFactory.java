package sk.upjs.ics.mmizak.simfolk.core.factories;

import sk.upjs.ics.mmizak.simfolk.core.services.implementations.*;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.*;
import sk.upjs.ics.mmizak.simfolk.core.services.implementations.ToleranceCalculator;
import sk.upjs.ics.mmizak.simfolk.melody.MusicTermBuilder;

public enum ServiceFactory {

    INSTANCE;

    // music - specific
    private ITermBuilder musicTermBuilder;




    private IVectorAlgorithmConfigurationService algorithmConfigurationService;

    private ILyricCleaner lyricCleaner;

    private ISongService songService;

    private ITermBuilder termBuilder;

    private ITermService termService;

    private ITermGroupService termGroupService;

    private IToleranceCalculator toleranceCalculator;

    private IWeightService weightCalculator;

    private ITermVectorFormatter termVectorFormatter;

    // comparators
    private ITermComparator termComparator;

    private IVectorComparator vectorComparator;


    public IVectorAlgorithmConfigurationService getAlgorithmConfigurationService() {
        if (algorithmConfigurationService == null) {
            algorithmConfigurationService = new DummyVectorAlgorithmConfigurationService();
        }
        return algorithmConfigurationService;
    }

    // TODO: Validate configuration
    public ITermBuilder getTermBuilder() {
        if (termBuilder == null) {
            termBuilder = new MusicTermBuilder();
        }
        return termBuilder;
    }

    public ITermVectorFormatter getTermVectorFormatter() {
        if (termVectorFormatter == null) {
            termVectorFormatter = new TermVectorFormatter();
        }
        return termVectorFormatter;
    }

    public ITermComparator getTermComparator() {
        if (termComparator == null) {
            termComparator = new TermComparator();
        }
        return termComparator;

    }

    public IVectorComparator getVectorComparator() {
        if (vectorComparator == null) {
            vectorComparator = new VectorComparator();
        }
        return vectorComparator;
    }

    public IWeightService getWeightCalculator() {
        if (weightCalculator == null) {
            weightCalculator = new WeightService(DaoFactory.INSTANCE.getWeightedVectorDao(),
                    DaoFactory.INSTANCE.getWeightedTermGroupDao(), DaoFactory.INSTANCE.getSongDao());

        }
        return weightCalculator;
    }

    public ITermService getTermService() {
        if (termService == null) {
            termService = new TermService(DaoFactory.INSTANCE.getTermDao(), INSTANCE.getTermBuilder());
        }
        return termService;
    }

    public IToleranceCalculator getToleranceCalculator() {
        if (toleranceCalculator == null) {
            toleranceCalculator = new ToleranceCalculator();
        }
        return toleranceCalculator;
    }

    public ILyricCleaner getLyricCleaner() {
        if (lyricCleaner == null) {
            lyricCleaner = new LyricCleaner();
        }
        return lyricCleaner;
    }

    public ISongService getSongService() {
        if (songService == null) {
            songService = new SongService(DaoFactory.INSTANCE.getSongDao(), INSTANCE.getLyricCleaner());
        }
        return songService;
    }

    public ITermGroupService getTermGroupService() {
        if (termGroupService == null) {
            termGroupService = new TermGroupService(DaoFactory.INSTANCE.getTermGroupDao(), INSTANCE.getTermComparator());
        }
        return termGroupService;
    }

    public ITermBuilder getMusicTermBuilder() {
        if (musicTermBuilder == null) {
            musicTermBuilder = new MusicTermBuilder();
        }
        return musicTermBuilder;
    }
}
