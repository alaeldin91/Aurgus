package com.aurages.ArestaurantWeb.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.AdapterTemp;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DialogeOrederDetails extends Dialog {
    public Activity activity;
    private FragmentManager fragmentManager;
    FloatingActionButton floatingActionButton;

    public static ArrayList<Temp_Order> temp_order = new ArrayList<>();
    public static AdapterTemp adapterTemp;
    private RecyclerView TempOrder;
    int id;
    Context context;

    public DialogeOrederDetails(@NonNull Activity a, FragmentManager fragmentManager, int id ) {
        super(a);
        this.activity = a;
        this.fragmentManager = fragmentManager;
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogorderdet);
        //getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        getWindow().setLayout(((getWidth(activity) / 100) * 90), ((getHeight(activity) / 100) * 90));
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //Dialog.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        TempOrder = findViewById(R.id.temp_order_rec);
        TempOrder.setLayoutManager(new GridLayoutManager(activity, 1, RecyclerView.VERTICAL, false));
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
      //  temp_order = databaseHelper.GetAllTempOrderFromWebDet(id);
        adapterTemp = new AdapterTemp(activity, temp_order);
        TempOrder.setAdapter(adapterTemp);

        floatingActionButton = findViewById(R.id.f_close);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (new SqlLiteDataBase(activity.getApplicationContext()).Update_Order_Row(id,"1")){
                   Toast.makeText(activity, "Done", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
               }
            }
        });




    }


    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    public static int getHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
//    private  FragmentManager getFragmentM(){
//        try{
//            final Activity activity = (Activity) getContext();
//
//            // Return the fragment manager
//            return activity.getFragmentManager();
//
//            // If using the Support lib.
//            // return activity.getSupportFragmentManager();
//
//        } catch (ClassCastException e) {
//            Log.d(TAG, "Can't get the fragment manager with this");
//        }
//    }



}
