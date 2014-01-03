package it.tiwiz.whatsong;

import android.content.Intent;

/**
 * Created by Roby on 03/01/14.
 */
public class SoundSearch implements ComplexIntent{

    private final static String GOOGLE_MUSIC_SEARCH = "com.google.android.googlequicksearchbox.MUSIC_SEARCH";

    @Override
    public Intent getInstance() {
        return new Intent(GOOGLE_MUSIC_SEARCH);
    }
}
