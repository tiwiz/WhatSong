package it.tiwiz.whatsong;

import android.content.Intent;

/**
 * Created by Roby on 03/01/14.
 */
public class TrackID implements ComplexIntent{

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
