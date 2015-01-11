package it.tiwiz.whatsong.utils;



import android.content.Context;
import android.content.Intent;
import android.widget.BaseAdapter;

import it.tiwiz.whatsong.WhatSongApp;
import it.tiwiz.whatsong.mvp.interfaces.WhatSongView;


public class MockedView implements WhatSongView{

    private boolean isOnChangeBigIconBeingCalled;
    private boolean isOnUpdateListAdapterBeingCalled;
    private boolean isOnUpdateShortcutNameBeingCalled;
    private boolean isOnShortcutIntentCreatedBeingCalled;
    private int iconResourceId;
    private BaseAdapter internalAdapter;
    private String shortcutName;
    private Intent internalShortcutIntent;

    public MockedView() {

        isOnChangeBigIconBeingCalled = false;
        isOnUpdateListAdapterBeingCalled = false;
        isOnUpdateShortcutNameBeingCalled = false;
        isOnShortcutIntentCreatedBeingCalled = false;

        iconResourceId = -1;
        internalAdapter = null;
        shortcutName = null;
    }

    @Override
    public void onChangeBigIcon(int resourceId) {
        isOnChangeBigIconBeingCalled = true;
        iconResourceId = resourceId;
    }

    @Override
    public void onUpdateListAdapter(BaseAdapter adapter) {
        isOnUpdateListAdapterBeingCalled = true;
        internalAdapter = adapter;
    }

    @Override
    public void onUpdateShortcutName(String newShortcutName) {
        isOnUpdateShortcutNameBeingCalled = true;
        shortcutName = newShortcutName;
    }

    @Override
    public void onShortcutIntentCreated(Intent shortcutIntent) {
        isOnShortcutIntentCreatedBeingCalled = true;
        internalShortcutIntent = shortcutIntent;
    }

    @Override
    public Context getViewContext() {
        return WhatSongApp.getInstance();
    }

    public boolean hasOnChangeBigIconBeenCalled() {
        return isOnChangeBigIconBeingCalled;
    }

    public boolean hasOnUpdateListAdapterBeenCalled() {
        return isOnUpdateListAdapterBeingCalled;
    }

    public boolean hasOnUpdateShortcutNameBeenCalled() {
        return isOnUpdateShortcutNameBeingCalled;
    }

    public boolean hasOnShortcutIntentCreatedBeenCalled() {
        return isOnShortcutIntentCreatedBeingCalled;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public BaseAdapter getInternalAdapter() {
        return internalAdapter;
    }

    public String getShortcutName() {
        return shortcutName;
    }

    public Intent getInternalShortcutIntent() {
        return internalShortcutIntent;
    }
}
