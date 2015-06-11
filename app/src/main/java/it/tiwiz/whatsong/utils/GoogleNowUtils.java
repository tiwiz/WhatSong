package it.tiwiz.whatsong.utils;

import android.content.Intent;

import it.tiwiz.whatsong.intents.IntentFactory;
import it.tiwiz.whatsong.settings.SettingsData;

/**
 * This class contains the methods needed to override the Google Now behaviour and
 * launch the chosen music recognition provider
 */
public class GoogleNowUtils {

    /**
     * @return Intent needed to launch the chosen music recognition provider or Google Now if none
     * of the others is present
     */
    public static Intent getIntentForChosenProvider() {

        SettingsData settingsData = new SettingsData(SettingsData.GOOGLE_NOW);

        if (!SettingsData.Rules.isChosenAppInstalled(settingsData)) {
            settingsData.resetToDefaulProvider();
        }

        final String packageName = AppUtils.getPackageNameFrom(settingsData.getIndex());
        return IntentFactory.getLaunchIntentFor(settingsData.getIndex(), packageName);
    }
}
