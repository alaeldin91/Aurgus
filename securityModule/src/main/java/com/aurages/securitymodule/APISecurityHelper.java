package com.Aurages.securitymodule;

import android.content.Context;
import android.content.SharedPreferences;

public class APISecurityHelper {

    public static int ActivationStatus = -1;

    public static String API_LINK = "http://www.aurages.com/key/API/";

    public static void SetIsActivated(Context context, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();

        editor.putBoolean("IsActivated", value)
                .apply();
    }

    public static boolean IsActivated(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return prefs.getBoolean("IsActivated", false);
    }
}
