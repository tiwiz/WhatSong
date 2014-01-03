package it.tiwiz.whatsong;

import android.content.Intent;

/**
 * Created by Roby on 03/01/14.
 */
public class MusiXmatch implements ComplexIntent{

    private static final String MUSIXMATCH_TAG_NOW = "com.designfuture.music.ui.phone.SearchLyricActivity";
    private String packageName = "";

    public MusiXmatch(String packageName){
        this.packageName = packageName;
    }
    @Override
    public Intent getInstance() {
        Intent musiXmatchIntent = new Intent();
        musiXmatchIntent.setClassName(packageName,MUSIXMATCH_TAG_NOW);
        musiXmatchIntent.putExtra("AUTO_START",true);
        musiXmatchIntent.putExtra("com.musixmatch.android.lyrify.ui.fragment.autostart", true);
        return musiXmatchIntent;
    }
}
