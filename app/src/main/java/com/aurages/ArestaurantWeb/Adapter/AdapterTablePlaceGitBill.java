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

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Dialog.TablesGetBill;
import com.aurages.ArestaurantWeb.Model.TablePlaseModel;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class AdapterTablePlaceGitBill extends RecyclerView.Adapter<AdapterTablePlaceGitBill.ViewHolder> {

    private ArrayList<TablePlaseModel> tablePlaseModels;
    private LayoutInflater mInflater;
    private AdapterTablePlaceGitBill.ItemClickListener mClickListener;
    private Context context;
    AppCompatDialogFragment dialogFragment;



    public AdapterTablePlaceGitBill(Context context, ArrayList<TablePlaseModel> e, AppCompatDialogFragment dialogFragment) {
        this.mInflater = LayoutInflater.from(context);
        this.tablePlaseModels = e;
        this.context = context;
        this.dialogFragment=dialogFragment;
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
                 //   TablesGetBill.tableModels = SqlConnector.GetAllTables(t.getGuid(),context);
                }else{
//                    TablesGetBill.tableModels = databaseHelper.GetAllTables(t.getGuid());
                }


                TablesGetBill.adapterTablesGitBill = new AdapterTablesGitBill(context, TablesGetBill.tableModels,dialogFragment);

                TablesGetBill.tables_rec.setAdapter(TablesGetBill.adapterTablesGitBill);

                t.setSelected(true);
                TablesGetBill.adapterTablePlaceGitBill.notifyDataSetChanged();
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
