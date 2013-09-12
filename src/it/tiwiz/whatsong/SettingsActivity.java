package it.tiwiz.whatsong;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: Roby
 * Date: 12/09/13
 */
public class SettingsActivity extends PreferenceActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setIcon(R.drawable.icon);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
