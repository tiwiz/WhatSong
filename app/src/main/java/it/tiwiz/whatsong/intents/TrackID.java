package it.tiwiz.whatsong.intents;

import android.content.Intent;

/**
 * This class implements the {@link MusicAppIntent} <b>Interface</b>
 * that will launch the TrackID app by Sony
 */
public class TrackID implements MusicAppIntent {

    private static final String TRACKID_TAG_NOW = "com.sonyericsson.trackid.activity.WidgetHandlerActivity";
    private String packageName = "";

    public TrackID(String packageName){
        this.packageName = packageName;
    }

    @Override
    public Intent getInstance() {
        Intent trackIDIntent = new Intent();
        trackIDIntent.setClassName(packageName, TRACKID_TAG_NOW);
        trackIDIntent.setAction("android.intent.action.MAIN");
        trackIDIntent.putExtra("AUTO_START",true);
        trackIDIntent.putExtra("widgetLaunch",true);
        trackIDIntent.addCategory("android.intent.category.LAUNCHER");
        return trackIDIntent;
    }
}
