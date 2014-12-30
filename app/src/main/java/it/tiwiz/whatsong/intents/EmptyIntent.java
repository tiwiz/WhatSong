package it.tiwiz.whatsong.intents;

import android.content.Intent;

/**
 * This is the default implementation of the {@link MusicAppIntent} Interface
 */
public class EmptyIntent implements MusicAppIntent {
    @Override
    public Intent getInstance() {
        return null;
    }
}
