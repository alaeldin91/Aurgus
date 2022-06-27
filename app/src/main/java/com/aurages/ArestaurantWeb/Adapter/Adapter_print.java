package com.aurages.ArestaurantWeb.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Dialog.TempEditDialog;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.R;

import java.io.IOException;
import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.discount;
import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.total;

public class Adapter_print extends RecyclerView.Adapter<Adapter_print.ViewHolder> {

    private ArrayList<Temp_Order> temp_order;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    public   static int temp_no;
    public static   String discounts;
    public static String sum;
    public static float Setting_tax;
    public static float fdiscount;
    private  final String SHARED_PREF_NAME = "aurages_rest";

//    private FragmentManager fragmentManager;


    public Adapter_print(Context context, ArrayList<Temp_Order> t) {
        this.mInflater = LayoutInflater.from(context);
        temp_order = t;
        this.context = context;
//    this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.temp_order_items, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Temp_Order t = temp_order.get(position);
        //holder.GroupName.setText(g.getName());
        temp_no= position+1;
        SharedPreferences sp =context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("temp_no", temp_no);
        holder.temp_no.setText(" "+position+1+"");
        holder.temp_name.setText(t.getName());
        String temp_name= t.getName();
        edit.putString("temp_name", temp_name);

        holder.temp_price.setText(" "+round(t.getPrice(), 1) + "");
        float  price= t.getPrice();
        edit.putFloat("price",price);


        holder.temp_qty.setText(" "+t.getQty() + "");
        int qty= t.getQty();
        edit.putInt("Qty",qty);
        String type= String.valueOf(t.getType());
        edit.putString("type",type);
        String order_id=t.getGuid();
        edit.putString("order_id",order_id);
        String  product_id=t.getMatCode();
        edit.putString("product_id",product_id);
        edit.apply();

        total += t.getPrice() * t.getQty();
        holder.temp_total.setText(round(t.getPrice() * t.getQty(), 1) + "");
        OrdersScreen.EdtTotal.setText(round(total, 1) + "");


        if (position == temp_order.size() - 1) {
            // total += t.getPrice() * t.getQty();


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            Setting_tax = Float.parseFloat(prefs.getString("Tax", "0"));
            double o_tax = round(total, 1) * Setting_tax / 100;
            OrdersScreen.EdtTax.setText(round(o_tax, 1) + " " + "SDG");
            //OrdersScreen.EdtEndTotal.setText((round(total, 1) + round(o_tax, 1))-fdiscount+ " " + "SDG");
            discount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {


                        discounts = OrdersScreen.discount.getText().toString();
                        fdiscount = Float.parseFloat(discounts);

                        OrdersScreen.EdtEndTotal.setText((round(total, 1) + round(o_tax, 1) - fdiscount + " " + "SDG"));
                    }
                    catch (Exception e){
                        e.printStackTrace();

                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // OrdersScreen.EdtEndTotal.setText((round(total, 1) + round(o_tax, 1)- fdiscount + " " + "SDG"));


                }
            });
            OrdersScreen.EdtEndTotal.setText((round(total, 1) + round(o_tax, 1)-fdiscount)+" "+"SDG");


        }



        holder.temp_items_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogEditTemp(t.getGuid(),t.getMatCode(),context);
                //  openDialogEditTemp(t.getGuid(),t.getMatCode(),context);
            }
        });


//        holder.group_items.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(context, " "+g.getGuid(), Toast.LENGTH_SHORT).show();
//                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
//                MatsList = databaseHelper.GetAllMats( OrdersScreen.GroupsList.get(position).getGuid());
//                adaptermats = new AdapterMats(context, MatsList);
//                GridMats.setAdapter(adaptermats);
//            }
//        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context, "Checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Un Checked", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return temp_order.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView temp_no,temp_name, temp_price, temp_qty, temp_total;
        EditText discount;
        LinearLayout temp_items_lay;
        CheckBox checkBox;


        ViewHolder(View itemView) {
            super(itemView);
            temp_no = itemView.findViewById(R.id.temp_no);
            temp_name = itemView.findViewById(R.id.temp_name);
            temp_price = itemView.findViewById(R.id.temp_price);
            temp_qty = itemView.findViewById(R.id.temp_qty);
            temp_total = itemView.findViewById(R.id.temp_total);
            temp_items_lay = itemView.findViewById(R.id.temp_items_lay);
            checkBox = itemView.findViewById(R.id.checkbox_visibility);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }


    }


    //    // convenience method for getting data at click position
    public Temp_Order getItem(int id) {
        return temp_order.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    //    public void openDialog(String temp_id) {
//        TempEditDesign exampleDialog = new TempEditDesign();
//        Bundle args = new Bundle();
//        args.putString("temp_id", temp_id);
//        exampleDialog.setArguments(args);
//        /*exampleDialog.show(((FragmentActivity)context).getSupportFragmentManager()
//                ,"New Order");*/
//        //exampleDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
//        exampleDialog.show(fragmentManager
//                , "New Order");
//
//
//        exampleDialog.setCancelable(true);
//    }
    public void openDialogEditTemp(String temp_id, String MatCode,Context ctx) {
        TempEditDialog tempEditd = new TempEditDialog((Activity) ctx,temp_id,MatCode);
        Bundle args = new Bundle();
        args.putString("temp_id", temp_id);
        //tempEditd.setArguments(args);
        /*exampleDialog.show(((FragmentActivity)context).getSupportFragmentManager()
                ,"New Order");*/
        //exampleDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
        tempEditd.show();


        tempEditd.setCancelable(true);
    }

}
