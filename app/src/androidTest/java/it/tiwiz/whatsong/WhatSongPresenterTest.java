package it.tiwiz.whatsong;

import android.content.Intent;

import junit.framework.TestCase;

import it.tiwiz.whatsong.mvp.ShortcutPresenter;
import it.tiwiz.whatsong.mvp.interfaces.WhatSongPresenter;
import it.tiwiz.whatsong.utils.MockedView;
import it.tiwiz.whatsong.utils.PackageDataMockedApps;

public class WhatSongPresenterTest extends TestCase{

    private MockedView mockedView;
    private WhatSongPresenter realPresenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockedView = new MockedView();
        realPresenter = new ShortcutPresenter(mockedView);
    }

    public void testOnProviderSelected() throws Exception {

        realPresenter.onPackagesRetrieved(PackageDataMockedApps.TEST_APPS);
        realPresenter.onProviderSelected(0, true);
        assertEquals(mockedView.hasOnUpdateShortcutNameBeenCalled(), true);
        assertEquals(mockedView.hasOnChangeBigIconBeenCalled(), true);

        String shortcutNameFromPresenter = mockedView.getShortcutName();
        assertEquals(shortcutNameFromPresenter, PackageDataMockedApps.TEST_APPS_NAMES[0]);


        realPresenter.onProviderSelected(0, false);

        shortcutNameFromPresenter = mockedView.getShortcutName();
        assertEquals(shortcutNameFromPresenter, PackageDataMockedApps.TEST_APPS_NAMES[0]);
    }

    public void testOnChangeBigIcon() throws Exception {

        realPresenter.onPackagesRetrieved(PackageDataMockedApps.TEST_APPS);

        realPresenter.onProviderIconChanged(true);
        assertEquals(mockedView.hasOnChangeBigIconBeenCalled(), true);
        int resourceId = mockedView.getIconResourceId();
        assertNotSame(resourceId, R.drawable.ic_launcher_vectorized);

        realPresenter.onProviderIconChanged(false);
        resourceId = mockedView.getIconResourceId();
        assertEquals(resourceId, R.drawable.ic_launcher_vectorized);
    }

    public void testOnShortcutRequested() throws Exception {
        realPresenter.onShortcutRequest(PackageDataMockedApps.TEST_APPS_PACKAGES[0], true);
        assertEquals(mockedView.hasOnShortcutIntentCreatedBeenCalled(), true);

        Intent requestedIntent = mockedView.getInternalShortcutIntent();
        assertNotNull(requestedIntent);
    }
}
