package com.aurages.ArestaurantWeb.Dialog.TabItems;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.AdapterCloseOrders;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class ThreeFragment extends Fragment {

    View view;
    public static ArrayList<WebOrders_model> webOrders_models = new ArrayList<>();
    public static   AdapterCloseOrders adapterCloseOrders;
    public static RecyclerView recyclerView;
    TextView item1_no_order;

    public ThreeFragment() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.item_one, container, false);
        item1_no_order = view.findViewById(R.id.item1_no_order);
        recyclerView = view.findViewById(R.id.item1_rec);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        webOrders_models = databaseHelper.Get_Orders(2,"2");

        if(webOrders_models.size()>0){
            adapterCloseOrders = new AdapterCloseOrders(getContext(),webOrders_models,getFragmentManager(),"three");
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));
            recyclerView.setVisibility(View.VISIBLE);
            item1_no_order.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(adapterCloseOrders);
        }else{
            recyclerView.setVisibility(View.INVISIBLE);
            item1_no_order.setVisibility(View.VISIBLE);
        }



        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        webOrders_models = databaseHelper.Get_Orders(2,"2");

        if(webOrders_models.size()>0){
            adapterCloseOrders = new AdapterCloseOrders(getContext(),webOrders_models,getFragmentManager(),"three");
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));
            recyclerView.setVisibility(View.VISIBLE);
            item1_no_order.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(adapterCloseOrders);
        }else{
            recyclerView.setVisibility(View.INVISIBLE);
            item1_no_order.setVisibility(View.VISIBLE);
        }
    }
}
