package it.tiwiz.whatsong.mvp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import it.tiwiz.whatsong.R;
import it.tiwiz.whatsong.mvp.interfaces.WhatSongModel;
import it.tiwiz.whatsong.mvp.interfaces.WhatSongPresenter;
import it.tiwiz.whatsong.mvp.interfaces.WhatSongView;
import it.tiwiz.whatsong.utils.IconUtils;
import it.tiwiz.whatsong.utils.IndexUtils;
import it.tiwiz.whatsong.utils.InstalledAppsUtils;
import it.tiwiz.whatsong.utils.PackageData;
import it.tiwiz.whatsong.utils.ShortcutUtils;
import rx.Subscriber;
import rx.observers.Subscribers;

/**
 * This class implement the {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongPresenter} {@code interface},
 * please refer to the superclass for the generic JavaDoc.
 * <br/><br/>
 * <b>Expected behaviour</b>
 * <ul>
 * <li>when a new item is selected as per {@link android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)}
 * the icon must be changed and the label must be updated</li>
 * <li>when the {@link android.support.v7.widget.SwitchCompat} changes its status, the big icon
 * must be changed</li>
 * </ul>
 */
public class ShortcutPresenter implements WhatSongPresenter {

    private WhatSongView whatSongView;
    private WhatSongModel whatSongModel;
    private int lastSelectedPosition;
    private ArrayList<PackageData> installedApps;

    private static final String TAG = ShortcutPresenter.class.getSimpleName();

    /**
     * This constructor initializes a new model and starts keeping track of the last selected
     * position and the {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongView} connected to
     * the <b>presenter</b> itself.
     */
    public ShortcutPresenter(WhatSongView whatSongView) {
        this(whatSongView, new ShortcutModel());
    }

    /**
     * This constructor initializes a new model and starts keeping track of the last selected
     * position and the {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongView} connected to
     * the <b>presenter</b> itself, with the possibility of setting a different
     * {@link WhatSongModel}
     */
    public ShortcutPresenter(WhatSongView whatSongView, WhatSongModel whatSongModel) {
        this.whatSongView = whatSongView;
        this.whatSongModel = whatSongModel;
        lastSelectedPosition = 0;
        installedApps = new ArrayList<>();
    }

    /**
     * Upon item selection, this method will retrieve the label for the given music recognition
     * app and sets it in the {@link android.widget.EditText}, so that the user can easily change
     * and modify the label before creating the shortcut
     */
    @Override
    public void onProviderSelected(int position, boolean isSelected) {
        lastSelectedPosition = position;
        String newLabelForShortcut = whatSongModel.getPackageName(lastSelectedPosition);
        whatSongView.onUpdateShortcutName(newLabelForShortcut);
        onProviderIconChanged(isSelected);
    }

    /**
     * Upon switching event from the UI, this method will retrieve the correct icon and trigger
     * the {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongView#onChangeBigIcon(int)} event
     * as per guidelines
     */
    @Override
    public void onProviderIconChanged(boolean isSelected) {
        int newIcon;
        if (isSelected) {
            newIcon = getIconResourceForLastSelectedItem();
        } else {
            newIcon = R.drawable.whatsong_logo;
        }
        whatSongView.onChangeBigIcon(newIcon);
    }

    /**
     * When the switch is selected, this method will find the correct icon associated with the
     * last selected item and return it.
     */
    private int getIconResourceForLastSelectedItem() {
        final String packageName = whatSongModel.getPackageName(lastSelectedPosition);
        final int realPosition = IndexUtils.getRealPositionFrom(packageName, R.array.softwares_names);
        return IconUtils.getMusicAppBigIconResourceID(realPosition);
    }

    /**
     * When the {@code Floating Action Button} is clicked, this method will create the correct
     * {@link android.content.Intent} and will then trigger the
     * {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongView#onShortcutIntentCreated(android.content.Intent)}
     * as per guidelines.
     */
    @Override
    public void onShortcutRequest(String selectedPackageLabel,
                                  boolean isSpecificIconSelected) {

        Intent shortcutIntent = ShortcutUtils.createShortcutIntent(whatSongView.getViewContext(), whatSongModel.getPackages(),
                lastSelectedPosition, selectedPackageLabel, isSpecificIconSelected);
        whatSongView.onShortcutIntentCreated(shortcutIntent);
    }

    /**
     * When the installed packages are retrieved, this method is invoked and will create the
     * {@link android.widget.ArrayAdapter} for the {@link android.widget.Spinner}, will trigger
     * both events <ul>
     * <li>{@link it.tiwiz.whatsong.mvp.interfaces.WhatSongView#onUpdateListAdapter(android.widget.BaseAdapter)}</li>
     * <li>{@link it.tiwiz.whatsong.mvp.interfaces.WhatSongView#onUpdateShortcutName(String)}</li>
     * </ul>
     * as per guidelines.
     */
    @Override
    public void onPackagesRetrieved(PackageData[] packageData) {

        whatSongModel.setInstalledPackages(packageData);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(whatSongView.getViewContext(), R.layout.custom_spinner, whatSongModel.getPackagesNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        whatSongView.onUpdateListAdapter(adapter);
        whatSongView.onUpdateShortcutName(whatSongModel.getPackageName(0));
    }

    /**
     * This method will get the installed apps from another thread and will then call the
     * {@link #onPackagesRetrieved(PackageData[])} to set the data in the spinner
     * @param context
     */
    @Override
    public void onInstalledPackagesRequested(final Context context) {

        InstalledAppsUtils.getInstalledApps(context)
                .subscribe(Subscribers.create(
                        (installedApps::add),
                        (thowable -> {
                            //TODO add a notification for the User
                        }),
                        (this::convertInstalledAppsListToVector)
                ));
    }

    /**
     * This method will create an array the same size of the installed apps list and will feed
     * the {@code Presenter} with the data that will be displayed in the user interface
     */
    private void convertInstalledAppsListToVector() {
        final int numberOfApps = installedApps.size();
        PackageData[] packageData = new PackageData[numberOfApps];
        onPackagesRetrieved(installedApps.toArray(packageData));
    }


}
