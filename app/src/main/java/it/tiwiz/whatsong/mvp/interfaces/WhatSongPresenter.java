package it.tiwiz.whatsong.mvp.interfaces;

import it.tiwiz.whatsong.utils.PackageData;

/**
 * This {@code interface} represents the <b>presenter</b> in the MVP paradigm, as of the glue that
 * connect the model and the view.
 *
 * Implementations of this {@code interface} will contain all the logic needed for the software to
 * correctly behave.
 *
 * @see it.tiwiz.whatsong.mvp.interfaces.WhatSongView the view
 * @see it.tiwiz.whatsong.mvp.interfaces.WhatSongModel the model
 */
public interface WhatSongPresenter {

    /**
     * This method is called whenever a new item has been selected in the {@link android.widget.Spinner}
     * present in the UI.
     * @param position the clicked position as returned by the
     *                 {@link android.widget.Spinner}'s event
     * @param isSelected the value taken from the current value of the
     *                   {@link android.support.v7.widget.SwitchCompat#isChecked()} method
     */
    public void onProviderSelected(int position, boolean isSelected);

    /**
     * This method is called every time the {@link android.support.v7.widget.SwitchCompat} changes
     * its {@code checked} status
     * @param isSelected the value taken from the current value of the
     *                   {@link android.support.v7.widget.SwitchCompat#isChecked()} method
     */
    public void onProviderIconChanged(boolean isSelected);

    /**
     * This method is called every time the {@code Floating Action Button} is clicked and will take
     * care of performing the main activity of the windows
     * @param selectedPackageLabel the selected label as modified by the user and taken from the
     *                             {@link android.widget.EditText} in the UI
     * @param isSpecificIconSelected the value taken from the current value of the
     *                               {@link android.support.v7.widget.SwitchCompat#isChecked()}
     *                               method
     */
    public void onShortcutRequest(String selectedPackageLabel, boolean isSpecificIconSelected);

    /**
     * This method is called when the packages have been retrieved and will then initialize the
     * {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongModel} with the given input.
     * @param packageData is the list of {@link it.tiwiz.whatsong.utils.PackageData} as returned by
     *                    the {@link it.tiwiz.whatsong.SearchInstalledProvidersService}.
     * @see {@link it.tiwiz.whatsong.utils.IntentUtils#getStartInstalledProvidersService(android.content.Context)}
     * how to start the Service
     * @see {@link it.tiwiz.whatsong.utils.IntentUtils.Filters#getSendInstalledProdiverResponseFilter()}
     * how to handle the response
     */
    public void onPackagesRetrieved(PackageData[] packageData);
}
