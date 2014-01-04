package it.tiwiz.whatsong;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by Roby on 04/01/14.
 */
public class SearchInstalledProviders extends AsyncTask<Void,Void,SimplePackage[]> {

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
    protected SimplePackage[] doInBackground(Void... voids) {

        ArrayList<SimplePackage> installedPackages = new ArrayList<SimplePackage>();
        ArrayList<SimplePackage> notInstalledPackages = new ArrayList<SimplePackage>();

        PackageManager packageManager = mContext.getPackageManager();
        String currentPackage = "";
        SimplePackage currentSimplePackage;

        for(int i = 0; i < allProviders.length; i++){
            currentPackage = allProviders[i];
            currentSimplePackage = new SimplePackage(currentPackage,allProvidersNames[i]);
            try {
                packageManager.getPackageInfo(currentPackage,PackageManager.GET_ACTIVITIES);
                installedPackages.add(currentSimplePackage);
            } catch (PackageManager.NameNotFoundException e) {
                notInstalledPackages.add(currentSimplePackage);
            }
        }

        SimplePackage[] result;
        ArrayList<SimplePackage> resultList;
        if(showInstalled)
            resultList = installedPackages;
        else
            resultList = notInstalledPackages;

        result = new SimplePackage[resultList.size()];
        for(int i = 0; i < resultList.size(); i++)
            result[i] = resultList.get(i);

        return result;
    }

    @Override
    protected void onPostExecute(SimplePackage[] result){

        //calls asynchronously given callback
        if(null != mCallback)
            mCallback.action(result);
    }

    public interface Callback{
        public void action(SimplePackage[] result);
    }

}
