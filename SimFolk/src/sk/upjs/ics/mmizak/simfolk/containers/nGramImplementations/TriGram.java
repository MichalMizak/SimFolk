package sk.upjs.ics.mmizak.simfolk.containers.nGramImplementations;

import jdk.nashorn.internal.ir.annotations.Immutable;
import sk.upjs.ics.mmizak.simfolk.containers.NGram;

@Immutable
    public class TriGram extends NGram {

        public TriGram(String lyricsFragment) {
            super(lyricsFragment);
        }

        @Override
        protected boolean validateLyricsFragment() {
            // TODO: Correct validation
            return true;
        }
    }