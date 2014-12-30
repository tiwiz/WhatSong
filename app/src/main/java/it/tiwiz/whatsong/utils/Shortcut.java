/*
 The MIT License (MIT)

 Copyright (c) 2014-2015 Roberto Orgiu, roberto.orgiu@gmail.com

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

package it.tiwiz.whatsong.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import org.intellij.lang.annotations.MagicConstant;

import it.tiwiz.whatsong.BuildConfig;


/**
 * This class contains the full logic to implement a Intent that will create a shortcut
 * on the user's home screen.
 * <br/><br/>
 * <b>Howto:</b>
 * <ol>
 *     <li>Create a {@link it.tiwiz.whatsong.utils.Shortcut.Builder} object</li>
 *     <li>Pass to this {@link it.tiwiz.whatsong.utils.Shortcut.Builder} object
 *     the Icon Resource ID and the {@link java.lang.String} label for the shortcut,
 *     together with the {@link android.content.Intent} you want the shortcut to fire when
 *     the icon is clicked</li>
 *     <li>Invoking the method
 *     {@link it.tiwiz.whatsong.utils.Shortcut.Builder#create(android.content.Context)},
 *     generate a {@link it.tiwiz.whatsong.utils.Shortcut} object</li>
 *     <li>Create the final  {@link android.content.Intent} by using and use it as parameter in a
 *     call to {@link android.app.Activity#setResult(int, android.content.Intent)} with
 *     {@code RESULT_OK} parameter</li>
 * </ol>
 *
 * <b>Note</b>: in case you fail in passing all the parameters, a warning will be sent by the
 * {@link it.tiwiz.whatsong.utils.Shortcut.L} class.
 * <br/><br/>
 * <b>Code example:</b><br/>
 * {@code shortcut = new Shortcut.Builder()}<br/>
 *      {@code .setIconResource(resID)}<br/>
 *      {@code .setLabel(shortcutLabel)}<br/>
 *      {@code .setLaunchIntent(launchIntent)}<br/>
 *      {@code .create(context);}<br/>
 * {@code setResult(RESULT_OK, shortcut.getShortcutIntent();}
 *
 * @see it.tiwiz.whatsong.utils.Shortcut.Builder Builder
 * @see #getShortcutIntent() Intent
 * @see it.tiwiz.whatsong.utils.Shortcut.L#w(String) Logger
 */
public class Shortcut {

    private final Intent launchIntent;
    private final Intent.ShortcutIconResource icon;
    private final String label;

    private Shortcut(Intent launchIntent, Intent.ShortcutIconResource icon, String label) {
        this.launchIntent = launchIntent;
        this.icon = icon;
        this.label = label;
    }

    /**
     * This method creates the {@link android.content.Intent} used as Result in a call to
     * {@link android.app.Activity#setResult(int, android.content.Intent)} and will create
     * the shortcut on user's home screen
     */
    public Intent getShortcutIntent() {

        Intent intent = new Intent();

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, label);

        return intent;
    }

    /**
     * This class is the only way to create a {@link it.tiwiz.whatsong.utils.Shortcut}
     */
    public static class Builder {

        private int iconResId = 0;
        private String label = null;
        private Intent launchIntent = null;

        public Builder() {}

        /**
         * Sets the icon of the shortcut. This icon will be visible by the user and calling this
         * method is mandatory in order to create the shortcut itself.
         */
        public Builder setIconResource(int iconResId) {
            this.iconResId = iconResId;
            return this;
        }

        /**
         * Sets the label of the shortcut. This label will be visible by the user and calling this
         * method is mandatory in order to create the shortcut itself.
         */
        public Builder setLabel(@NonNull String label) {
            this.label = label;
            return this;
        }

        /**
         * Sets the Intent that will be fired when the user clicks on the shortcut. Calling this
         * method is mandatory in order to create the shortcut itself.
         */
        public Builder setLaunchIntent(@NonNull Intent launchIntent) {
            this.launchIntent = launchIntent;
            return this;
        }

        /**
         * This method will create a {@link it.tiwiz.whatsong.utils.Shortcut} that can be used to
         * create a shortcut on user's home screen.
         *
         * @see #setIconResource(int)
         * @see #setLabel(String)
         * @see #setLaunchIntent(android.content.Intent)
         */
        public Shortcut create(final Context context) {
            checkShortcutMandatoryData();
            Intent.ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(context, iconResId);
            Shortcut shortcut = new Shortcut(launchIntent, icon, label);
            return shortcut;
        }

        private void checkShortcutMandatoryData(){
            if (iconResId == 0) {
                L.w(L.ICON_RESOURCE_NOT_SET);
            }

            if (label == null || label.length() == 0) {
                L.w(L.LABEL_NOT_SET);
            }

            if (launchIntent == null) {
                L.w(L.INTENT_NOT_SET);
            }
        }
    }

    /**
     * This class represent a minimalistic wrapper of {@link android.util.Log} utility that
     * will warn you about the absence of any of the parameters while in <b>debug build</b>
     */
    public static class L {

        protected static final String INTENT_NOT_SET = "Launch Intent";
        protected static final String ICON_RESOURCE_NOT_SET = "Icon Resource ID";
        protected static final String LABEL_NOT_SET = "Label";

        private static final String exceptionMessageFormat = "FATAL: %s has not been set, the shortcut cannot be created!";
        private static final String TAG = L.class.getSimpleName();


        @MagicConstant(stringValues = {INTENT_NOT_SET, ICON_RESOURCE_NOT_SET, LABEL_NOT_SET})
        public @interface Reasons{
        }

        public static void w (@Reasons String reason) {
            if (BuildConfig.DEBUG) {
                String exceptionMessage = String.format(exceptionMessageFormat, reason);
                Log.w(TAG, exceptionMessage);
            }
        }

    }
}
