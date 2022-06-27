package com.aurages.ArestaurantWeb.Model.local;

import android.content.Context;
import android.content.SharedPreferences;

public class DataManager {
 private static SharedPreferences prefs;
 private Context context;
 public static String firstName;
 public static String lastName;
 public static String token;
 public static String bincode;

 private DataManager(Context context){
     this.context = context;
 }
public static void  init(Context context){
    if(prefs == null)
        prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

}
    public static String read(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static boolean read(String key, boolean defValue) {
        return prefs.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public static Integer read(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(key, value).commit();
    }

}
