package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Model.Error_log;
import com.aurages.ArestaurantWeb.R;

import java.util.ArrayList;

public class AdapterErrorLog extends RecyclerView.Adapter<AdapterErrorLog.ViewHolder> {

    private ArrayList<Error_log> e;
    private LayoutInflater mInflater;
    private AdapterErrorLog.ItemClickListener mClickListener;
    private Context context;
    private FragmentManager fragmentManager;


    public AdapterErrorLog(Context context, ArrayList<Error_log> e, FragmentManager fragmentManager) {
        this.mInflater = LayoutInflater.from(context);
        this.e = e;
        this.context = context;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.log_err_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Error_log error_log = e.get(position);
        holder.e_no.setText(error_log.getError_no());
        holder.e_txt.setText(error_log.getError_txt());



        holder.error_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, " "+e.get(position).getError_no(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return e.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView e_no,e_txt;
        CardView error_items;

        ViewHolder(View itemView) {
            super(itemView);
            e_no = itemView.findViewById(R.id.err_log_err_no);
            e_txt = itemView.findViewById(R.id.err_log_err_txt);
            error_items= itemView.findViewById(R.id.reg_err_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    //    // convenience method for getting data at click position
    public Error_log getItem(int id) {
        return e.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
