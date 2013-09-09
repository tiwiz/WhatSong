package it.tiwiz.whatsong;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Roby
 * Date: 08/09/13
 */
public class WhatSongExtension extends DashClockExtension{
    @Override
    protected void onUpdateData(int i) {

        //gets the sound provider
        //String appTitle = "Shazam Encore";
        //String appTitle = "SoundHound";
        String appTitle = "Sound Search";

        //creates data for SoundProvider
        int index = getIndex(appTitle);
        String pkg = getPackage(index);
        String shortTitle = getShortTitle(index);
        int icon = getIcon(index);
        String expandedBody = getResources().getString(R.string.expanded_body, appTitle);
        String contentDescription = getResources().getString(R.string.content_description,appTitle);
        Intent launchProvider = getIntent(index,pkg);

        ExtensionData data = new ExtensionData()
            .visible(true)
            .icon(icon)
            .status(shortTitle)
            .expandedTitle(appTitle)
            .expandedBody(expandedBody)
            .contentDescription(contentDescription)
            .clickIntent(launchProvider);

        publishUpdate(data);
    }

    private int getIndex(String appTitle){

        Resources res = getResources();
        String[] titles = res.getStringArray(R.array.softwares_names);

        int index = 0;

        for(int i=0; i < titles.length; i++)
            if(appTitle.equalsIgnoreCase(titles[i])){
                index = i;
                i = titles.length; //exits from the cicle
            }
        return index;
    }

    private String getPackage(int index){
        return getResources().getStringArray(R.array.softwares_packages)[index];
    }

    private String getShortTitle(int index){
        return getResources().getStringArray(R.array.softwares_names_short)[index];
    }

    private int getIcon(int index){

        int resId = R.drawable.icon;

        switch(index){
            case 0: //Sound Search
                resId = R.drawable.google;
                break;
            case 1: //Shazam
            case 2:
                resId = R.drawable.shazam;
                break;
            case 3: //SoundHound
            case 4:
                resId = R.drawable.soundhound;
                break;
            case 5: //TrackID
                break;
            case 6: //musiXmatch
                break;
            case 7: //SoundTracking
                break;
        }

        return resId;
    }

    private Intent getIntent(int index, String pkg){

        Intent intent = new Intent();

        switch(index){
            case 0: //Sound Search
                //intent.setClassName(pkg,C.GOOGLE_VOICE_SEARCH);
                intent = new Intent(C.GOOGLE_MUSIC_SEARCH);
                break;
            case 1: //Shazam
            case 2: //Shazam Encore
                intent = getPackageManager().getLaunchIntentForPackage(pkg);
                break;
            case 3: //SoundHound
            case 4: //SoundHound Pro
                ComponentName soundHoundComponent = new ComponentName(pkg,C.SOUNDHOUND_TAG_NOW);
                intent.setComponent(soundHoundComponent);
                break;
            case 5: //TrackID
                break;
            case 6: //musiXmatch
                break;
            case 7: //SoundTracking
                break;
        }

        return intent;
    }
}
