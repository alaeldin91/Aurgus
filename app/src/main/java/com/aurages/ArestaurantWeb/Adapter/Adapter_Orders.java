package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Dialog.DialogeUpdateOrder;
import com.aurages.ArestaurantWeb.Model.BranchesModel;
import com.aurages.ArestaurantWeb.Model.ModelProduct;
import com.aurages.ArestaurantWeb.Model.WebOrderDetails_model;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.GridMats;
import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.MatsList;
import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.adaptermats;
import static com.aurages.ArestaurantWeb.Dialog.DialogeUpdateOrder.order_details;
import static com.aurages.ArestaurantWeb.Dialog.DialogeUpdateOrder.table_layers;

public class Adapter_Orders extends  RecyclerView.Adapter<Adapter_Orders.ViewHolder> {
    private ArrayList<WebOrders_model> orders;
    private LayoutInflater mInflater;
    public Adapter_Orders.ItemClickListener mClickListener;
public  ArrayList <WebOrderDetails_model> webOrderDetails_models= new ArrayList<>();
     public  static final String SHARED_PREF_NAME = "aurages_rest";
    public ArrayList<BranchesModel> Sluglaby;
    Adapter_OrdersDetails adapter_ordersDetails;
 public Context context;

    public  int temp_id;
    public Adapter_Orders(Context context,ArrayList<WebOrders_model> orders){
        this.orders=orders;
        this.context=context;
        this.mInflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public Adapter_Orders.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.update_orderlist, parent, false);
        return new ViewHolder(view);

    }
    public void setClickListener(Adapter_Orders.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Orders.ViewHolder holder, int position) {
        WebOrders_model w= orders.get(position);
        holder.no.setText(w.getId()+"");
        holder.items.setText("the order no is"+w.getId());
        holder.total.setText(w.getTotal()+"");
        holder.discount.setText(w.getDiscount()+"");
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);

     //   webOrderDetails_models= databaseHelper.Get_WebOrderDetails_model(w.getGuid());
        holder.items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                table_layers.setVisibility(View.VISIBLE);
                SharedPreferences sp =context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
                webOrderDetails_models = databaseHelper.Get_WebOrderDetails_model(DialogeUpdateOrder.orders.get(position).getGuid());
                String uid= OrdersScreen.GroupsList.get(position).getId();
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("uid", uid);

                adapter_ordersDetails = new Adapter_OrdersDetails(context, webOrderDetails_models);
                order_details.setAdapter(adapter_ordersDetails);

            }
        });


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
TextView no;
TextView items;
TextView total;
EditText discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.no);
            items=itemView.findViewById(R.id.Item);
            total= itemView.findViewById(R.id.total);discount=itemView.findViewById(R.id.discount);

        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());

        }
    }
}