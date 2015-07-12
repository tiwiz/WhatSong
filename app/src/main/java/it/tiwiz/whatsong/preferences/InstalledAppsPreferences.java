package it.tiwiz.whatsong.preferences;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import it.tiwiz.whatsong.utils.InstalledAppsUtils;
import it.tiwiz.whatsong.utils.PackageData;
import rx.observers.Subscribers;

/**
 * This class represents an extension of {@link ListPreference} with the idea of letting me filter
 * the installed apps in the settings without the limitation of showing all the apps
 */
public class InstalledAppsPreferences extends ListPreference {

    List<String> entries;

    public InstalledAppsPreferences(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        updateInstalledAppsList(context);
    }

    public InstalledAppsPreferences(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        updateInstalledAppsList(context);
    }

    public InstalledAppsPreferences(Context context, AttributeSet attrs) {
        super(context, attrs);
        updateInstalledAppsList(context);
    }

    public InstalledAppsPreferences(Context context) {
        super(context);
        updateInstalledAppsList(context);
    }

    /**
     * This method will retrieve the installed apps list from the System, so that the user cannot
     * choose a non installed app and end up finding the Google Play Search instead.
     */
    private void updateInstalledAppsList(Context context) {
        InstalledAppsUtils.getInstalledApps(context)
                .subscribe(Subscribers.create(this::addInstalledAppToEntriesFrom,
                        (thowable -> {
                        }), this::convertEntriesListToArray));
    }

    /**
     * This method will extract the package label from the {@link PackageData} and will add it to
     * the {@link #entries}
     */
    private void addInstalledAppToEntriesFrom(PackageData packageData) {
        if (entries == null) {
            entries = new ArrayList<>();
        }

        entries.add(packageData.getPackageLabel());
    }

    /**
     * This method will convert the {@link List} to an {@code Array} so that it can call the internal
     * {@link #setEntries(CharSequence[])} with the filtered list of apps
     */
    private void convertEntriesListToArray() {
        final int entriesSize = entries.size();
        CharSequence[] entriesAsVector = new CharSequence[entriesSize];
        setEntries(entries.toArray(entriesAsVector));
    }


}
