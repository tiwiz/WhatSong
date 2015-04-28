package it.tiwiz.whatsong.utils;

import it.tiwiz.whatsong.mvp.interfaces.WhatSongModel;

public class MockedModel implements WhatSongModel{
    @Override
    public void setInstalledPackages(PackageData[] data) {

    }

    @Override
    public String[] getPackages() {
        return PackageDataMockedApps.TEST_APPS_PACKAGES;
    }

    @Override
    public String[] getPackagesNames() {
        return PackageDataMockedApps.TEST_APPS_NAMES;
    }

    @Override
    public String getPackage(int position) {
        return PackageDataMockedApps.TEST_APPS_PACKAGES[position];
    }

    @Override
    public String getPackageName(int position) {
        return PackageDataMockedApps.TEST_APPS_NAMES[position];
    }
}
