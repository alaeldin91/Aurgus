package com.aurages.ArestaurantWeb.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Adapter.AdapterTemp;
import com.aurages.ArestaurantWeb.Model.ModelPPrinter;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.aurages.ArestaurantWeb.wagu.Block;
import com.aurages.ArestaurantWeb.wagu.Board;
import com.aurages.ArestaurantWeb.wagu.Table;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions.getHeight;
import static com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions.getWidth;

public class ShowRecieptDialoge extends Dialog {
    private static final String SHARED_PREF_NAME = "aurages_rest";
    public static AdapterTemp adapterTemp;
    public static RecyclerView GridMats, GridGroups, TempOrder;
    public static ArrayList<Temp_Order> temp_order = new ArrayList<>();
    public static ArrayList<ModelPPrinter> listPrinter = new ArrayList<>();
    private static Handler handler = new Handler();
    public ImageView logo;
    Activity activity;
    FragmentManager fragmentManager;

    Button print;
    Button cancel;

    public ShowRecieptDialoge(@NonNull Activity a, FragmentManager supportFragmentManager) {
        super(a);
        this.activity = a;
        this.fragmentManager = fragmentManager;
    }

    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showreciep);
        getWindow().setLayout(((getWidth(activity) / 100) * 60), ((getHeight(activity) / 100) * 60));
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        print = findViewById(R.id.print);
        cancel = findViewById(R.id.cancel);
        TempOrder=findViewById(R.id.recycler_reciept);
        temp_order = databaseHelper.GetAllTempOrder();
        TempOrder.setLayoutManager(new GridLayoutManager(scanForActivity(getContext()), 1, RecyclerView.VERTICAL, false));

        adapterTemp=new AdapterTemp(scanForActivity(getContext()),temp_order);
        TempOrder.setAdapter(adapterTemp);
        printer s = new printer(scanForActivity(getContext()), fragmentManager);


        Runnable runprintkitchen = new Runnable() {


            public void run() {
                try {
                    s.PrinterRecieptResturant();
                    System.out.println("" +"print suuccess");



                } catch (Exception x) {
                    System.out.println("Exception thrown.");
                }

            }


        };



        print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        printInvoices();
                        Thread threadA2 = new Thread(runprintkitchen, "print kitchen");
                        threadA2.start();
                        Thread.sleep(500);




                    }
                    catch (InterruptedException x) {
                    }


                }
            });




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            clearTemp();
            dismiss();


            }
        });

    }

    public void printInvoices() {
        printer s = new printer(scanForActivity(getContext()), fragmentManager);
        s.printBill();

    }

    public void invoicesResturant() {

        printer s = new printer(scanForActivity(getContext()), fragmentManager);
        s.PrinterRecieptResturant();
    }
    public void clearTemp() {
        new SqlLiteDataBase(getContext()).deleteAllTableData("Temp_orders");
        OrdersScreen.adapterTemp.notifyDataSetChanged();
        SqlLiteDataBase helper = new SqlLiteDataBase(getContext());
        OrdersScreen.temp_order = helper.GetAllTempOrder();
        OrdersScreen.EdtEndTotal.setText("");
        OrdersScreen.EdtTotal.setText("");
        OrdersScreen.adapterTemp = new AdapterTemp(getContext(), OrdersScreen.temp_order);
        OrdersScreen.TempOrder.setAdapter(OrdersScreen.adapterTemp);

    }


}