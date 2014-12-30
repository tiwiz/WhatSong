package it.tiwiz.whatsong.settings;

import it.tiwiz.whatsong.utils.IndexUtils;

/**
 * This class creates an object containing all the data gathered from the Settings
 */
public class SettingsData {

    final String defaultProvider;
    String appTitle;
    final boolean useAppSpecificIcon;
    final boolean showExtendedBody;
    final boolean useAppSpecificTitle;
    final boolean showShortTitle;
    int index;

    public SettingsData() {
        defaultProvider = Settings.getDefaultMusicRecognitionProvider();
        appTitle = Settings.getAppTitleBasedOnGivenProvider(defaultProvider);
        useAppSpecificIcon = Settings.getUseAppSpecificIcon();
        showExtendedBody = Settings.getShowExtendedBody();
        useAppSpecificTitle = Settings.getUseAppSpecificName();
        showShortTitle = Settings.getUseAppShortName();
        index = IndexUtils.getInternalIndexFrom(appTitle);
    }

    public String getDefaultProvider() {
        return defaultProvider;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public boolean isUseAppSpecificIcon() {
        return useAppSpecificIcon;
    }

    public boolean isShowExtendedBody() {
        return showExtendedBody;
    }

    public boolean isUseAppSpecificTitle() {
        return useAppSpecificTitle;
    }

    public boolean isShowShortTitle() {
        return showShortTitle;
    }

    public int getIndex() {
        return index;
    }

    /**
     * This method is useful when the chosen provider is not installed and
     * the app needs to automatically set the default provider
     */
    public void resetToDefaulProvider() {
        appTitle = defaultProvider;
        index = IndexUtils.getInternalIndexFrom(defaultProvider);
    }
}
