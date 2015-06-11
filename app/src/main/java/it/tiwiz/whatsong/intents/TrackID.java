package it.tiwiz.whatsong.intents;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * This class implements the {@link MusicAppIntent} <b>Interface</b>
 * that will launch the TrackID app by Sony
 */
public class TrackID extends MusicAppIntentTemplate {

    private static final String TRACKID_TAG_NOW = "com.sonyericsson.trackid.activity.WidgetHandlerActivity";

    public TrackID(String packageName) {
        super(packageName);
    }

    @NonNull
    @Override
    Intent configureIntent(@NonNull String packageName) {
        Intent trackIDIntent = new Intent();
        trackIDIntent.setClassName(packageName, TRACKID_TAG_NOW);
        trackIDIntent.setAction("android.intent.action.MAIN");
        trackIDIntent.putExtra("AUTO_START", true);
        trackIDIntent.putExtra("widgetLaunch", true);
        trackIDIntent.addCategory("android.intent.category.LAUNCHER");
        return trackIDIntent;
    }

}
