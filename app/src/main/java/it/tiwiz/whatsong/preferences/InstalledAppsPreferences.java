package it.tiwiz.whatsong.preferences;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import it.tiwiz.whatsong.utils.InstalledAppsUtils;
import it.tiwiz.whatsong.utils.PackageData;

/**
 * This class represents an extension of {@link ListPreference} with the idea of letting me filter
 * the installed apps in the settings without the limitation of showing all the apps
 */
public class InstalledAppsPreferences extends ListPreference {

    List<String> entries;

    public InstalledAppsPreferences(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        InstalledAppsUtils.getInstalledApps(context)
                .doOnNext(this::addInstalledAppToEntriesFrom)
                .doOnCompleted(() -> setEntries((CharSequence[]) entries.toArray()));
    }

    private void addInstalledAppToEntriesFrom(PackageData packageData) {
        if (entries == null) {
            entries = new ArrayList<>();
        }

        entries.add(packageData.getPackageLabel());
    }


}
