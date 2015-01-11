package it.tiwiz.whatsong;

import junit.framework.TestCase;

import it.tiwiz.whatsong.utils.PackageData;
import it.tiwiz.whatsong.utils.PackageDataMockedApps;

public class PackageDataTest extends TestCase{


    private PackageData[] testedData;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        testedData = PackageDataMockedApps.TEST_APPS;
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        testedData = null;
    }

    public void testNotNull() throws Exception {
        assertNotNull(testedData);
    }

    public void testGetPackages() throws Exception {

        String[] packages = PackageData.Utils.getPackages(testedData);
        assertNotNull(packages);
        assertEquals(packages.length, PackageDataMockedApps.TEST_APPS_PACKAGES.length);

        for (int i = 0; i < packages.length; i++) {
            assertEquals(packages[i], PackageDataMockedApps.TEST_APPS_PACKAGES[i]);
        }
    }

    public void testGetLabels() throws Exception {

        String[] packages = PackageData.Utils.getNames(testedData);
        assertNotNull(packages);
        assertEquals(packages.length, PackageDataMockedApps.TEST_APPS_NAMES.length);

        for (int i = 0; i < packages.length; i++) {
            assertEquals(packages[i], PackageDataMockedApps.TEST_APPS_NAMES[i]);
        }
    }
}
