package com.aurages.ArestaurantWeb.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

/**
 */
public class SharePref extends AppCompatActivity{

    private static final String SHARED_PREF_NAME = "aurages_rest";

    //AuthUserLogin
    public static void AuthUserLogin(Context context, String userId,String name, String pinCode, String token) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userId", userId);
        edit.putString("name", name);
        edit.putString("pinCode", pinCode);
        edit.putString("token", token);
        edit.apply();

    }
    public static  void Temp_order(Context context,String temp_id){
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sp.edit();
        edit.putString("temp_id", temp_id);

    }

public static String Get_Temp_id(Context context){
    SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
    return sp.getString("temp_id","");
}
//get token
    public static String Get_pinCode(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        return sp.getString("pinCode","");
    }

    //get token
    public static String Get_token(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        return sp.getString("token","");
    }


    public static void SaveLogin(Context context, String u_guid, String u_name, boolean is_logged_in) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("u_guid", u_guid);
        edit.putString("u_name", u_name);
        edit.putBoolean("is_logged_in", is_logged_in);
        edit.apply();
    }

    public static void SaveSession(Context context, boolean begin) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("is_begin", begin);
        edit.apply();
    }
    public static boolean Begin(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        return sp.getBoolean("is_begin",false);
    }

    public static boolean Is_logged_in(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        return sp.getBoolean("is_logged_in",false);
    }



    //Check if data sync
    public static boolean Is_Sync(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        return sp.getBoolean("is_sync",false);
    }


    //save data sync
    public static void SaveSync(Context context, boolean is_sync) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("is_sync", is_sync);
        edit.apply();
    }

    public static String Get_host_name(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        return sp.getString("u_name","");
    }
//
//    public static void SaveUserData(String id, String phone, String first_name, String last_name,
//                                    String email, String motor_type, String img_profile, String latitude, String longitude,
//                                    Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("id", id);
//        edit.putString("phone", phone);
//        edit.putString("first_name", first_name);
//        edit.putString("last_name", last_name);
//        edit.putString("motor_type", motor_type);
//        edit.putString("email", email);
//        edit.putString("img_profile", img_profile);
//        edit.putString("latitude", latitude);
//        edit.putString("longitude", longitude);
//        edit.apply();
//
//    }
//
//
//    public static void SaveDriverPhoneEmail(String phone, Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("phone", phone);
//        edit.apply();
//    }
//
//    public static void SaveDriverStatus(String status, Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("driver_status", status);
//        edit.apply();
//    }
//
//
//    public static void SaveDriverEmail(String email, Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("email", email);
//        edit.apply();
//    }
//
//
//    public static String Get_driver_status(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getString("driver_status", "1");
//    }
//
//
//
//
//
//    public static String Get_driver_first_name(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getString("first_name", null);
//    }
//    public static String Get_driver_last_name(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getString("last_name", null);
//    }
//
//    public static String Get_user_phone(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getString("phone", null);
//    }
//    public static String Get_driver_email(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getString("email", null);
//    }
//
//
//    public static String Get_driver_id(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getString("id", null);
//    }
//    public static String Get_driver_motor_type(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getString("motor_type", null);
//    }
//
//    //
//    public static boolean Is_Logged_in(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        boolean x = false;
//        String id = sp.getString("id", null);
//        if (id != null) {
//            x = true;
//        }
//        return x;
//    }
//
//    public static void Logout(Context context) {
//        final SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.clear();
//        edit.apply();
//    }
//
//    public static void UpdateLatLong(String latitude, String longitude,
//                                    Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("latitude", latitude);
//        edit.putString("longitude", longitude);
//        edit.apply();
//
//    }
//
//    public static void WhenUpdateMyProfile(String firstname, String lastname,String email,String motor_type,
//                                     Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("first_name", firstname);
//        edit.putString("last_name", lastname);
//        edit.putString("email", email);
//        edit.putString("motor_type", motor_type);
//        edit.apply();
//
//    }
//
//
//    public static void SaveLanguage(String s_language, Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("language", s_language);
//        edit.apply();
//    }
//    public static String GetLanguage(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getString("language", "ar");
//    }
//
//    public static void PutOpenState(Context context,boolean val) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putBoolean("is_first_open",val);
//        edit.apply();
//    }
//    public static boolean GetOpenState(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getBoolean("is_first_open",true);
//    }
//
//    public static void PutVersionAndState(Context context,String v_name, String v_code,String update_state,String date ,boolean val) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("v_name",v_name);
//        edit.putString("v_code",v_code);
//        edit.putString("date",date);
//        edit.putString("update_state",update_state);
//        edit.putBoolean("is_new_version_found",val);
//        edit.apply();
//    }
//    public static void No_new_version(Context context, boolean val) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putBoolean("is_new_version_found",val);
//        edit.apply();
//    }
//    public static boolean Is_new_version_found(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getBoolean("is_new_version_found",false);
//    }
//    public static String Get_update_state(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getString("update_state","0");
//    }
//
//
//    public static void Save_not_now_date(Context context, String _date, boolean is_check_today) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("not_now_date",_date);
//        edit.putBoolean("is_check_today",is_check_today);
//        edit.apply();
//    }
//
//    public static String Get_not_now_date(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
//        return sp.getString("not_now_date","");
//    }



}
