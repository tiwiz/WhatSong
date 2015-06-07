package it.tiwiz.whatsong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.api.GoogleApiClient;

public class AppInvitationReceiver extends BroadcastReceiver implements GoogleApiClient.ConnectionCallbacks {

    private String appInvitationId = "";
    private GoogleApiClient googleApiClient;

    public AppInvitationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        appInvitationId = AppInviteReferral.getInvitationId(intent);

        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(AppInvite.API)
                .addConnectionCallbacks(this)
                .useDefaultAccount()
                .build();

        googleApiClient.connect();

        //TODO implement "Thanks dialog"
    }

    @Override
    public void onConnected(Bundle bundle) {
        AppInvite.AppInviteApi.updateInvitationOnInstall(googleApiClient, appInvitationId);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
