package it.tiwiz.whatsong;

import android.content.Intent;

/**
 * Created by Roby on 03/01/14.
 */
public class EmptyIntent implements ComplexIntent{
    @Override
    public Intent getInstance() {
        return null;
    }
}
