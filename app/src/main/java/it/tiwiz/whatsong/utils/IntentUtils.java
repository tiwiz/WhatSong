package it.tiwiz.whatsong.utils;

import android.content.Context;
import android.content.Intent;

import it.tiwiz.whatsong.SearchInstalledProvidersService;
import it.tiwiz.whatsong.intents.MusicAppIntent;
import it.tiwiz.whatsong.intents.EmptyIntent;
import it.tiwiz.whatsong.intents.MusiXmatch;
import it.tiwiz.whatsong.intents.Shazam;
import it.tiwiz.whatsong.intents.SoundHound;
import it.tiwiz.whatsong.intents.SoundSearch;
import it.tiwiz.whatsong.intents.SoundTracking;
import it.tiwiz.whatsong.intents.TrackID;

/**
 * This class contains all the methods to related to the Intent
 * creation and management
 */
public class IntentUtils {

    /**
     * Creates the {@link android.content.Intent} needed to launch the chosen
     * music recognition app.
     * @return {@link android.content.Intent} ready to be fired.
     */
    public static Intent getLaunchIntent(int index, String packageName) {

        MusicAppIntent intent;

        switch(index){
            case 0: //Sound Search
                intent = new SoundSearch();
                break;
            case 1: //Shazam
            case 2: //Shazam Encore
                intent = new Shazam();
                break;
            case 3: //SoundHound
            case 4: //SoundHound Pro
                intent = new SoundHound(packageName);
                break;
            case 5: //TrackID
                intent = new TrackID(packageName);
                break;
            case 6: //musiXmatch
                intent = new MusiXmatch(packageName);
                break;
            case 7: //SoundTracking
                intent = new SoundTracking(packageName);
                break;
            default:
                intent = new EmptyIntent();
                break;
        }

        return intent.getInstance();
    }

    /**
     * This method will build the {@link android.content.Intent} needed to request a search of all the music recognition
     * apps installed in the current system
     */
    public static Intent getStartInstalledProvidersService(final Context context) {
        Intent serviceIntent = new Intent(context, SearchInstalledProvidersService.class);
        serviceIntent.setAction(SearchInstalledProvidersService.SEARCH_INSTALLED_PROVIDERS);
        return serviceIntent;
    }

    /**
     * This method will create the {@link android.content.Intent} needed for respond to a request for
     * installed (or not installed) music recognition providers.
     *
     * <b>Note</b>: this {@link android.content.Intent} will be used together with a
     * {@link android.support.v4.content.LocalBroadcastManager}
     */
    public static Intent getSendInstalledProvidersResponse(final PackageData[] data) {
        Intent responseIntent = new Intent(SearchInstalledProvidersService.SEARCH_PROVIDERS_RESPONSE);
        responseIntent.putExtra(SearchInstalledProvidersService.SEARCH_PROVIDERS_KEY, data);
        return responseIntent;
    }
}
