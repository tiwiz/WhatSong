package it.tiwiz.whatsong.intents;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * This class adds compatibility for LyricsMania/AngoloTesti
 */
public class LyricsMania extends MusicAppIntentTemplate {


    private final static String LYRICS_MANIA_START_SEARCH = "com.x3.angolotesti.SearchActivity";

    public LyricsMania(@NonNull String packagName) {
        super(packagName);
    }

    @Override
    @NonNull
    Intent configureIntent(@NonNull String packageName) {
        Intent lyricsManiaIntent = new Intent();
        lyricsManiaIntent.setClassName(packageName, LYRICS_MANIA_START_SEARCH);
        return lyricsManiaIntent;
    }

}
