package com.aurages.ArestaurantWeb.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.Adapter_Orders;
import com.aurages.ArestaurantWeb.Adapter.Adapter_OrdersDetails;
import com.aurages.ArestaurantWeb.Adapter.PaymentAdapterSave;
import com.aurages.ArestaurantWeb.Model.Model_PaymentType;
import com.aurages.ArestaurantWeb.Model.WebOrderDetails_model;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions.getHeight;
import static com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions.getWidth;

public class DialogeUpdateOrder  extends Dialog {
    public static RecyclerView orders_recycler;
    public static ArrayList<WebOrders_model> orders = new ArrayList<>();
    public static ArrayList<WebOrderDetails_model> details = new ArrayList<>();
    public static RecyclerView order_details;
    public static LinearLayout table_layers;
    public static Adapter_Orders adapter_orders;

    public static Adapter_OrdersDetails adapter_orders_details;
    FragmentManager fragmentManager;
        Activity a;

    public DialogeUpdateOrder(@NonNull Activity a, FragmentManager supportFragmentManager) {
       super(a);
        this.a = a;

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
        getWindow().setLayout(((getWidth(a) / 100) * 100), ((getHeight(a) / 100) * 110));

        setContentView(R.layout.updateorder);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        // getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
               // WindowManager.LayoutParams.MATCH_PARENT);
        orders=databaseHelper.Get_All_Orders();
        orders_recycler= findViewById(R.id.data_list_view_order);
        order_details=findViewById(R.id.data_list_view_order_details);
        table_layers=findViewById(R.id.table_layers);
    //    details= databaseHelper.Get_WebOrderDetails_model()
        orders_recycler.setLayoutManager(new LinearLayoutManager(scanForActivity(getContext()),RecyclerView.VERTICAL, false));
        order_details.setLayoutManager(new LinearLayoutManager(scanForActivity(getContext()),RecyclerView.VERTICAL, false));
     //   Adapter_OrdersDetails ordersDetails= new Adapter_OrdersDetails(getContext(),)
        details = databaseHelper.Get_WebOrderDetails_model(orders.get(0).getGuid());

        adapter_orders= new Adapter_Orders(getContext(),orders);
        adapter_orders_details= new Adapter_OrdersDetails(getContext(),details);
if (orders.size()>0) {
    orders_recycler.setAdapter(adapter_orders);
    order_details.setAdapter(adapter_orders_details);



}





    }



}
