package it.tiwiz.whatsong.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

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
     * This method will build the {@link android.content.Intent} needed to request a search of all the music recognition
     * apps installed in the current system
     *
     * @see it.tiwiz.whatsong.utils.IntentUtils.Filters#getSendInstalledProdiverResponseFilter()
     * Filter needed for handling the response
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
     *
     * @see it.tiwiz.whatsong.utils.IntentUtils.Filters#getSendInstalledProdiverResponseFilter()
     * Filter needed for handling the response
     */
    public static Intent getSendInstalledProvidersResponse(final PackageData[] data) {
        Intent responseIntent = new Intent(SearchInstalledProvidersService.SEARCH_PROVIDERS_RESPONSE);
        responseIntent.putExtra(SearchInstalledProvidersService.SEARCH_PROVIDERS_KEY, data);
        return responseIntent;
    }

    /**
     * This subclass represents the {@link android.content.IntentFilter} needed for creating the
     * {@link android.content.BroadcastReceiver} used to retrieve the responses from the requests
     */
    public static class Filters {

        /**
         * This method creates a {@link android.content.IntentFilter} used when waiting for the
         * response from the query of the Search Installed Providers request.
         *
         * @see #getStartInstalledProvidersService(android.content.Context) Make the request
         * @see #getSendInstalledProvidersResponse(PackageData[]) Create the response
         */
        public static IntentFilter getSendInstalledProdiverResponseFilter() {
            IntentFilter responseFilter = new IntentFilter(SearchInstalledProvidersService.SEARCH_PROVIDERS_RESPONSE);
            return responseFilter;
        }
    }
}
