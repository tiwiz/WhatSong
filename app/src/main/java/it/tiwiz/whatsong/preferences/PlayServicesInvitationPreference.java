package it.tiwiz.whatsong.preferences;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.util.AttributeSet;

import com.google.android.gms.appinvite.AppInviteInvitation;

import it.tiwiz.whatsong.BuildConfig;
import it.tiwiz.whatsong.R;

/**
 * This preference will make the users invite their friends to use WhatSong through Google Play Services
 * new feature
 */
public class PlayServicesInvitationPreference extends Preference{

    private final Intent invitationIntent;

    public PlayServicesInvitationPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpUiData();
        invitationIntent = createInvitationIntent();
    }

    public PlayServicesInvitationPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayServicesInvitationPreference(Context context) {
        super(context);
        setUpUiData();
        invitationIntent = createInvitationIntent();
    }

    private void setUpUiData() {
        setSummary(R.string.invite_friends_summary);
    }

    private Intent createInvitationIntent() {
        final String invitationTitle = getContext().getString(R.string.invite_invitation_title);
        final String invitationMessage = getContext().getString(R.string.invite_invitation_message);

        return new AppInviteInvitation.IntentBuilder(invitationTitle)
                .setMessage(invitationMessage)
                .setGoogleAnalyticsTrackingId(BuildConfig.ANALYTICS_USERID)
                .build();
    }

    @Override
    protected void onClick() {
        super.onClick();

        getContext().startActivity(invitationIntent);
    }
}
