package com.Aurages.lang;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * Useful for localization issues (rtl support)
 */
public class SamaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void refreshLocale(@NonNull Context context, String language, boolean force) {

        final Locale locale;
        if ("ar".equals(language) || "en".equals(language)) {
            locale = new Locale(language);
        } else if (force) {
            // get the system locale (since we overwrote the default locale)
            locale = Resources.getSystem().getConfiguration().locale;
        } else {
            // nothing to do...
            return;
        }

        updateLocale(context, locale);
        final Context appContext = context.getApplicationContext();
        if (context != appContext) {
            updateLocale(appContext, locale);
        }
    }

    private void updateLocale(@NonNull Context context, @NonNull Locale locale) {
        final Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLayoutDirection(config.locale);
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
