package it.tiwiz.whatsong.mvp;

import it.tiwiz.whatsong.mvp.interfaces.WhatSongModel;
import it.tiwiz.whatsong.utils.PackageData;


/**
 * This class implements the {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongModel} {@code interface}.
 *
 * Please refer to {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongModel} for the JavaDoc explaining
 * the meaning of the following methods.
 */
public class ShortcutModel implements WhatSongModel{
    private String[] modelPackages;
    private String[] modelPackagesNames;

    @Override
    public void setInstalledPackages(PackageData[] data) {
        modelPackages = PackageData.Utils.getPackages(data);
        modelPackagesNames = PackageData.Utils.getNames(data);
    }

    @Override
    public String[] getPackages() {
        return modelPackages;
    }

    @Override
    public String[] getPackagesNames() {
        return modelPackagesNames;
    }

    @Override
    public String getPackage(int position) {
        return getStringFrom(modelPackages, position);
    }

    @Override
    public String getPackageName(int position) {
        return getStringFrom(modelPackagesNames, position);
    }

    private String getStringFrom(String[] arrayOfData, int position) {
        return (arrayOfData != null && position <= arrayOfData.length) ? arrayOfData[position] : null;
    }
}
