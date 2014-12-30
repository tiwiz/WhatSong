package it.tiwiz.whatsong.intents;

import android.content.ComponentName;
import android.content.Intent;

/**
 * This class uses the SoundHound internal Intent to trigger the listening
 */
public class SoundHound implements MusicAppIntent {

    private final static String SOUNDHOUND_TAG_NOW = "com.soundhound.android.appcommon.activity.SoundHoundIdNow";
    private String packageName = "";

    public SoundHound(String packageName){
        this.packageName = packageName;
    }

    @Override
    public Intent getInstance() {
        Intent soundHoundIntent = new Intent();
        ComponentName soundHoundComponent = new ComponentName(packageName, SOUNDHOUND_TAG_NOW);
        soundHoundIntent.setComponent(soundHoundComponent);
        return soundHoundIntent;
    }
}
