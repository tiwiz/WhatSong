package it.tiwiz.whatsong;

import android.content.Intent;

/**
 * Created by Roby on 03/01/14.
 */
public class Shazam implements ComplexIntent{

    private final static String SHAZAM_INTENT = "com.shazam.android.intent.actions.START_TAGGING";

    @Override
    public Intent getInstance() {
        return new Intent(SHAZAM_INTENT);
    }
}
