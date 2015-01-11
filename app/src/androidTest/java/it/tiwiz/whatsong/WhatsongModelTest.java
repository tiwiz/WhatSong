package it.tiwiz.whatsong;

import junit.framework.TestCase;

import it.tiwiz.whatsong.mvp.ShortcutModel;
import it.tiwiz.whatsong.mvp.interfaces.WhatSongModel;
import it.tiwiz.whatsong.utils.PackageDataMockedApps;


public class WhatSongModelTest extends TestCase {

    private WhatSongModel testedModel;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        testedModel = new ShortcutModel();
        testedModel.setInstalledPackages(PackageDataMockedApps.TEST_APPS);
    }

    public void testModelCreated() throws Exception {
        assertNotNull(testedModel);
    }

    public void testGetPackages() throws Exception {
        String[] packages = testedModel.getPackages();

        assertNotNull(packages);
        assertEquals(packages.length, PackageDataMockedApps.TEST_APPS.length);

        for(int i = 0; i < packages.length; i++) {
            assertEquals(packages[i], PackageDataMockedApps.TEST_APPS_PACKAGES[i]);
        }
    }

    public void testGetNames() throws Exception {
        String[] packages = testedModel.getPackagesNames();

        assertNotNull(packages);
        assertEquals(packages.length, PackageDataMockedApps.TEST_APPS.length);

        for(int i = 0; i < packages.length; i++) {
            assertEquals(packages[i], PackageDataMockedApps.TEST_APPS_NAMES[i]);
        }
    }

    public void testSinglePackageData() throws Exception {

        String pkg, pkgName;
        pkg = testedModel.getPackage(0);
        pkgName = testedModel.getPackageName(0);

        assertNotSame(pkg, pkgName);
        assertEquals(pkg, PackageDataMockedApps.TEST_APPS_PACKAGES[0]);
        assertEquals(pkgName, PackageDataMockedApps.TEST_APPS_NAMES[0]);
    }
}
