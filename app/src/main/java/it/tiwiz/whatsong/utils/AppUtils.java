package it.tiwiz.whatsong.utils;

import android.content.pm.PackageManager;

import it.tiwiz.whatsong.R;
import it.tiwiz.whatsong.WhatSongApp;

/**
 * This class contains all the methods needed for the low-level
 * management of package data
 */
public class AppUtils {

    /**
     * @return a {@link java.lang.String} representing the package name linked to the given index
     */
    public static String getPackageNameFrom(int index){
        return getStringFromArray(R.array.softwares_packages, index);
    }

    /**
     * @return a {@link java.lang.String} representing the short title linked to the given index
     */
    public static String getShortTitleFrom(int index){
        return getStringFromArray(R.array.softwares_names_short, index);
    }

    /**
     * @return a {@link java.lang.String} representing the app title linked to the given index
     */
    public static String getTitleFrom(int index) {
        return getStringFromArray(R.array.softwares_names, index);
    }

    /**
     * This method will retrieve {@link java.lang.String} in position {@code index} from an array identified by the {@code arrayResourceId} parameter
     * @return the requested {@link java.lang.String} if present, {@code null} otherwise
     */
    private static String getStringFromArray(int arrayResourceId, int index) {
        String requestedString;

        try {
            requestedString = WhatSongApp.getInstance().getResources().getStringArray(arrayResourceId)[index];
        } catch(Exception e) {
            requestedString = null;
        }

        return requestedString;
    }
    /**
     * This method checks if the given package name is installed or not
     * @return a <b>boolean</b> value representing the installation status of the given package name
     */
    public static boolean isAppInstalled(String packageName){

        boolean appInstalled;
        PackageManager packageManager = WhatSongApp.getInstance().getPackageManager();

        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            appInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            appInstalled = false;
        }
        return appInstalled;
    }
}
