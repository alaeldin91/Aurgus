package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.Report_mat_sale;
import com.aurages.ArestaurantWeb.Model.WebOrderDetails_model;
import com.aurages.ArestaurantWeb.R;

import java.util.ArrayList;

public class AdapterReport_bills_mats extends RecyclerView.Adapter<AdapterReport_bills_mats.ViewHolder> {

    private ArrayList<WebOrderDetails_model> webOrders_models;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    double _price = 0, _total = 0;

    public AdapterReport_bills_mats(Context context, ArrayList<WebOrderDetails_model> webOrdersModels, FragmentManager fragmentManager) {
        this.mInflater = LayoutInflater.from(context);
        this.webOrders_models = webOrdersModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mat_sale_report_adapter, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (position > 0) {
            holder.b_first_row.setVisibility(View.GONE);
        }
        WebOrderDetails_model w = webOrders_models.get(position);
        double total = w.getPrice() * w.getQty();


        holder.b_mat_cod.setText(w.getMatCode());
        holder.b_mat_name.setText(w.getMatCode());
        holder.b_qty.setText(String.valueOf(w.getQty()));
        holder.b_price.setText(String.valueOf(round(w.getPrice(), 1)));
        holder.b_total.setText(String.valueOf(round(total, 1)));

        _price = _price + w.getPrice();
        _total = _total + total;
        Report_mat_sale._priceTXT.setText("Price"+" "+String.valueOf(round(_price,2)));
        Report_mat_sale._totalTXT.setText("Total"+" "+String.valueOf(round(_total,2)));
        Report_mat_sale._noOfRows.setText(webOrders_models.size()+" "+"Rows");

//        if (position == webOrders_models.size()-1&&webOrders_models.size()>0) {
//            WebOrderDetails_model w2 = new WebOrderDetails_model();
//            w2.setPrice(Float.valueOf(String.valueOf(_price)));
//
//            webOrders_models.add(w2);

//            if (holder.b_mat_cod.getParent() != null) {
//                ((ViewGroup) holder.b_mat_cod.getParent()).removeView(holder.b_mat_cod);
//            }
//            if (holder.b_mat_name.getParent() != null) {
//                ((ViewGroup) holder.b_mat_name.getParent()).removeView(holder.b_mat_name);
//            }
//            if (holder.b_qty.getParent() != null) {
//                ((ViewGroup) holder.b_qty.getParent()).removeView(holder.b_qty);
//            }
//            if (holder.b_price.getParent() != null) {
//                ((ViewGroup) holder.b_price.getParent()).removeView(holder.b_price);
//            }
//            if (holder.b_total.getParent() != null) {
//                ((ViewGroup) holder.b_total.getParent()).removeView(holder.b_total);
//            }
//
//            holder.b_mat_cod.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 56));
//            holder.b_mat_name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 56));
//            holder.b_qty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 56));
//            holder.b_price.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 56));
//            holder.b_total.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 56));
//
//            setMargins(holder.b_mat_cod,0,0,0,0);
//            setMargins(holder.b_mat_name,0,0,0,0);
//            setMargins(holder.b_qty,0,0,0,0);
//            setMargins(holder.b_price,0,0,0,0);
//            setMargins(holder.b_total,0,0,0,0);
//
//            holder.b_price.setText(String.valueOf(round(_price,2)));
//            holder.b_total.setText(String.valueOf(round(_total,2)));
//            holder.b_price.setTextColor(R.color.blue);
//            holder.b_total.setTextColor(R.color.blue);
//            holder.b_price.setTypeface(holder.b_price.getTypeface(), Typeface.BOLD);
//            holder.b_total.setTypeface(holder.b_total.getTypeface(), Typeface.BOLD);
//
//            if (holder.base_row_mat.getParent() != null) {
//                ((ViewGroup) holder.base_row_mat.getParent()).removeView(holder.base_row_mat);
//            }
//            if (holder.invisible_row.getParent() != null) {
//                ((ViewGroup) holder.invisible_row.getParent()).removeView(holder.invisible_row);
//            }
//
//            holder.base_row_mat.addView(holder.b_mat_cod);
//            holder.base_row_mat.addView(holder.b_mat_name);
//            holder.base_row_mat.addView(holder.b_qty);
//            holder.base_row_mat.addView(holder.b_price);
//            holder.base_row_mat.addView(holder.b_total);
//
//            holder.mat_table_reports.addView(holder.invisible_row);
//            holder.mat_table_reports.addView(holder.base_row_mat);
//        }

    }

    @Override
    public int getItemCount() {
        return webOrders_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView b_mat_cod, b_mat_name, b_qty, b_price, b_total;
        TableRow b_first_row, base_row_mat, invisible_row;
        TableLayout mat_table_reports;

        ViewHolder(View itemView) {
            super(itemView);
            b_mat_cod = itemView.findViewById(R.id.b_mat_cod);
            b_mat_name = itemView.findViewById(R.id.b_mat_name);
            b_qty = itemView.findViewById(R.id.b_qty);
            b_price = itemView.findViewById(R.id.b_price);
            b_total = itemView.findViewById(R.id.b_total);
            b_first_row = itemView.findViewById(R.id.row_header);
            base_row_mat = itemView.findViewById(R.id.base_row_mat);
            invisible_row = itemView.findViewById(R.id.invisible_row);
            mat_table_reports = itemView.findViewById(R.id.mat_reports);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

//    // convenience method for getting data at click position
//    Integer getItem(int id) {
//        return Mats.get(id);
//    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

//    public void openDialog(String mat_img_guid,String mat_name,String mat_price,String mat_note) {
//        Mat_Details mat_details = new Mat_Details();
//        Bundle args=new Bundle();
//        args.putString("mat_img_guid",mat_img_guid);
//        args.putString("mat_name",mat_name);
//        args.putString("mat_price",mat_price);
//        args.putString("mat_note",mat_note);
//        mat_details.setArguments(args);
//        /*exampleDialog.show(((FragmentActivity)context).getSupportFragmentManager()
//                ,"New Order");*/
//        mat_details.show(fragmentManager
//                ,"Mat Details");
//
//
//
//        mat_details.setCancelable(true);
//    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }



}
