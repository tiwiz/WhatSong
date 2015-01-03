package it.tiwiz.whatsong.mvp.interfaces;


import android.content.Intent;
import android.widget.BaseAdapter;

/**
 * This {@code interface} represents the View that manages the changes in the UI and gathers the
 * input from the user.
 */
public interface WhatSongView {

    /**
     * This method is called when there is the need of changing the big icon in the app
     * @param resourceId the id from {@link android.content.res.Resources} used to retrieve the image
     */
    public void onChangeBigIcon(int resourceId);

    /**
     * This method is called whenever an update of the installed providers list is performed
     * @param adapter the {@link android.widget.BaseAdapter} used by the {@link android.widget.Spinner}
     *                in the main UI window
     */
    public void onUpdateListAdapter(BaseAdapter adapter);

    /**
     * This method is called when the proper {@link android.widget.EditText} needs to be updated
     * with a new label for the shortcut
     * @param newShortcutName the {@link java.lang.String} used as label that will appear in the
     *                        {@link android.widget.EditText} itself
     */
    public void onUpdateShortcutName(String newShortcutName);

    /**
     * This method is called when the shortcut needs to be created, so that the View can simply
     * set its own result as OK and terminate the execution.
     * @param shortcutIntent to use as result
     */
    public void onShortcutIntentCreated (Intent shortcutIntent);
}
