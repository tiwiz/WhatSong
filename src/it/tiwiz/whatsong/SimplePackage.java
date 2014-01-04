package it.tiwiz.whatsong;

/**
 * Created by Roby on 04/01/14.
 */
public class SimplePackage {

    private String packageName = "";
    private String packageLabel = "";

    public SimplePackage(){}

    public SimplePackage(String packageName,String packageLabel){
        this.packageName = packageName;
        this.packageLabel = packageLabel;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageLabel() {
        return packageLabel;
    }

    public void setPackageLabel(String packageLabel) {
        this.packageLabel = packageLabel;
    }

    public static final String[] getPackages(SimplePackage[] simplePackages){

        final int size = simplePackages.length;
        final String[] result = new String[size];

        for(int i = 0; i < size; i++)
            result[i] = simplePackages[i].getPackageName();

        return result;
    }

    public static final String[] getNames(SimplePackage[] simplePackages){

        final int size = simplePackages.length;
        final String[] result = new String[size];

        for(int i = 0; i < size; i++)
            result[i] = simplePackages[i].getPackageLabel();

        return result;
    }
}
