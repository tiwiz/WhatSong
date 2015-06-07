package it.tiwiz.whatsong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import it.tiwiz.whatsong.R;
import it.tiwiz.whatsong.utils.BaseActivity;
import it.tiwiz.whatsong.utils.GoogleNowUtils;

public class GoogleNowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent launchIntent = GoogleNowUtils.getIntentForChosenProvider();
        startActivity(launchIntent);

        finish();
    }

    @Override
    public String getActivityTag() {
        return GoogleNowActivity.class.getSimpleName();
    }

}
