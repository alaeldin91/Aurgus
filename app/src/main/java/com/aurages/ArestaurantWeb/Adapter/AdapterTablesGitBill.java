package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Model.TableModel;
import com.aurages.ArestaurantWeb.R;

import java.util.ArrayList;

public class AdapterTablesGitBill extends RecyclerView.Adapter<AdapterTablesGitBill.ViewHolder> {

    private ArrayList<TableModel> tableModels;
    private LayoutInflater mInflater;
    private AdapterTablesGitBill.ItemClickListener mClickListener;
    private Context context;
    private FragmentManager fragmentManager;
    AppCompatDialogFragment dialogFragment;


    public AdapterTablesGitBill(Context context, ArrayList<TableModel> e, AppCompatDialogFragment dialogFragment) {
        this.mInflater = LayoutInflater.from(context);
        this.tableModels = e;
        this.context = context;
        this.dialogFragment=dialogFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.table_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TableModel t = tableModels.get(position);
    /*    holder.e_no.setText(error_log.getError_no());
        holder.e_txt.setText(error_log.getError_txt());*/

       // holder.tablep_name.setText(t.getCode());

        //holder.t_place_lay.setBackgroundColor(Color.argb(255, t.getColorR(), t.getColorG(), t.getColorB()));
       // holder.t_place_card.setCardBackgroundColor(Color.argb(255, t.getColorR(), t.getColorG(), t.getColorB()));


        holder.t_place_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Not yet", Toast.LENGTH_SHORT).show();

//                if(SqlConnector.GetBillFromServer(t.getGuid(), context)){
//                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
//                    dialogFragment.dismiss();
//                }else{
//                    Toast.makeText(context, "Error try again", Toast.LENGTH_SHORT).show();
//                }

//                SharedPreferences prefs = PreferenceManager
//                        .getDefaultSharedPreferences(context);
//                boolean SAVE_STATUS = prefs.getBoolean("Status", false);
//                Toast.makeText(context, "Status \n"+SAVE_STATUS, Toast.LENGTH_SHORT).show();
//
//                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
//
//                if(SAVE_STATUS){
//                    SqlConnector.SaveOnline(context,t.getCode());
//                    OrdersScreen.adapterTemp.notifyDataSetChanged();
//                    OrdersScreen.temp_order = databaseHelper.GetAllTempOrder();
//                    OrdersScreen.adapterTemp= new AdapterTemp(context, OrdersScreen.temp_order, fragmentManager);
//                    OrdersScreen.TempOrder.setAdapter(OrdersScreen.adapterTemp);
//                    OrdersScreen.EdtTotal.setText(OrdersScreen.total+"");
//                }else {
//                    SqlConnector.SaveOffline(context,t.getCode());
//                    OrdersScreen.adapterTemp.notifyDataSetChanged();
//                    OrdersScreen.temp_order = databaseHelper.GetAllTempOrder();
//                    OrdersScreen.adapterTemp= new AdapterTemp(context, OrdersScreen.temp_order, fragmentManager);
//                    OrdersScreen.TempOrder.setAdapter(OrdersScreen.adapterTemp);
//                    OrdersScreen.EdtTotal.setText(OrdersScreen.total+"");
//                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return tableModels.size();
    }

    //    // convenience method for getting data at click position
    public TableModel getItem(int id) {
        return tableModels.get(id);
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
        CardView t_place_card;

        ViewHolder(View itemView) {
            super(itemView);
            tablep_name = itemView.findViewById(R.id.table_name);
            tablep_image = itemView.findViewById(R.id.table_image);
            t_place_lay = itemView.findViewById(R.id.t_place_lay);
            t_place_card = itemView.findViewById(R.id.t_place_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
