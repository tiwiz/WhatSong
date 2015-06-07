package it.tiwiz.whatsong.utils;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.util.AttributeSet;

import com.google.android.gms.appinvite.AppInviteInvitation;

import it.tiwiz.whatsong.R;

/**
 * This preference will make the users invite their friends to use WhatSong
 */
public class InvitationPreference extends Preference{

    private final Intent invitationIntent;

    public InvitationPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpUiData();
        invitationIntent = createInvitationIntent();
    }

    public InvitationPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InvitationPreference(Context context) {
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
                .build();
    }

    @Override
    protected void onClick() {
        super.onClick();

        getContext().startActivity(invitationIntent);
    }
}
