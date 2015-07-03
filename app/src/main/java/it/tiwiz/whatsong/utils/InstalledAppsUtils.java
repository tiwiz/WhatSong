package it.tiwiz.whatsong.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import it.tiwiz.whatsong.R;
import it.tiwiz.whatsong.WhatSongApp;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 *
 */
public class InstalledAppsUtils {

    @NonNull
    public static Observable<PackageData> getInstalledApps(final Context context) {

        return Observable.from(WhatSongApp.getStringArrayBy(R.array.softwares_packages))
                .zipWith(getAppNames(), PackageData::new)
                .filter(packageData -> isAppInstalled(context, packageData.getPackageName()))
                .subscribeOn(Schedulers.io());
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
