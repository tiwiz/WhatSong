package it.tiwiz.whatsong;

import android.content.Intent;

/**
 * Created by Roby on 03/01/14.
 */
public class SoundTracking implements ComplexIntent{

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
