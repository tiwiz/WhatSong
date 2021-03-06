package it.tiwiz.whatsong.intents;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * This class uses the public Shazam Intent to start the tagging
 */
public class Shazam implements MusicAppIntent {

    private final static String SHAZAM_INTENT = "com.shazam.android.intent.actions.START_TAGGING";

    @Override
    @NonNull
    public Intent getInstance() {
        return new Intent(SHAZAM_INTENT);
    }
}
