package it.tiwiz.whatsong;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import it.tiwiz.whatsong.mvp.ShortcutPresenter;
import it.tiwiz.whatsong.mvp.interfaces.WhatSongPresenter;
import it.tiwiz.whatsong.mvp.interfaces.WhatSongView;
import it.tiwiz.whatsong.utils.IntentUtils;
import it.tiwiz.whatsong.utils.PackageData;
import it.tiwiz.whatsong.views.AnimatedImageView;

/**
 * This {@link android.app.Activity} implements the {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongView}
 * {@code interface}. Based on the idea of MVP, every user input is redirected to the <b>presenter</b>,
 * while the presenter itself will invoke this <b>view</b> when a UI update is needed.
 * <br/><br/>
 * <b>MVP paradigm:</b><br/>
 * {@code View} <===> {@code Presenter} <===> {@code Model}
 * <br/><br/>
 * @see it.tiwiz.whatsong.mvp.interfaces.WhatSongView View interface
 * @see it.tiwiz.whatsong.mvp.interfaces.WhatSongPresenter Presenter interface
 * @see it.tiwiz.whatsong.mvp.interfaces.WhatSongModel Model interface
 * @see it.tiwiz.whatsong.mvp.ShortcutPresenter Presenter implementation
 * @see it.tiwiz.whatsong.mvp.ShortcutModel Model implementation
 */
public class ShortcutActivity extends Activity implements AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener, View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener, WhatSongView {

    private EditText editTextShortcutName;
    private Spinner spinnerInstalledProviders;
    private SwitchCompat switchIcon;
    private AnimatedImageView imgLogo;
    private Toolbar toolbar;
    private ImageButton fabCreateShortcut;

    private WhatSongPresenter whatSongPresenter;
    private final BroadcastReceiver updateReceiver = new ServiceUpdatesReceiver();
    private final IntentFilter updateFilter = IntentUtils.Filters.getSendInstalledProdiverResponseFilter();

    /**
     * During the {@code onCreate} phase, the {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongPresenter}
     * is initialized and the Activity data are set.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shortcut_activity);
        whatSongPresenter = new ShortcutPresenter(this);
        initActivity();
    }

    /**
     * This method is the main hub for all the initializations methods and it's invoked during the
     * {@link #onCreate(android.os.Bundle)} of the Activity
     */
    private void initActivity() {
        initVTO();
        findViews();
        setViewListeners();
    }

    /**
     * Initialization of the {@link android.view.ViewTreeObserver} for positioning correctly the
     * {@code Floating Action Button}
     */
    private void initVTO() {
        View content = findViewById(R.id.contentParent);
        content.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * This method connects all the needed {@link android.view.View}s with the variables
     */
    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fabCreateShortcut = (ImageButton) findViewById(R.id.fabMake);
        editTextShortcutName = (EditText) findViewById(R.id.editTextShortcutName);
        spinnerInstalledProviders = (Spinner) findViewById(R.id.spinnerInstalledProviders);
        switchIcon = (SwitchCompat) findViewById(R.id.switchIcon);
        imgLogo = (AnimatedImageView) findViewById(R.id.imgLogo);
    }

    /**
     * This method sets the needed listeners for the only objects that need to react on user's input
     */
    private void setViewListeners() {
        switchIcon.setOnCheckedChangeListener(this);
        spinnerInstalledProviders.setOnItemSelectedListener(this);
        fabCreateShortcut.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(updateReceiver, updateFilter);
        initServiceCommunication();
    }

    /**
     * This method starts the {@link android.app.Service} for retrieving the list of installed
     * packages in the system
     */
    private void initServiceCommunication() {
        Intent serviceIntent = IntentUtils.getStartInstalledProvidersService(this);
        startService(serviceIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReceiver);
    }

    /**
     * As per MVP paradigm, when a UI event is triggered, the View lets the Presenter take care
     * of it
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        whatSongPresenter.onItemSelected(position, switchIcon.isChecked());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        whatSongPresenter.onSwitchSelected(b);
    }

    /**
     * @see #calculateFabVerticalPosition()
     */
    @Override
    public void onGlobalLayout() {
        fabCreateShortcut.setY(calculateFabVerticalPosition());
    }

    /**
     * <b>Before</b> effectively drawing the layout, the {@link android.view.ViewTreeObserver} will
     * place the {@code Floating Action Button} at the end of the {@link android.support.v7.widget.Toolbar}
     * so that the end of the {@code Toolbar} is exactly at half of the {@code Floating Action Button}'s
     * background circle.
     */
    private float calculateFabVerticalPosition() {
        float toolbarHeight = (float) toolbar.getHeight();
        float fabHeight = (float) fabCreateShortcut.getHeight();

        return toolbarHeight - fabHeight/2;
    }

    /**
     * As per MVP paradigm, when the application requires the UI to update, the presenter will invoke
     * the UI, so that the new data is displayed
     */
    @Override
    public void onChangeBigIcon(int resourceId) {
        imgLogo.setImageResource(resourceId);
    }

    @Override
    public void onUpdateListAdapter(BaseAdapter adapter) {
        spinnerInstalledProviders.setAdapter(adapter);
    }

    @Override
    public void onUpdateShortcutName(String newShortcutName) {
        editTextShortcutName.setText(newShortcutName);
    }

    @Override
    public void onShortcutIntentCreated(Intent shortcutIntent) {
        setResult(RESULT_OK, shortcutIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        whatSongPresenter.onFabClick(editTextShortcutName.getText().toString(),
                switchIcon.isChecked());
    }

    /**
     * This class is the receiver that will take care of listening the response from the
     * {@link it.tiwiz.whatsong.SearchInstalledProvidersService}.
     *
     * The expected behaviour is, as soon as the {@link android.content.Intent} is received,
     * the {@link android.os.Parcelable} array is extracted and the presenter's method
     * {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongPresenter#onPackagesRetrieved(it.tiwiz.whatsong.utils.PackageData[])}
     * is invoked.
     */
    private class ServiceUpdatesReceiver extends BroadcastReceiver {

        /**
         * When the {@link android.content.Intent} is received, the
         * {@link it.tiwiz.whatsong.utils.PackageData} array is extracted and the Presenter gets
         * invoked.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            PackageData[] response = extractResultsFrom(intent);
            whatSongPresenter.onPackagesRetrieved(response);
        }

        /**
         * This method checks if the {@link android.content.Intent} contains the correct key and,
         * if the key is effectively inserted, the data is extracted and then returned to the
         * caller
         */
        private PackageData[] extractResultsFrom(Intent intent) {
            PackageData[] response =  new PackageData[0];

            if (intent.hasExtra(SearchInstalledProvidersService.SEARCH_PROVIDERS_KEY)) {
                response = (PackageData[]) intent.getParcelableArrayExtra(
                        SearchInstalledProvidersService.SEARCH_PROVIDERS_KEY);
            }

            return response;
        }
    }

}