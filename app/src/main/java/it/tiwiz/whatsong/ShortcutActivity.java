package it.tiwiz.whatsong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;

import it.tiwiz.whatsong.utils.IconUtils;
import it.tiwiz.whatsong.utils.IndexUtils;
import it.tiwiz.whatsong.utils.PackageData;
import it.tiwiz.whatsong.utils.ShortcutUtils;
import it.tiwiz.whatsong.views.AnimatedImageView;

/**
 * Created by Roby on 04/01/14.
 */
public class ShortcutActivity extends Activity implements AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener, ViewTreeObserver.OnGlobalLayoutListener {

    private EditText editTextShortcutName;
    private Spinner spinnerInstalledProviders;
    private SwitchCompat switchIcon;
    private AnimatedImageView imgLogo;
    private Toolbar toolbar;
    private ImageButton fabCreateShortcut;
    private String[] installedProvidersPackages = null;
    private String[] installedProvidersNames = null;
    private int position = 0;
    private Context mContext = null;

    //fabMake::id
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shortcut_activity);

        View content = findViewById(R.id.contentParent);
        content.getViewTreeObserver().addOnGlobalLayoutListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fabCreateShortcut = (ImageButton) findViewById(R.id.fabMake);
        editTextShortcutName = (EditText) findViewById(R.id.editTextShortcutName);

        spinnerInstalledProviders = (Spinner) findViewById(R.id.spinnerInstalledProviders);
        //spinnerInstalledProviders.setEnabled(false);
        spinnerInstalledProviders.setOnItemSelectedListener(this);
        switchIcon = (SwitchCompat) findViewById(R.id.switchIcon);
        switchIcon.setOnCheckedChangeListener(this);

        imgLogo = (AnimatedImageView) findViewById(R.id.imgLogo);

        SpinnerFiller spinnerFiller = new SpinnerFiller();
        SearchInstalledProviders searchInstalledProviders = new SearchInstalledProviders(getApplicationContext(), spinnerFiller, true);
        searchInstalledProviders.execute();

        mContext = this;


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        this.position = position;

        editTextShortcutName.setText(installedProvidersNames[position]);
        changeBigIcon(switchIcon.isChecked());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void createShortcut() {

        Intent intent = ShortcutUtils.createShortcutIntent(this, installedProvidersPackages,
                position, editTextShortcutName.getText().toString(), switchIcon.isChecked());

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        changeBigIcon(b);

    }

    private void changeBigIcon(boolean provider) {

        final int icon;

        if (provider) {
            final String packageName = installedProvidersPackages[position];
            final int realPosition = IndexUtils.getRealPositionFrom(packageName);
            icon = IconUtils.getMusicAppBigIconResourceID(realPosition);
        } else
            icon = R.drawable.ic_launcher_vectorized;

        imgLogo.setImageResource(icon);
    }

    @Override
    public void onGlobalLayout() {

        float toolbarHeight = (float) toolbar.getHeight();
        float fabHeight = (float) fabCreateShortcut.getHeight();

        float newPosition = toolbarHeight - fabHeight/2;

        fabCreateShortcut.setY(newPosition);
    }

    private class SpinnerFiller implements SearchInstalledProviders.Callback {
        @Override
        public void action(PackageData[] result) {

            installedProvidersPackages = PackageData.Utils.getPackages(result);
            installedProvidersNames = PackageData.Utils.getNames(result);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, installedProvidersNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInstalledProviders.setAdapter(adapter);
            //spinnerInstalledProviders.setEnabled(true);

            editTextShortcutName.setText(installedProvidersNames[0]);
        }
    }

}