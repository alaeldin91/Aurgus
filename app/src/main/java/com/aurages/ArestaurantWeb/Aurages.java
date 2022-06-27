package com.aurages.ArestaurantWeb;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

public class Aurages extends Application {

    static Aurages context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        context = this;
        super.onCreate();
        SqlLiteDataBase db = new SqlLiteDataBase(this);
        db.getWritableDatabase();

//        db.close();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
