package it.tiwiz.whatsong;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

import it.tiwiz.whatsong.utils.IntentUtils;
import it.tiwiz.whatsong.utils.PackageData;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class SearchInstalledProvidersService extends IntentService {

    private static final String TAG = SearchInstalledProvidersService.class.getSimpleName();
    public static final String SEARCH_PROVIDERS_RESPONSE = TAG + ".SearchProvidersResponse";
    public static final String SEARCH_INSTALLED_PROVIDERS = TAG + ".SearchInstalledProviders";
    public static final String SEARCH_NOT_INSTALLED_PROVIDERS = TAG + ".SearchNotInstalledProviders";

    public static final String SEARCH_PROVIDERS_KEY = "SearchProvidersKey";

    private final String[] allProvidersPackages, allProvidersNames;
    private boolean showInstalledPackages;
    private PackageManager packageManager;

    {
        allProvidersPackages = WhatSongApp.getStringArrayBy(R.array.softwares_packages);
        allProvidersNames = WhatSongApp.getStringArrayBy(R.array.softwares_names);
    }

    public SearchInstalledProvidersService() {
        super("SearchInstalledProvidersService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (isPackagesListRequest(action)) {
                handlePackageRequest(action);
            }
        }
    }

    /**
     * This method will check if the given Action is a request for installed or
     * not installed packages
     */
    protected boolean isPackagesListRequest(String action) {
        return (action.equals(SEARCH_INSTALLED_PROVIDERS) ||
                action.equals(SEARCH_NOT_INSTALLED_PROVIDERS));
    }

    /**
     * This method is the skeleton that must be called when dealing with requests for
     * installed and not installed packages
     */
    protected void handlePackageRequest(String action) {

        showInstalledPackages = isInstalledPackagesListRequest(action);
        ArrayList<PackageData> resultList = getPackagesBasedOnStatus();
        PackageData[] result = getArrayFrom(resultList);
        sendResponse(result);
    }

    /**
     * This method will check if the given action is a call for installed
     * packages
     */
    protected boolean isInstalledPackagesListRequest(String action) {
        return (action.equals(SEARCH_INSTALLED_PROVIDERS));
    }

    /**
     * This method will retrieve every package that has been requested.
     * <b>Note</b>: this logic has been written to work with both installed
     * and not installed packages filter.
     */
    protected ArrayList<PackageData> getPackagesBasedOnStatus() {

        ArrayList<PackageData> packages = new ArrayList<>();

        packageManager = getPackageManager();
        String currentPackage;

        for(int i = 0; i < allProvidersPackages.length; i++){
            currentPackage = allProvidersPackages[i];
            if (shouldAddPackage(currentPackage)) {
                packages.add(new PackageData(currentPackage, allProvidersNames[i]));
            }
        }

        return packages;
    }

    /**
     * This method decides if the given package name shall be added to the list using the following rules
     * <b>Add if</b>
     * <ul>
     *     <li>if the package is installed and only the installed packages are requested</li>
     *     <li>if the package is <b>not</b> installed and only the not installed packages are requested</li>
     * </ul>
     *
     * <b>Note</b>: given the two conditions, we can easily see that the conditions can be reduced to
     * <ol>
     *     <li>{@code isPackageInstalled && showInstalledOnly}</li>
     *     <li>{@code !isPackageInstalled && !showInstalledOnly}</li>
     * </ol>
     * from these 2 conditions, we can see that the package should be added only if both the conditions
     * are verified or if both are <b>not</b> verified so the package is added only if the two conditions
     * are equal.
     */
    protected boolean shouldAddPackage(String packageName) {
        return (isPackageInstalled(packageName) == showInstalledPackages);
    }

    /**
     * This method checks if the given package is installed in the system
     */
    protected boolean isPackageInstalled(String packageName) {
        boolean isInstalled = true;
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            isInstalled = false;
        }
        return isInstalled;
    }

    protected PackageData[] getArrayFrom(ArrayList<PackageData> data) {
        final int resultSize = data.size();
        PackageData[] result = new PackageData[resultSize];

        for(int i = 0; i < resultSize; i++) {
            result[i] = data.get(i);
        }

        return result;
    }

    /**
     * This method sends back the list of {@link it.tiwiz.whatsong.utils.PackageData} containing
     * the requested packages using a {@link android.support.v4.content.LocalBroadcastManager}
     */
    protected void sendResponse(PackageData[] packageData) {
        final Intent responseIntent = IntentUtils.getSendInstalledProvidersResponse(packageData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(responseIntent);
    }

}
