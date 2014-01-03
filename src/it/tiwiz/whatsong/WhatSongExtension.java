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

        String defaultProvider = (getResources().getStringArray(R.array.softwares_names)[0]);
        //gets the sound provider
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String appTitle = sp.getString(getString(R.string.pref_provider_key), defaultProvider);
        //and the other options
        boolean useAppSpecificIcon = sp.getBoolean(getString(R.string.pref_icon_key), true);
        boolean showExtendedBody = sp.getBoolean(getString(R.string.pref_extended_body),true);
        boolean useAppSpecificTitle = sp.getBoolean(getString(R.string.pref_app_name),true);
        boolean showShortTitle = sp.getBoolean(getString(R.string.pref_short_title),true);

        Manager manager = Manager.getInstance(this);

        //creates data for SoundProvider
        int index = manager.getIndex(appTitle);
        String pkg = manager.getPackage(index);

        //if app is not installed, default provider is choosen
        if(!manager.isAppInstalled(pkg)){
            appTitle = defaultProvider;
            index = manager.getIndex(defaultProvider);
            pkg = manager.getPackage(index);
        }

        String shortTitle = manager.getShortTitle(index);
        int icon;

        //choses correct icon depending on user's choice
        if(useAppSpecificIcon)
            icon = manager.getIcon(index);
        else
            icon = R.drawable.ic_launcher;

        String expandedBody = "";

        //overwrites app name is user wants so
        if(!useAppSpecificTitle){
            appTitle = getResources().getString(R.string.app_name);
            shortTitle = appTitle;
        }
        //shows extended body only if users wants it to do it
        if(showExtendedBody)
            expandedBody = getResources().getString(R.string.expanded_body, appTitle);

        //hides the short title if needed
        if(!showShortTitle)
            shortTitle = "";

        String contentDescription = getResources().getString(R.string.content_description,appTitle);
        Intent launchProvider = manager.getIntent(index, pkg);

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

        int resId = R.drawable.ic_launcher;

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
                resId = R.drawable.trackid;
                break;
            case 6: //musiXmatch
                resId = R.drawable.musixmatch;
                break;
            case 7: //SoundTracking
                resId = R.drawable.soundtracking;
                break;
        }

        return resId;
    }

    private Intent getIntent(int index, String pkg){

        ComplexIntent intent = new EmptyIntent();

        switch(index){
            case 0: //Sound Search
                intent = new SoundSearch();
                break;
            case 1: //Shazam
            case 2: //Shazam Encore
                intent = new Shazam();
                break;
            case 3: //SoundHound
            case 4: //SoundHound Pro
                intent = new SoundHound(pkg);
                break;
            case 5: //TrackID
                intent = new TrackID(pkg);
                break;
            case 6: //musiXmatch
                intent = new MusiXmatch(pkg);
                break;
            case 7: //SoundTracking
                intent = new SoundTracking(pkg);
                break;
        }

        return intent.getInstance();
    }

    private boolean isAppInstalled(String pkg){

        boolean appInstalled;
        PackageManager pm = getPackageManager();

        try {
            pm.getPackageInfo(pkg,PackageManager.GET_ACTIVITIES);
            appInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            appInstalled = false;
        }
        return appInstalled;
    }
}
