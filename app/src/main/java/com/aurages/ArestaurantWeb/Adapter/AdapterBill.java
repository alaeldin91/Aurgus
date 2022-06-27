package com.aurages.ArestaurantWeb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Dialog.Payment;
import com.aurages.ArestaurantWeb.Dialog.payment_save;
import com.aurages.ArestaurantWeb.Model.ImageModelBill;
import com.aurages.ArestaurantWeb.Model.ModelBill;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.ViewHolder> {
    private static final String SHARED_PREF_NAME = "aurages_rest";
    ArrayList<ModelBill> billtype;
    String type;
    Context context;
    ArrayList<ImageModelBill> imageModels;
    private FragmentManager fragmentManager;
    private LayoutInflater mInflater;

    public AdapterBill(ArrayList<ModelBill> billtype, ArrayList<ImageModelBill> imageModels, String type, Context context) {
        this.billtype = billtype;
        this.imageModels = imageModels;
        this.type = type;
        this.context = context;


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


    @NonNull
    @Override
    public AdapterBill.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.saveoptions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBill.ViewHolder holder, int position) {
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
        final ImageModelBill model = imageModels.get(position);
      //  billtype = databaseHelper.GETBill();
     //   holder.textBill.setText(billtype.get(position).getBillKindName());
       // holder.imageBill.setImageResource(model.getImagePath());
        holder.imageBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String billId = billtype.get(position).getId();
                String billName=billtype.get(position).getBillKindName();
                int billNum= billtype.get(position).getBillKindNumber();

                SharedPreferences sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sps.edit();
                edit.putString("billId", billId);
                edit.putString("billName", billName);
                edit.putInt("billNum", billNum);


                edit.apply();
                Log.i("ala", billId);
                fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                if(type.equals("print")) {
                    Payment payments = new Payment(type, billId);

                    payments.show(fragmentManager, type);
                }
                else {
                    payment_save payments = new payment_save(type, billId);

                    payments.show(fragmentManager, type);
                }

            }
        });

    }

    public ModelBill getItem(int id) {
        return billtype.get(id);
    }

    @Override
    public int getItemCount() {
        return billtype.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardBill;
        LinearLayout linearBill;
        TextView textBill;
        ImageView imageBill;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textBill = itemView.findViewById(R.id.textBill);
            imageBill = itemView.findViewById(R.id.imageBill);
        }
    }
}
