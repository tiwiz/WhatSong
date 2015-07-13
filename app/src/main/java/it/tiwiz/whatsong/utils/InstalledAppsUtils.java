package it.tiwiz.whatsong.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import it.tiwiz.whatsong.R;
import it.tiwiz.whatsong.WhatSongApp;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * This class contains the utility functions needed to retrieve the installed apps matching the
 * criteria (AKA are music recognition apps)
 *
 * @see #getInstalledApps(Context)
 */
public class InstalledAppsUtils {

    /**
     * This method retrieves the list of apps installed in the system by using the following algorithm
     * <ol>
     *     <li>Emits one by one all the packages we want to check</li>
     *     <li>Emits, for each of the packages, the corresponding package name and zips it together with
     *     the previous data, into a {@link PackageData}</li>
     *     <li>Emits on the Android Main Thread only the packages that are actually found on the system.</li>
     * </ol>
     */
    @NonNull
    public static Observable<PackageData> getInstalledApps(final Context context) {

        return Observable.from(WhatSongApp.getStringArrayBy(R.array.softwares_packages))
                .zipWith(getAppNames(), PackageData::new)
                .filter(packageData -> isAppInstalled(context, packageData.getPackageName()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> getAppNames() {
        return Observable.from(WhatSongApp.getStringArrayBy(R.array.softwares_names));
    }

    @WorkerThread
    private static boolean isAppInstalled(final Context context, String packageName) {
        boolean isAppInstalled = true;

        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            isAppInstalled = false;
        }

        return isAppInstalled;
    }
}
