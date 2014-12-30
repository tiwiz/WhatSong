package it.tiwiz.whatsong.utils;

import android.content.Context;
import android.content.res.Resources;

import it.tiwiz.whatsong.R;
import it.tiwiz.whatsong.WhatSongApp;

/**
 * This class contains all the methods needed to retrieve and calculate the correct
 * position of any package name
 */
public class IndexUtils {

    /**
     * @return the correct internally used index for the given app title
     */
    public static int getInternalIndexFrom(String appTitle){

        Resources res = WhatSongApp.getInstance().getResources();
        String[] titles = res.getStringArray(R.array.softwares_names);

        int index = 0;

        for(int i=0; i < titles.length; i++){
            if (appTitle.equalsIgnoreCase(titles[i])) {
                index = i;
                i = titles.length; //exits from the cicle
            }
        }

        return index;
    }

    /**
     *
     * @return the real position of the package name inside the given list
     */
    public static int getRealPositionFrom(String packageName){

        String[] packages = WhatSongApp.getInstance().getResources().getStringArray(R.array.softwares_packages);
        int i;

        for(i = 0; (i < packages.length) && (!packageName.equals(packages[i])); i++);

        return i;
    }
}
