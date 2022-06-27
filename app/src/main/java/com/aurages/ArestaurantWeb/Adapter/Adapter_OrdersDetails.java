package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Model.BranchesModel;
import com.aurages.ArestaurantWeb.Model.WebOrderDetails_model;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class Adapter_OrdersDetails extends   RecyclerView.Adapter<Adapter_OrdersDetails.ViewHolder> {
    private LayoutInflater mInflater;
    public Adapter_Orders.ItemClickListener mClickListener;
    public  ArrayList <WebOrderDetails_model> webOrderDetails_models=new ArrayList<>();
    public  static final String SHARED_PREF_NAME = "aurages_rest";
    public ArrayList<BranchesModel> Sluglaby;
    public Context context;
    public Adapter_OrdersDetails(Context c,ArrayList<WebOrderDetails_model>webOrderDetails_models){
        this.context=c;
        this.webOrderDetails_models=webOrderDetails_models;
        this.mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public Adapter_OrdersDetails.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.update_orderlist, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OrdersDetails.ViewHolder holder, int position) {
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
        WebOrderDetails_model s= webOrderDetails_models.get(position);

        holder.no.setText( s.getId()+"");
        String product =databaseHelper.GetMatName(s.getMatCode());
        holder.Item.setText(product);
        holder.edit_qty.setText(s.getQty()+"");
        Log.i("ala",s.getQty()+"");
        holder.edit_qty.setText(s.getPrice()+"");

    }

    @Override
    public int getItemCount() {
        return webOrderDetails_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
   TextView no;
   TextView Item;
   TextView edit_qty;
   TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.no);
            Item= itemView.findViewById(R.id.Item);
price=itemView.findViewById(R.id.price);
            edit_qty=itemView.findViewById(R.id.price);
        }
    }
}
