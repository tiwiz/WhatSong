package it.tiwiz.whatsong.utils;

import android.content.Context;
import android.content.Intent;

import it.tiwiz.whatsong.R;

/**
 * This class contains a unique method to create a shortcut {@link android.content.Intent} using
 * the {@link it.tiwiz.whatsong.utils.Shortcut} class.
 */
public class ShortcutUtils {

    public static Intent createShortcutIntent(final Context context, String[] listOfPackages,
                                              int position, String shortcutLabel,
                                              boolean isSpecificIcon) {

        final String selectedPackage = listOfPackages[position];
        final String packageName = listOfPackages[position];
        final int realPosition = IndexUtils.getRealPositionFrom(packageName);

        int resID;

        if (isSpecificIcon)
            resID = IconUtils.getMusicAppIconResourceID(realPosition);
        else
            resID = R.drawable.ic_launcher;

        Intent launchIntent = IntentUtils.getLaunchIntent(realPosition, selectedPackage);

        Shortcut shortcut = new Shortcut.Builder()
                .setIconResource(resID)
                .setLabel(shortcutLabel)
                .setLaunchIntent(launchIntent)
                .create(context);

        return shortcut.getShortcutIntent();
    }
}
