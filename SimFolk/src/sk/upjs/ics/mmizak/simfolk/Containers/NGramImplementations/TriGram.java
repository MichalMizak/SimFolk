package sk.upjs.ics.mmizak.simfolk.Containers.NGramImplementations;

import jdk.nashorn.internal.ir.annotations.Immutable;
import sk.upjs.ics.mmizak.simfolk.Containers.NGram;

@Immutable
    public class TriGram extends NGram {

        public TriGram(String lyricsFragment) {
            super(lyricsFragment);
        }

        @Override
        protected boolean validateLyricsFragment() {
            // TODO: Correct validation
            return false;
        }
    }