package it.tiwiz.whatsong.preferences;

import android.app.Activity;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import it.tiwiz.whatsong.R;

/**
 * This preference is used to let the user share this app using the Facebook Share APIs
 */
public class FacebookInvitePreference extends Preference {

    private final AppInviteContent appInviteContent;

    public FacebookInvitePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setSummary(R.string.app_share_facebook_summary);

        appInviteContent = createAppInvite(context.getString(R.string.app_share_facebook_url),
                context.getString(R.string.app_share_facebook_image));
    }

    public FacebookInvitePreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FacebookInvitePreference(Context context) {
        this(context, null);
    }

    @Override
    protected void onClick() {
        super.onClick();

        if (appInviteContent != null) {
            AppInviteDialog.show((Activity) getContext(), appInviteContent);
        }
    }

    /**
     * This method will build the {@link AppInviteContent} containing all the data for building the
     * proper dialog that will appear when the user presses the button
     */
    private AppInviteContent createAppInvite(String appLinkUrl, String appImageUrl) {
        AppInviteContent content  = null;

        if (AppInviteDialog.canShow()) {
            content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(appImageUrl)
                    .build();
        }

        return content;
    }
}
