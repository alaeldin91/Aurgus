package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Model.BranchesModel;
import com.aurages.ArestaurantWeb.Model.ModelCatogry;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.bumptech.glide.Glide;



import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.GridMats;
import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.MatsList;
import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.adaptermats;

import static com.aurages.ArestaurantWeb.Utils.URL.ROOT_image;

public class AdapterGroups extends RecyclerView.Adapter<AdapterGroups.ViewHolder> {

    private ArrayList<ModelCatogry> Groups;
    private LayoutInflater mInflater;
    private AdapterGroups.ItemClickListener mClickListener;
    private Context context;
    public ArrayList<BranchesModel> Sluglaby;
    private static final String SHARED_PREF_NAME = "aurages_rest";



    public AdapterGroups(Context context, ArrayList<ModelCatogry> g) {
        this.mInflater = LayoutInflater.from(context);
        Groups = g;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.category_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelCatogry g = Groups.get(position);
        holder.GroupName.setText(g.getName());
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
        Sluglaby = databaseHelper.GetBranch();
        for (int i=0;i<Sluglaby.size();i++) {
            String slugbly = Sluglaby.get(i).getSlugable();
            Glide.with(context).load(ROOT_image + slugbly + "/" + "category" + "/" + g.getImage()).override(80, 80).error(R.drawable.foodmenubackgroundpicture).into(holder.GroupImg);


        }
       MatsList = databaseHelper.GetAllMats(OrdersScreen.GroupsList.get(1).getId());
        holder.GroupImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridMats.setVisibility(View.VISIBLE);
                SharedPreferences sp =context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

               SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
                MatsList = databaseHelper.GetAllMats(OrdersScreen.GroupsList.get(position).getId());
                String uid= OrdersScreen.GroupsList.get(position).getId();
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("uid", uid);

                adaptermats = new AdapterMats(context, MatsList);
                GridMats.setAdapter(adaptermats);
            }
        });


    }

    @Override
    public int getItemCount() {
        return Groups.size();
    }

    //    // convenience method for getting data at click position
    public ModelCatogry getItem(int id) {
        return Groups.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView GroupName;
        ImageView GroupImg;
        CardView group_items;

        ViewHolder(View itemView) {
            super(itemView);
            GroupName = itemView.findViewById(R.id.categoryItemName);
            GroupImg = itemView.findViewById(R.id.categoryItemImageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
