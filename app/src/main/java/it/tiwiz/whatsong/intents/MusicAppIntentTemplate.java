package it.tiwiz.whatsong.intents;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 *
 */
public abstract class MusicAppIntentTemplate implements MusicAppIntent{

    private final Intent configuredIntent;

    public MusicAppIntentTemplate(@NonNull String packagName) {
        configuredIntent = configureIntent(packagName);
    }

    @NonNull
    abstract Intent configureIntent(@NonNull String packageName);

    @Override
    @NonNull
    public Intent getInstance() {
        return configuredIntent;
    }
}
