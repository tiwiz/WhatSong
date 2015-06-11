package it.tiwiz.whatsong.intents;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * This class creates the Intent as used by MusiXmatch for searching the lyrics directly
 */
public class MusiXmatch extends MusicAppIntentTemplate {

    private static final String MUSIXMATCH_TAG_NOW = "com.designfuture.music.ui.phone.SearchLyricActivity";

    public MusiXmatch(@NonNull String packageName) {
        super(packageName);
    }

    @Override
    @NonNull
    Intent configureIntent(@NonNull String packageName) {
        Intent configuredIntent = new Intent();
        configuredIntent.setClassName(packageName, MUSIXMATCH_TAG_NOW);
        configuredIntent.putExtra("AUTO_START", true);
        configuredIntent.putExtra("com.musixmatch.android.lyrify.ui.fragment.autostart", true);
        return configuredIntent;
    }
}
