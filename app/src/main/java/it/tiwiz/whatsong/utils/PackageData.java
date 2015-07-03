package it.tiwiz.whatsong.utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.intellij.lang.annotations.MagicConstant;

/**
 * This class represents the basic package data needed to
 * filter and identify the correct installed music recognition
 * providers.
 */
public class PackageData implements Parcelable {

    private static final int PACKAGE_NAME = 0;
    private static final int PACKAGE_LABEL = 1;

    @MagicConstant(intValues = {PACKAGE_NAME, PACKAGE_LABEL})
    private @interface Keys{}

    private final String packageName;
    private final String packageLabel;

    public PackageData(String packageName, String packageLabel) {
        this.packageName = packageName;
        this.packageLabel = packageLabel;
    }

    private PackageData(Parcel in) {
        packageName = in.readString();
        packageLabel = in.readString();
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", packageLabel, packageName);
    }

    /**
     * This method uses the {@link org.intellij.lang.annotations.MagicConstant} annotation
     * to decide which of the data it needs to return.
     * @return a {@link java.lang.String} representing the
     */
    protected String getStringFromKey(@Keys int key) {
        String result;

        switch (key) {
            case PACKAGE_NAME:
                result = packageName;
                break;
            case PACKAGE_LABEL:
                result = packageLabel;
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageName);
        dest.writeString(packageLabel);
    }

    public static final Creator<PackageData> CREATOR = new Creator<PackageData>() {
        @Override
        public PackageData createFromParcel(Parcel source) {
            return new PackageData(source);
        }

        @Override
        public PackageData[] newArray(int size) {
            return new PackageData[size];
        }
    };

    /**
     * This class contains the methods for extracting the packages and the names of the installed apps.
     *
     * This is needed because we want to filter only for the providers that are actually installed,
     * so that we have a shorter list of apps to chose from.
     *
     * @see #getNames(PackageData[]) getting packages name
     * @see #getPackages(PackageData[]) getting packages label
     * @see #getStringFromKey(int) getting specific package data by key
     */
    public static class Utils {

        public static String[] getPackages(PackageData[] data) {

            return getStrings(data, PACKAGE_NAME);
        }

        public static String[] getNames(PackageData[] data) {

            return getStrings(data, PACKAGE_LABEL);
        }

        /**
         * This is a generic method for getting any of the package data using the
         * {@link #getStringFromKey(int)} method and the {@link org.intellij.lang.annotations.MagicConstant}
         * annotation for {@code Keys} interface
         */
        private static String[] getStrings(PackageData[] data, @Keys int key) {
            int size = data.length;
            String[] result = new String[size];

            for (int i = 0; i < size; i++)
                result[i] = data[i].getStringFromKey(key);

            return result;
        }
    }
}
