package it.tiwiz.whatsong.intents;

import android.content.Intent;

public class IntentFactory {
    /**
     * Creates the {@link android.content.Intent} needed to launch the chosen
     * music recognition app.
     * @return {@link android.content.Intent} ready to be fired.
     */
    public static Intent getLaunchIntentFor(int index, String packageName) {

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
}
