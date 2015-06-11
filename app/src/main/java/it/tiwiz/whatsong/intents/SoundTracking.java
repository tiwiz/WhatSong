package it.tiwiz.whatsong.intents;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * This class implements the correct Intent for the SoundTracking app
 */
public class SoundTracking extends MusicAppIntentTemplate {

    private static final String SOUNDTRACKING_TAG_NOW = "com.schematiclabs.soundtracking.activities.ListenActivity";

    public SoundTracking(String packageName) {
        super(packageName);
    }

    @Override
    @NonNull
    Intent configureIntent(@NonNull String packageName) {
        Intent soundTrackingIntent = new Intent();
        soundTrackingIntent.setClassName(packageName, SOUNDTRACKING_TAG_NOW);
        soundTrackingIntent.putExtra("widgetLaunch", true);
        return soundTrackingIntent;
    }
}
