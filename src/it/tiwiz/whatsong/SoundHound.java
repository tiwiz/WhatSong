package it.tiwiz.whatsong;

import android.content.ComponentName;
import android.content.Intent;

/**
 * Created by Roby on 03/01/14.
 */
public class SoundHound implements ComplexIntent{

    private final static String SOUNDHOUND_TAG_NOW = "com.soundhound.android.appcommon.activity.SoundHoundIdNow";
    private String packageName = "";

    public SoundHound(String packageName){
        this.packageName = packageName;
    }

    @Override
    public Intent getInstance() {
        Intent soundHoundIntent = new Intent();
        ComponentName soundHoundComponent = new ComponentName(packageName,SOUNDHOUND_TAG_NOW);
        soundHoundIntent.setComponent(soundHoundComponent);
        return soundHoundIntent;
    }
}
