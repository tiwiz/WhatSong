package it.tiwiz.whatsong.mvp.interfaces;

import it.tiwiz.whatsong.utils.PackageData;

/**
 * This {@code interface} represent the list of installed packages and contains all the methods
 * to access the data.
 */
public interface WhatSongModel {

    /**
     * This method will populate the model by setting an array of
     * {@link it.tiwiz.whatsong.utils.PackageData} as internal field of the object
     */
    public void setInstalledPackages(PackageData[] data);

    /**
     * This method will retrieve the list of all the packages installed as an array
     */
    public String[] getPackages();

    /**
     * This method will retrieve an array of all the names associated with the installed packages
     * in the system
     */
    public String[] getPackagesNames();

    /**
     * This method will return the exact package as string contained at the given {@code position}
     * of the installed packages list
     */
    public String getPackage(int position);

    /**
     * This method will return the label associated at the given package indicated by the
     * {@code position} passed as input parameter
     */
    public String getPackageName(int position);
}
