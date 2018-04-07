package sk.upjs.ics.mmizak.simfolk.core.database.access;

import sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations.*;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.*;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations.ToleranceCalculator;

public enum ServiceFactory {

    INSTANCE;

    private IAlgorithmConfigurationService algorithmConfigurationService;

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


    public IAlgorithmConfigurationService getAlgorithmConfigurationService() {
        if (algorithmConfigurationService == null) {
            algorithmConfigurationService = new DummyVectorAlgorithmConfigurationService();
        }
        return algorithmConfigurationService;
    }

    public ITermBuilder getTermBuilder() {
        if (termBuilder == null) {
            termBuilder = new TermBuilder();
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
            weightCalculator = new WeightService(DaoFactory.INSTANCE.getWeightedVectorDao());
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
}
