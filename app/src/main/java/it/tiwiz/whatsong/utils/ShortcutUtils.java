package it.tiwiz.whatsong.utils;

import android.content.Context;
import android.content.Intent;

import it.tiwiz.whatsong.R;

/**
 * This class contains a unique method to create a shortcut {@link android.content.Intent} using
 * the {@link it.tiwiz.whatsong.utils.Shortcut} class.
 */
public class ShortcutUtils {

    /**
     * This method generates the {@link android.content.Intent} that will create the shortcut once
     * set as result of any {@link android.app.Activity}
     * @param context any available {@link android.content.Context} used to retrieve the correct
     *                icon format for the final {@link android.content.Intent}
     * @param listOfPackages the list of packages currently installed in the system
     * @param position the clicked item position from the {@link android.widget.Spinner} from which
     *                 the user choices
     * @param shortcutLabel the {@link java.lang.String} taken from the
     *                      {@link android.widget.EditText} as modified by the user
     * @param isSpecificIcon the value from {@link android.support.v7.widget.SwitchCompat} from
     *                       which the user decides the kind of icon for the shortcut
     * @return a properly created {@link android.content.Intent} that will ask the Android system
     * to create a shortcut on user's home screen
     */
    public static Intent createShortcutIntent(final Context context, String[] listOfPackages,
                                              int position, String shortcutLabel,
                                              boolean isSpecificIcon) {

        final String selectedPackage = listOfPackages[position];
        final String packageName = listOfPackages[position];
        final int realPosition = IndexUtils.getRealPositionFrom(packageName, R.array.softwares_packages);
        int resID = getShortcutIconFrom(realPosition, isSpecificIcon);

        Intent launchIntent = IntentUtils.getLaunchIntent(realPosition, selectedPackage);

        Shortcut shortcut = new Shortcut.Builder()
                .setIconResource(resID)
                .setLabel(shortcutLabel)
                .setLaunchIntent(launchIntent)
                .create(context);

        return shortcut.getShortcutIntent();
    }

    /**
     * Decides between <b>WhatSong</b>'s icon or the one from the specific music recognition
     * provider based on the given position in the installed packages list and the user's preference
     * regarding the icon
     * @return the resource ID of the icon to be used as the one for the shortcut
     */
    private static int getShortcutIconFrom(int realPosition, boolean isSpecificIcon) {
        if (isSpecificIcon) {
            return IconUtils.getMusicAppIconResourceID(realPosition);
        }
        else {
            return R.drawable.ic_launcher;
        }
    }
}
