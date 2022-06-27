package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Dialog.TempEdit;
import com.aurages.ArestaurantWeb.Dialog.TempEditDesign;
import com.aurages.ArestaurantWeb.Dialog.TempEditDialog;
import com.aurages.ArestaurantWeb.Model.ModelModifiors;
import com.aurages.ArestaurantWeb.Model.OrderOptions;
import com.aurages.ArestaurantWeb.R;
import java.util.ArrayList;
public class AdapterOptions extends RecyclerView.Adapter<AdapterOptions.ViewHolder> {

    public static ArrayList<OrderOptions> orderOptions;
    private LayoutInflater mInflater;
    private AdapterOptions.ItemClickListener mClickListener;
    private Context context;
    private FragmentManager fragmentManager;
    private static final String SHARED_PREF_NAME = "aurages_rest";
    public AdapterOptions(Context context, ArrayList<OrderOptions> t) {
        this.mInflater = LayoutInflater.from(context);
        orderOptions = t;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.sub_temp_rec_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderOptions t = orderOptions.get(position);


        holder.name.setText(t.getNameAr());




        holder.temp_card.setOnClickListener(new View.OnClickListener() {
            int[] radioValue = new int[]{0,1,2};

            @Override
            public void onClick(View v) {
            String modifiers=t.getNameAr();
                SharedPreferences sp =context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("note",modifiers);

                if (radioValue[0] == 0) {
                    TempEditDialog.notetempedtdesign.append(" " + "بدون" + " " + t.getNameAr() + " " + "#");
                } else if
                (radioValue[1] == 1) {
                    TempEditDialog.notetempedtdesign.append(" " + "مع" + " " + t.getNameAr() + " " + "#");
                } else{
                    TempEditDialog.notetempedtdesign.append(" " + "زيادة" + " " + t.getNameAr() + " " + "#");
                }


        }
        });







    }

    @Override
    public int getItemCount() {
        return orderOptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView temp_card;
        TextView name;
        ImageView  circleImageView;

        ViewHolder(View itemView) {
            super(itemView);
            temp_card = itemView.findViewById(R.id.sub_temp_card);
            name = itemView.findViewById(R.id.sub_order_option_name);
           circleImageView = itemView.findViewById(R.id.order_option_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }



    }


    //    // convenience method for getting data at click position
    public OrderOptions getItem(int id) {
        return orderOptions.get(id);
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


    public void openDialog(String temp_id) {
        TempEdit exampleDialog = new TempEdit();
        Bundle args=new Bundle();
        args.putString("temp_id",temp_id);

        exampleDialog.setArguments(args);
        /*exampleDialog.show(((FragmentActivity)context).getSupportFragmentManager()
                ,"New Order");*/
        exampleDialog.show(fragmentManager
                ,"New Order");



        exampleDialog.setCancelable(true);
    }

}
