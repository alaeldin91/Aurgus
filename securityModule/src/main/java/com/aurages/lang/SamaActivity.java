package com.Aurages.lang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Helps in rtl support
 */
public class SamaActivity extends Activity {

    protected boolean mIsRtl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getApplication() instanceof SamaApplication) {
            ((SamaApplication) getApplication()).refreshLocale(this, getLangFromPreferences(), false);
        } else {
            Log.d(this.getClass().getSimpleName(), "Couldn't cast application.. be sure to set that in manifest file");
        }

        mIsRtl = isRtl();

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final boolean isRtl = isRtl();
        if (isRtl != mIsRtl) {
            final Intent i = getIntent();
            finish();
            startActivity(i);
        }
    }

    /**
     * @return Returns the key used for getting selected language from shared preferences.
     * Default "language", Override when having difference key
     */
    protected String languagePreferenceKey() {
        return "language";
    }

    /**
     *
     * @return Returns the selected language code i.e ("en", "ar", etc...).
     * By default it assumes that values like ("en", "ar") are stored as is
     * in the key provided in <code>languagePreferenceKey</code>
     */
    protected String getLangFromPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString(languagePreferenceKey(), "en");
    }

    private boolean isRtl() {
        return getLangFromPreferences().equals("ar");
    }
}
