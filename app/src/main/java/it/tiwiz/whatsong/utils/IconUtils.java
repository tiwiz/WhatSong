package it.tiwiz.whatsong.utils;

import it.tiwiz.whatsong.R;

/**
 * This class contains all the methods needed to retrieve icons
 */
public class IconUtils {

    public static int getMusicAppIconResourceID(int index) {

        int resId;

        switch (index) {
            case 0: //Sound Search
                resId = R.drawable.google;
                break;
            case 1: //Shazam
            case 2:
                resId = R.drawable.shazam;
                break;
            case 3: //SoundHound
            case 4:
                resId = R.drawable.soundhound;
                break;
            case 5: //TrackID
                resId = R.drawable.trackid;
                break;
            case 6: //musiXmatch
                resId = R.drawable.musixmatch;
                break;
            case 7: //SoundTracking
                resId = R.drawable.soundtracking;
                break;
            default:
                resId = R.drawable.ic_launcher;
                break;
        }

        return resId;
    }

    public static int getMusicAppBigIconResourceID(int index) {
        int resId;

        switch (index) {
            case 0: //Sound Search
                resId = R.drawable.google_vectorized;
                break;
            case 1: //Shazam
            case 2:
                resId = R.drawable.shazam_vectorized;
                break;
            case 3: //SoundHound
            case 4:
                resId = R.drawable.soundhound_vectorized;
                break;
            case 5: //TrackID
                resId = R.drawable.trackid_vectorized;
                break;
            case 6: //musiXmatch
                resId = R.drawable.musixmatch_vectorized;
                break;
            case 7: //SoundTracking
                resId = R.drawable.soundtracking_vectorized;
                break;
            default:
                resId = R.drawable.ic_launcher_vectorized;
                break;
        }

        return resId;
    }
}
