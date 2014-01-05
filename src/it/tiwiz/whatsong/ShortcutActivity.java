package it.tiwiz.whatsong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

/**
 * Created by Roby on 04/01/14.
 */
public class ShortcutActivity extends Activity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener{

    private EditText editTextShortcutName;
    private Spinner spinnerInstalledProviders;
    private Switch switchIcon;
    private ImageView imgLogo;
    private String[] installedProvidersPackages = null;
    private String[] installedProvidersNames = null;
    private int position = 0;
    private int currentIcon = 0;
    private Context mContext = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shortcut_activity);

        editTextShortcutName = (EditText) findViewById(R.id.editTextShortcutName);

        spinnerInstalledProviders = (Spinner) findViewById(R.id.spinnerInstalledProviders);
        //spinnerInstalledProviders.setEnabled(false);
        spinnerInstalledProviders.setOnItemSelectedListener(this);
        switchIcon = (Switch) findViewById(R.id.switchIcon);
        switchIcon.setOnCheckedChangeListener(this);

        imgLogo = (ImageView) findViewById(R.id.imgLogo);

        SpinnerFiller spinnerFiller = new SpinnerFiller();
        SearchInstalledProviders searchInstalledProviders = new SearchInstalledProviders(getApplicationContext(),spinnerFiller, true);
        searchInstalledProviders.execute();

        mContext = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shortcut_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.shortcut_accept:
                createShortcut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void createShortcut(){

        final Manager manager = Manager.getInstance(this);

        final String selectedPackage = installedProvidersPackages[position];

        int resID;

        final String packageName = installedProvidersPackages[position];
        final int realPosition = manager.getRealPosition(packageName);

        if(switchIcon.isChecked())
            resID = manager.getIcon(realPosition);
        else
            resID = R.drawable.ic_launcher;

        final Intent launchIntent = manager.getIntent(realPosition,selectedPackage);

        final Intent.ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(this, resID);
        final Intent intent = new Intent();

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, editTextShortcutName.getText().toString());

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        changeBigIcon(b);

    }

    private void changeBigIcon(boolean provider){

        final int icon;

        if(provider){
            final String packageName = installedProvidersPackages[position];
            Manager manager = Manager.getInstance(mContext);
            final int realPosition = manager.getRealPosition(packageName);
            icon = manager.getBigIcon(realPosition);
        }else
            icon = R.drawable.ic_launcher_vectorized;

        if(currentIcon != icon){
            //creates animation
            final Animation animation_out = AnimationUtils.loadAnimation(mContext,R.anim.shrink);
            final Animation animation_in = AnimationUtils.loadAnimation(mContext,R.anim.enlarge);

            animation_out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {

                    animation_in.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            imgLogo.setImageResource(icon);
                            currentIcon = icon;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {}

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    imgLogo.startAnimation(animation_in);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            imgLogo.startAnimation(animation_out);
        }
    }

    private class SpinnerFiller implements SearchInstalledProviders.Callback{
        @Override
        public void action(SimplePackage[] result) {

            installedProvidersPackages = SimplePackage.getPackages(result);
            installedProvidersNames = SimplePackage.getNames(result);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, installedProvidersNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInstalledProviders.setAdapter(adapter);
            //spinnerInstalledProviders.setEnabled(true);

            editTextShortcutName.setText(installedProvidersNames[0]);
        }
    }

}