package it.tiwiz.whatsong;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import java.util.ArrayList;

import it.tiwiz.whatsong.utils.PackageData;

/**
 * Created by Roby on 04/01/14.
 */
public class SearchInstalledProviders extends AsyncTask<Void,Void,PackageData[]> {

    private Context mContext;
    private Callback mCallback;
    private boolean showInstalled;
    private String[] allProviders;
    private String[] allProvidersNames;

    public SearchInstalledProviders(Context mContext, Callback mCallback, boolean showInstalled){

        this.mContext = mContext;
        this.mCallback = mCallback;
        this.showInstalled = showInstalled;

    }

    @Override
    protected void onPreExecute(){

        allProviders = mContext.getResources().getStringArray(R.array.softwares_packages);
        allProvidersNames = mContext.getResources().getStringArray(R.array.softwares_names);
    }

    @Override
    protected PackageData[] doInBackground(Void... voids) {

        ArrayList<PackageData> installedPackages = new ArrayList<PackageData>();
        ArrayList<PackageData> notInstalledPackages = new ArrayList<PackageData>();

        PackageManager packageManager = mContext.getPackageManager();
        String currentPackage;
        PackageData currentPackageData;

        for(int i = 0; i < allProviders.length; i++){
            currentPackage = allProviders[i];
            currentPackageData = new PackageData(currentPackage, allProvidersNames[i]);
            try {
                packageManager.getPackageInfo(currentPackage, PackageManager.GET_ACTIVITIES);
                installedPackages.add(currentPackageData);
            } catch (PackageManager.NameNotFoundException e) {
                notInstalledPackages.add(currentPackageData);
            }
        }

        PackageData[] result;
        ArrayList<PackageData> resultList;
        if(showInstalled)
            resultList = installedPackages;
        else
            resultList = notInstalledPackages;

        result = new PackageData[resultList.size()];
        for(int i = 0; i < resultList.size(); i++)
            result[i] = resultList.get(i);

        return result;
    }

    @Override
    protected void onPostExecute(PackageData[] result){

        //calls asynchronously given callback
        if(null != mCallback)
            mCallback.action(result);
    }

    public interface Callback{
        public void action(PackageData[] result);
    }

}
