package it.tiwiz.whatsong.intents;

import android.content.Intent;

/**
 * This app implements the Intent for the Google Music Search feature
 */
public class SoundSearch implements MusicAppIntent {

    private final static String GOOGLE_MUSIC_SEARCH = "com.google.android.googlequicksearchbox.MUSIC_SEARCH";

    @Override
    public Intent getInstance() {
        return new Intent(GOOGLE_MUSIC_SEARCH);
    }
}
