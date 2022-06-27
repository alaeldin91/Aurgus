package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Dialog.Tables;
import com.aurages.ArestaurantWeb.Model.TablePlaseModel;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class AdapterTablePlace extends RecyclerView.Adapter<AdapterTablePlace.ViewHolder> {

    private ArrayList<TablePlaseModel> tablePlaseModels;
    private LayoutInflater mInflater;
    private AdapterTablePlace.ItemClickListener mClickListener;
    private Context context;
    private FragmentManager fragmentManager;


    public AdapterTablePlace(Context context, ArrayList<TablePlaseModel> e) {
        this.mInflater = LayoutInflater.from(context);
        this.tablePlaseModels = e;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.table_place_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TablePlaseModel t = tablePlaseModels.get(position);

        holder.tablep_name.setText(t.getCode());

//        if(position==0){
//            t.setSelected(true);
//            //holder.t_place_lay.setBackgroundResource(R.color.t_palce_bg);
//        }

        if (t.isSelected()) {
            t.setSelected(false);
            holder.t_place_lay.setBackgroundResource(R.color.t_palce_bg);
        } else {
            holder.t_place_lay.setBackgroundResource(R.color.gr_tab);
        }


        holder.t_place_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(t.isSelected())
//                {
//                    t.setSelected(false);
//                    holder.t_place_lay.setBackgroundResource(R.color.t_palce_bg);
//                }else {
//                    t.setSelected(true);
//                    holder.t_place_lay.setBackgroundResource(R.color.t_palce_bg);
//                }
                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                boolean SAVE_STATUS = prefs.getBoolean("Status", false);
                if(SAVE_STATUS){
                   // Tables.tableModels = databaseHelper.GetAllTables();
                }else{
//                   Tables.tableModels = databaseHelper.GetAllTables(t.getGuid());
                }


//                Tables.adapterTables = new AdapterTables(context, Tables.tableModels);

                Tables.tables_rec.setAdapter(Tables.adapterTables);

                t.setSelected(true);
                Tables.adapterTablePlace.notifyDataSetChanged();
                holder.t_place_lay.setBackgroundResource(R.color.t_palce_bg);


            }
        });


    }

    @Override
    public int getItemCount() {
        return tablePlaseModels.size();
    }

    //    // convenience method for getting data at click position
    public TablePlaseModel getItem(int id) {
        return tablePlaseModels.get(id);
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
        TextView tablep_name;
        ImageView tablep_image;
        LinearLayout t_place_lay;

        ViewHolder(View itemView) {
            super(itemView);
            tablep_name = itemView.findViewById(R.id.tablep_name);
            tablep_image = itemView.findViewById(R.id.tablep_image);
            t_place_lay = itemView.findViewById(R.id.t_place_lay);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }


}
