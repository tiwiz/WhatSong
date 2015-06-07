package it.tiwiz.whatsong.utils;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appinvite.AppInviteInvitation;

import java.util.Map;

import it.tiwiz.whatsong.R;
import it.tiwiz.whatsong.WhatSongApp;

/**
 * This preference will make the users invite their friends to use WhatSong
 */
public class InvitationPreference extends Preference{

    private final Intent invitationIntent;
    private final Tracker trackerInstance;
    private final Map<String, String> eventData;

    public InvitationPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpUiData();
        invitationIntent = createInvitationIntent();
        trackerInstance = getTrackerInstance();
        eventData = getEventData();
    }

    public InvitationPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InvitationPreference(Context context) {
        super(context);
        setUpUiData();
        invitationIntent = createInvitationIntent();
        trackerInstance = getTrackerInstance();
        eventData = getEventData();
    }

    private void setUpUiData() {
        setSummary(R.string.invite_friends_summary);
    }

    private Tracker getTrackerInstance() {
        return ((WhatSongApp) WhatSongApp.getInstance()).getDefaultTracker();
    }

    private Map<String, String> getEventData() {
        return new HitBuilders.EventBuilder()
                .setCategory("AppInvitation")
                .setAction("Send")
                .build();
    }

    private Intent createInvitationIntent() {
        final String invitationTitle = getContext().getString(R.string.invite_invitation_title);
        final String invitationMessage = getContext().getString(R.string.invite_invitation_message);

        return new AppInviteInvitation.IntentBuilder(invitationTitle)
                .setMessage(invitationMessage)
                .build();
    }

    @Override
    protected void onClick() {
        super.onClick();

        getContext().startActivity(invitationIntent);
        trackerInstance.send(eventData);
    }
}
