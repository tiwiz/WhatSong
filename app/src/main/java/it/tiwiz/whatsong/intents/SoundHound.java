package it.tiwiz.whatsong.intents;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * This class uses the SoundHound internal Intent to trigger the listening
 */
public class SoundHound extends MusicAppIntentTemplate {

    private final static String SOUNDHOUND_TAG_NOW = "com.soundhound.android.appcommon.activity.SoundHoundIdNow";

    public SoundHound(@NonNull String packageName){
        super(packageName);
    }

    @Override
    @NonNull
    Intent configureIntent(@NonNull String packageName) {
        Intent configuredIntent = new Intent();
        ComponentName soundHoundComponent = new ComponentName(packageName, SOUNDHOUND_TAG_NOW);
        configuredIntent.setComponent(soundHoundComponent);
        return configuredIntent;
    }

}
