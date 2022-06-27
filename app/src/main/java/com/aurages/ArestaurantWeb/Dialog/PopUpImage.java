package com.aurages.ArestaurantWeb.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import static com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions.getHeight;
import static com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions.getWidth;

public class PopUpImage  extends Dialog {
    Activity activity;
    FragmentManager fragmentManager;

    public PopUpImage(@NonNull Activity a, FragmentManager supportFragmentManager) {
        super(a);
        this.activity = a;

        this.fragmentManager = fragmentManager;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        getWindow().setLayout(((getWidth(activity) / 100) * 70), ((getHeight(activity) / 100) * 80));

    }
}