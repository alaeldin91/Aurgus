package com.aurages.ArestaurantWeb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Dialog.TabItems.OneFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.ThreeFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.TwoFragment;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Aurages.getContext;

public class AdapterCloseOrders extends RecyclerView.Adapter<AdapterCloseOrders.ViewHolder> {

    private ArrayList<WebOrders_model> webOrders_models;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    SqlLiteDataBase dataBasehelper;
    FragmentManager fragmentManager;
    String where;

    public AdapterCloseOrders(Context context, ArrayList<WebOrders_model> webOrdersModels, FragmentManager fragmentManager,String where) {
        this.mInflater = LayoutInflater.from(context);
        this.webOrders_models = webOrdersModels;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.where = where;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_allbillsview, parent, false);
        dataBasehelper = new SqlLiteDataBase(context);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        WebOrders_model w = webOrders_models.get(position);
        holder.date.setText(w.getDate());
        holder.time.setText(w.getBillDate());
        holder.table.setText(w.getTableCode());
        holder.total.setText(w.getTotal()+"");
        w.getId();

        holder.adapter_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();

//
            }
        });


    }

    @Override
    public int getItemCount() {
        return webOrders_models.size();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

//    // convenience method for getting data at click position
//    Integer getItem(int id) {
//        return Mats.get(id);
//    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView date, time, table, total;
        LinearLayout adapter_container;


        ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.adapter_date);
            time = itemView.findViewById(R.id.adapter_time);
            table = itemView.findViewById(R.id.adapter_table);
            total = itemView.findViewById(R.id.adapter_total);

            adapter_container = itemView.findViewById(R.id.adapter_container);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }



    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }








}
