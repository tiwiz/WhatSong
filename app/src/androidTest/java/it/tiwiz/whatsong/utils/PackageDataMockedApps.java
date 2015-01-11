package it.tiwiz.whatsong.utils;


public class PackageDataMockedApps {

    private static final String TEST_PACKAGE_1 = "it.tiwiz.whatsong.test1";
    private static final String TEST_PACKAGE_2 = "it.tiwiz.whatsong.test2";
    private static final String TEST_NAME_1 = "TEST1";
    private static final String TEST_NAME_2 = "TEST2";

    public static final PackageData TEST_APP_1 = new PackageData(TEST_PACKAGE_1, TEST_NAME_1);
    public static final PackageData TEST_APP_2 = new PackageData(TEST_PACKAGE_2, TEST_NAME_2);

    public static final PackageData[] TEST_APPS = new PackageData[] {TEST_APP_1, TEST_APP_2};
    public static final String[] TEST_APPS_PACKAGES = new String[] {TEST_PACKAGE_1, TEST_PACKAGE_2};
    public static final String[] TEST_APPS_NAMES = new String[] {TEST_NAME_1, TEST_NAME_2};
}
