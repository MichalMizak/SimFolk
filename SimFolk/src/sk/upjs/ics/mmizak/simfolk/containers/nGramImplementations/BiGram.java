package sk.upjs.ics.mmizak.simfolk.containers.nGramImplementations;

import jdk.nashorn.internal.ir.annotations.Immutable;
import sk.upjs.ics.mmizak.simfolk.containers.NGram;

@Immutable
    public class BiGram extends NGram {
        public BiGram(String lyricsFragment) {
            super(lyricsFragment);
        }

        @Override
        protected boolean validateLyricsFragment() {
            // TODO: Correct validation
            return true;
        }
    }
