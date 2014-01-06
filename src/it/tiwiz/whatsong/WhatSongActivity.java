package it.tiwiz.whatsong;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Roby on 06/01/14.
 */
public class WhatSongActivity extends Activity {

    private FragmentManager fragmentManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whatsong_layout);

        fragmentManager = getFragmentManager();
        loadReportFragment(); //loads main fragment
    }

    private void loadReportFragment(){

        Fragment fragment = new ReportFragment();
        changeFragment(fragment,0);

    }

    private void changeFragment(Fragment fragment, int position){

        invalidateOptionsMenu();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content,fragment);

        /*Allows any window to bring back report */
        if(position > 0) transaction.addToBackStack(null);

        transaction.setCustomAnimations(android.R.anim.fade_in, //Fragment enters
                android.R.anim.fade_out, //Fragment exits
                android.R.anim.fade_in, //Fragment enters from back pressed
                android.R.anim.fade_out); //Fragment exits from back pressed

        transaction.commit();



        //TODO Close Drawer here
    }
}