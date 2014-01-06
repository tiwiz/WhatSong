package it.tiwiz.whatsong;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.kristijandraca.backgroundmaillibrary.BackgroundMail;
import it.tiwiz.secrets.GRS;

/**
 * Created by Roby on 06/01/14.
 */
public class ReportFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private LinearLayout layoutCustomURL, layoutComment;
    private Spinner softwarePickerSpinner;
    private EditText editCustomPlayStoreURL, editCustomComment;
    private String[] spinnerReportVoices;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.report_fragment_layout, container, false);
        connectLogicToUI(rootView);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {

        menu.clear();
        inflater.inflate(R.menu.send_report_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.sendReport:
                sendReport();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }


    private final void sendReport(){

        String issuedPackage = getPackage();

        if(!issuedPackage.equalsIgnoreCase("")){
            final String body = buildReport(issuedPackage);
            BackgroundMail backgroundMail = new BackgroundMail(mContext);
            backgroundMail.setGmailUserName(GRS.ADDRESS);
            backgroundMail.setGmailPassword(GRS.PASSWORD);
            backgroundMail.setMailTo(GRS.ADDRESS);
            backgroundMail.setFormSubject(mContext.getString(R.string.report_object));
            backgroundMail.setFormBody(body);
            backgroundMail.setSendingMessage(mContext.getString(R.string.report_sending_message));
            backgroundMail.setSendingMessageSuccess(mContext.getString(R.string.report_sending_message_success));
            backgroundMail.setProcessVisibility(true);
            backgroundMail.send();
        }
    }

    private final String getPackage(){

        if(layoutCustomURL.getVisibility() != View.GONE){
            //checks if user wrote something
            String customURLcontent = editCustomPlayStoreURL.getText().toString();

            //draws custom icon error in EditText
            final Drawable standardErrorIcon = mContext.getResources().getDrawable(android.R.drawable.stat_notify_error);
            final Drawable customErrorIcon = mContext.getResources().getDrawable(R.drawable.ic_error);
            final Rect bounds = new Rect(0,0,standardErrorIcon.getIntrinsicWidth(),standardErrorIcon.getIntrinsicHeight());
            customErrorIcon.setBounds(bounds);

            //gets Google Play address format
            final String GooglePlayHeader = mContext.getResources().getString(R.string.play_store_address_header);
            if(null == customURLcontent || customURLcontent.length() == 0){
                //no string has been inserted
                editCustomPlayStoreURL.requestFocus();
                editCustomPlayStoreURL.setError(mContext.getString(R.string.send_report_empty_string_error),customErrorIcon);
                return "";
            }else if(!customURLcontent.contains(GooglePlayHeader)){
                //a bad address has been inserted
                editCustomPlayStoreURL.requestFocus();
                editCustomPlayStoreURL.setError(mContext.getString(R.string.send_report_bad_address_error),customErrorIcon);
                return "";
            }else
                editCustomPlayStoreURL.setError(null);
                return customURLcontent;
        }else{
            return softwarePickerSpinner.getSelectedItem().toString();
        }
    }

    private final String buildReport(String issuedPackage){

        String versionName ="";
        int versionCode = 0;

        try {
            versionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "N/A";
        }

        final StringBuilder report = new StringBuilder();
        report.append("Device: " + Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL + "\n");
        report.append("Android Version: " + Build.VERSION.RELEASE + "\n");
        report.append("WhatSong version: " + versionName + " (" + versionCode + ")\n");
        report.append("Issued package: " + issuedPackage + "\n");

        String comment = editCustomComment.getText().toString();

        if(null != comment || comment.length() > 0){
            report.append("*******************\n");
            report.append("User\'s comment:\n");
            report.append(comment);
        }

        return report.toString();
    }

    private final void connectLogicToUI(View rootView){

        softwarePickerSpinner = (Spinner) rootView.findViewById(R.id.spinnerReportSoftware);
        softwarePickerSpinner.setOnItemSelectedListener(this);

        layoutCustomURL = (LinearLayout) rootView.findViewById(R.id.layoutInsertUrl);
        editCustomPlayStoreURL = (EditText) rootView.findViewById(R.id.editReportInsertURL);

        layoutComment = (LinearLayout) rootView.findViewById(R.id.layoutInsertComment);
        editCustomComment = (EditText) rootView.findViewById(R.id.editReportComment);

        SpinnerCallback callback = new SpinnerCallback();
        SearchInstalledProviders filler = new SearchInstalledProviders(getActivity(),callback,true);
        filler.execute();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        final int lastVoiceIndex = spinnerReportVoices.length - 1;
        final Animation fade_in_animation = AnimationUtils.loadAnimation(mContext, R.anim.enlarge);
        final Animation fade_out_animation = AnimationUtils.loadAnimation(mContext,R.anim.shrink);
        final Animation slide_up_out_animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_up_out);
        final Animation slide_down_in_animation = AnimationUtils.loadAnimation(mContext,R.anim.slide_down_in);

        if(position == lastVoiceIndex){
            //"other..." has been selected
            if(layoutCustomURL.getVisibility() != View.VISIBLE){

                slide_down_in_animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        layoutCustomURL.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        layoutComment.startAnimation(fade_in_animation);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                fade_out_animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        layoutCustomURL.startAnimation(slide_down_in_animation);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {}
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                layoutComment.startAnimation(fade_out_animation);
            }
        }else{
            //a software as been selected
            if(layoutCustomURL.getVisibility() != View.GONE){

                fade_in_animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        layoutComment.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {}
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                slide_up_out_animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        layoutCustomURL.setVisibility(View.GONE);
                        layoutComment.startAnimation(fade_in_animation);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                fade_out_animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        layoutCustomURL.startAnimation(slide_up_out_animation);
                        layoutComment.startAnimation(fade_out_animation);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        layoutComment.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                layoutCustomURL.startAnimation(fade_out_animation);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class SpinnerCallback implements SearchInstalledProviders.Callback{
        @Override
        public void action(SimplePackage[] result) {
            final int resultSize = result.length;
            final int adapterSize = resultSize + 1;

            spinnerReportVoices = new String[adapterSize];
            for(int i = 0; i < resultSize; i++)
                spinnerReportVoices[i] = result[i].getPackageLabel();
            spinnerReportVoices[resultSize] = mContext.getString(R.string.report_other_software_element);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,spinnerReportVoices);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            softwarePickerSpinner.setAdapter(adapter);

        }
    }
}