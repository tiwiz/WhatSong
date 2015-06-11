package it.tiwiz.whatsong.intents;

import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * This is the default implementation of the {@link MusicAppIntent} Interface
 */
public class EmptyIntent implements MusicAppIntent {
    @Override
    @Nullable
    public Intent getInstance() {
        return null;
    }
}
