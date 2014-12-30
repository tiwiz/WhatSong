package it.tiwiz.whatsong.intents;

import android.content.Intent;

/**
 * This class implements the correct Intent for the SoundTracking app
 */
public class SoundTracking implements MusicAppIntent {

    private static final String SOUNDTRACKING_TAG_NOW = "com.schematiclabs.soundtracking.activities.ListenActivity";
    private String packageName = "";

    public SoundTracking(String packageName){
        this.packageName = packageName;
    }
    @Override
    public Intent getInstance() {
        Intent soundTrackingIntent = new Intent();
        soundTrackingIntent.setClassName(packageName,SOUNDTRACKING_TAG_NOW);
        soundTrackingIntent.putExtra("widgetLaunch",true);
        return soundTrackingIntent;
    }
}
