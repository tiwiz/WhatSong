package it.tiwiz.whatsong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import it.tiwiz.whatsong.mvp.ShortcutPresenter;
import it.tiwiz.whatsong.mvp.interfaces.WhatSongPresenter;
import it.tiwiz.whatsong.mvp.interfaces.WhatSongView;
import it.tiwiz.whatsong.utils.BaseActivity;
import it.tiwiz.whatsong.views.AnimatedImageView;

/**
 * This {@link android.app.Activity} implements the {@link it.tiwiz.whatsong.mvp.interfaces.WhatSongView}
 * {@code interface}. Based on the idea of MVP, every user input is redirected to the <b>presenter</b>,
 * while the presenter itself will invoke this <b>view</b> when a UI update is needed.
 * <br/><br/>
 * <b>MVP paradigm:</b><br/>
 * {@code View} <===> {@code Presenter} <===> {@code Model}
 * <br/><br/>
 *
 * @see it.tiwiz.whatsong.mvp.interfaces.WhatSongView View interface
 * @see it.tiwiz.whatsong.mvp.interfaces.WhatSongPresenter Presenter interface
 * @see it.tiwiz.whatsong.mvp.interfaces.WhatSongModel Model interface
 * @see it.tiwiz.whatsong.mvp.ShortcutPresenter Presenter implementation
 * @see it.tiwiz.whatsong.mvp.ShortcutModel Model implementation
 */
public class ShortcutActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener, View.OnClickListener, WhatSongView {

    private EditText editTextShortcutName;
    private Spinner spinnerInstalledProviders;
    private SwitchCompat switchIcon;
    private AnimatedImageView imgLogo;
    private Toolbar toolbar;
    private ImageButton fabCreateShortcut;

    private WhatSongPresenter whatSongPresenter;

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

    @Override
    public String getActivityTag() {
        return ShortcutActivity.class.getSimpleName();
    }

    /**
     * This method is the main hub for all the initializations methods and it's invoked during the
     * {@link #onCreate(android.os.Bundle)} of the Activity
     */
    private void initActivity() {
        findViews();
        setViewListeners();
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
    protected void onResume() {
        super.onResume();
        whatSongPresenter.onInstalledPackagesRequested(this);
    }

    /**
     * As per MVP paradigm, when a UI event is triggered, the View lets the Presenter take care
     * of it
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        whatSongPresenter.onProviderSelected(position, switchIcon.isChecked());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        whatSongPresenter.onProviderIconChanged(b);
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
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        whatSongPresenter.onShortcutRequest(editTextShortcutName.getText().toString(),
                switchIcon.isChecked());
    }

}