package com.aurages.ArestaurantWeb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Dialog.Mat_Details;
import com.aurages.ArestaurantWeb.Dialog.TabItems.OneFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.ThreeFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.TwoFragment;
import com.aurages.ArestaurantWeb.Model.Mats;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Aurages.getContext;

public class AdapterViewOrders extends RecyclerView.Adapter<AdapterViewOrders.ViewHolder> {

    private ArrayList<WebOrders_model> webOrders_models;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    SqlLiteDataBase dataBasehelper;
    FragmentManager fragmentManager;
    String where;

    public AdapterViewOrders(Context context, ArrayList<WebOrders_model> webOrdersModels, FragmentManager fragmentManager, String where) {
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


                if(new SqlLiteDataBase(context).InsertTo_Temp_ordersFromView(w.getGuid(),1)){

                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                    OrdersScreen.last_view_guid=w.getGuid();
                    switch (where){
                        case "one":{
                            OrdersScreen.last_view_tableCode=w.getTableCode();
                            OrdersScreen.last_view_Billkind="0";
                            Toast.makeText(context, "Tab View 1", Toast.LENGTH_SHORT).show();
                                         SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
                                           OneFragment.webOrders_models = databaseHelper.Get_Orders(0,"0");
                                            OneFragment.adapterCloseOrders.notifyDataSetChanged();
                                           OneFragment.recyclerView.setAdapter(OneFragment.adapterCloseOrders);
                            break;}
                        case "two":{
                            OrdersScreen.last_view_tableCode=w.getTableCode();
                            OrdersScreen.last_view_Billkind="1";
                            Toast.makeText(context, "Tab View 2", Toast.LENGTH_SHORT).show();
                                       SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
                                       TwoFragment.webOrders_models = databaseHelper.Get_Orders(1,"0");
                                      TwoFragment.adapterCloseOrders.notifyDataSetChanged();
                                            TwoFragment.recyclerView.setAdapter(TwoFragment.adapterCloseOrders);
                            break;}
                        case "three":{
                            OrdersScreen.last_view_tableCode=w.getTableCode();
                            OrdersScreen.last_view_Billkind="2";
                                           OrdersScreen.tables.dismiss();
                                            SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
                                           ThreeFragment.webOrders_models = databaseHelper.Get_Orders(2,"0");
                                        ThreeFragment.adapterCloseOrders = new AdapterCloseOrders(getContext(),webOrders_models,fragmentManager,"three");
                                            ThreeFragment.adapterCloseOrders.notifyDataSetChanged();
                                            ThreeFragment.recyclerView.setAdapter(ThreeFragment.adapterCloseOrders);

                                            ((Activity)context).finish();
                            Toast.makeText(context, "Tab View 3", Toast.LENGTH_SHORT).show();

                            break;}
                    }
                    ((Activity)context).finish();
                }else{
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                }

            CloseDialog(w);

                if(dataBasehelper.Update_Order_Row(w.getId(),"1")){
                 Toast.makeText(context, "Order Closed", Toast.LENGTH_SHORT).show();
                    switch (w.getBillKind()){
                     case 0 :{
                            OneFragment.webOrders_models = dataBasehelper.Get_Orders(0,"0");
                            OneFragment.adapterCloseOrders.notifyDataSetChanged();
                            OneFragment.recyclerView.setAdapter(OneFragment.adapterCloseOrders);
                            break;}
                        case 1 :{
                            TwoFragment.webOrders_models = dataBasehelper.Get_Orders(1,"0");
                            TwoFragment.adapterCloseOrders.notifyDataSetChanged();
                            TwoFragment.recyclerView.setAdapter(OneFragment.adapterCloseOrders);
                            break;}
                        case 2 :{
                            ThreeFragment.webOrders_models = dataBasehelper.Get_Orders(2,"0");
                            ThreeFragment.adapterCloseOrders.notifyDataSetChanged();
                            ThreeFragment.recyclerView.setAdapter(OneFragment.adapterCloseOrders);
                            break;}
                    }

                }else{
                    Toast.makeText(context, "Order Not Closed", Toast.LENGTH_SHORT).show();
                }
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
 //Integer getItem(int id) {
   //     return Mats.get(id);
   // }

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

 //  public void openDialog(String mat_img_guid,String mat_name,String mat_price,String mat_note) {
   //    Mat_Details mat_details = new Mat_Details();
     // Bundle args=new Bundle();
       // args.putString("mat_img_guid",mat_img_guid);
        //args.putString("mat_name",mat_name);
        //args.putString("mat_price",mat_price);
        //args.putString("mat_note",mat_note);
        //mat_details.setArguments(args);
        /*exampleDialog.show(((FragmentActivity)context).getSupportFragmentManager()
//                ,"New Order");*/
    //mat_details.show(fragmentManager
      //          ,"Mat Details");



        //mat_details.setCancelable(true);
    //}

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }



    public void CloseDialog(WebOrders_model w) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;


        builder.setMessage("Close This Order !")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                              if (new SqlLiteDataBase(context).Update_Order_Row(w.getId(),"1")){








                             }else{
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }




}
