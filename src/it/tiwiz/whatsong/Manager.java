package it.tiwiz.whatsong;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;

/**
 * Created by Roby on 03/01/14.
 */
public class Manager {

    private static Manager instance = null;
    private Context mContext;

    private Manager(Context mContext){
        this.mContext = mContext;
        instance = this;
    }

    public static Manager getInstance(Context mContext){

        if(null == instance)
            new Manager(mContext);

        return instance;
    }

    public int getIndex(String appTitle){

        Resources res = mContext.getResources();
        String[] titles = res.getStringArray(R.array.softwares_names);

        int index = 0;

        for(int i=0; i < titles.length; i++)
            if(appTitle.equalsIgnoreCase(titles[i])){
                index = i;
                i = titles.length; //exits from the cicle
            }
        return index;
    }

    public String getPackage(int index){
        return mContext.getResources().getStringArray(R.array.softwares_packages)[index];
    }

    public String getShortTitle(int index){
        return mContext.getResources().getStringArray(R.array.softwares_names_short)[index];
    }

    public int getIcon(int index){

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

    public int getBigIcon(int index){

        int resId = R.drawable.ic_launcher_vectorized;

        switch(index){
            case 0: //Sound Search
                resId = R.drawable.google_vectorized;
                break;
            case 1: //Shazam
            case 2:
                resId = R.drawable.shazam_vectorized;
                break;
            case 3: //SoundHound
            case 4:
                resId = R.drawable.soundhound_vectorized;
                break;
            case 5: //TrackID
                resId = R.drawable.trackid_vectorized;
                break;
            case 6: //musiXmatch
                resId = R.drawable.musixmatch_vectorized;
                break;
            case 7: //SoundTracking
                resId = R.drawable.soundtracking_vectorized;
                break;
        }

        return resId;
    }

    public Intent getIntent(int index, String pkg){

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

    public boolean isAppInstalled(String pkg){

        boolean appInstalled;
        PackageManager pm = mContext.getPackageManager();

        try {
            pm.getPackageInfo(pkg,PackageManager.GET_ACTIVITIES);
            appInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            appInstalled = false;
        }
        return appInstalled;
    }

    public int getRealPosition(String pkg){

        String[] packages = mContext.getResources().getStringArray(R.array.softwares_packages);
        int i;

        for(i = 0; (i < packages.length) && (!pkg.equals(packages[i])); i++);

        return i;
    }
}
