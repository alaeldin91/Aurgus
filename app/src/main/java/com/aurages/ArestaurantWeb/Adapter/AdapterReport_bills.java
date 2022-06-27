package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.Report_Bills;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.Utils;

import java.util.ArrayList;

public class AdapterReport_bills extends RecyclerView.Adapter<AdapterReport_bills.ViewHolder> {
    private ArrayList<WebOrders_model> webOrders_models;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    private Context context;
    double _total=0,_discount=0, _tax=0, _end_total=0;
    public AdapterReport_bills(Context context, ArrayList<WebOrders_model> webOrdersModels, FragmentManager fragmentManager) {
        this.mInflater = LayoutInflater.from(context);
        this.webOrders_models = webOrdersModels;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.bill_report_adapter, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (webOrders_models.size() == 0) {
            Toast.makeText(context, "The array is empty", Toast.LENGTH_SHORT).show();
        }

        if(position>0){
            holder.tableRow.setVisibility(View.GONE);
        }




        WebOrders_model w = webOrders_models.get(position);

        holder.b_no.setText(String.valueOf(w.getNumber()));
        holder.b_d_no.setText(String.valueOf(w.getDailyNumber()));
        //holder.b_date.setText(w.getDate());
        holder.b_date.setText(w.getDate());
        holder.b_table_code.setText(w.getTableCode());

        holder.b_total.setText(String.valueOf(w.getTotal()));
        holder.b_discount.setText(String.valueOf(w.getDiscount()));
        holder.b_tax.setText(String.valueOf(w.getTax()));

        double total = w.getTotal()-w.getDiscount()+w.getTax();

        //holder.b_end_total.setText(String.valueOf(w.getTableCode()));
        holder.b_end_total.setText(String.valueOf(round(total,2)));

        _total=_total+w.getTotal();
        _discount=_discount+w.getDiscount();
        _tax=_tax+w.getTax();
        _end_total=_end_total+total;

        Report_Bills._totalTXT.setText("Total"+" "+String.valueOf(round(_total,2)));
        Report_Bills._discountTXT.setText("Discount"+" "+String.valueOf(round(_discount,2)));
        Report_Bills._txTXT.setText("Tax"+" "+String.valueOf(round(_tax,2)));
        Report_Bills._endtotalTXT.setText("End Total"+" "+String.valueOf(round(_end_total,2)));
        Report_Bills._no_of_row.setText(webOrders_models.size()+" "+"Rows");


//        if(holder.b_no.getParent()!=null){
//            ((ViewGroup)holder.b_no.getParent()).removeView(holder.b_no);
//        }
//        if(holder.b_d_no.getParent()!=null){
//            ((ViewGroup)holder.b_d_no.getParent()).removeView(holder.b_d_no);
//        }
//        if(holder.b_date.getParent()!=null){
//            ((ViewGroup)holder.b_date.getParent()).removeView(holder.b_date);
//        }
//        if(holder.b_table_code.getParent()!=null){
//            ((ViewGroup)holder.b_table_code.getParent()).removeView(holder.b_table_code);
//        }
//        if(holder.b_total.getParent()!=null){
//            ((ViewGroup)holder.b_total.getParent()).removeView(holder.b_total);
//        }
//        if(holder.b_discount.getParent()!=null){
//            ((ViewGroup)holder.b_discount.getParent()).removeView(holder.b_discount);
//        }
//        if(holder.b_tax.getParent()!=null){
//            ((ViewGroup)holder.b_tax.getParent()).removeView(holder.b_tax);
//        }
//        if(holder.b_end_total.getParent()!=null){
//            ((ViewGroup)holder.b_end_total.getParent()).removeView(holder.b_end_total);
//        }
//        holder.tableRow.addView(holder.b_no);
//        holder.tableRow.addView(holder.b_d_no);
//        holder.tableRow.addView(holder.b_date);
//        holder.tableRow.addView(holder.b_table_code);
//        holder.tableRow.addView(holder.b_total);
//        holder.tableRow.addView(holder.b_discount);
//        holder.tableRow.addView(holder.b_tax);
//        holder.tableRow.addView(holder.b_end_total);
//
//        holder.bill_table_layout.addView(holder.tableRow);


    }

    @Override
    public int getItemCount() {
        return webOrders_models.size();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

//    // convenience method for getting data at click position
//    Integer getItem(int id) {
//        return Mats.get(id);
//    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView b_no, b_d_no, b_date, b_table_code, b_total, b_discount,
                b_tax, b_end_total;
        TableRow b_first_row,tableRow;
        TableLayout bill_table_layout;


        ViewHolder(View itemView) {
            super(itemView);
            b_no = itemView.findViewById(R.id.b_no);
            b_d_no = itemView.findViewById(R.id.b_d_no);
            b_date = itemView.findViewById(R.id.b_date);
            b_table_code = itemView.findViewById(R.id.b_table_code);

            b_total = itemView.findViewById(R.id.b_total);
            b_discount = itemView.findViewById(R.id.b_discount);
            b_tax = itemView.findViewById(R.id.b_tax);
            b_end_total = itemView.findViewById(R.id.b_end_total);


            tableRow =  itemView.findViewById(R.id.table_row1);
            /* Create a Button to be the row-content. */
            bill_table_layout = itemView.findViewById(R.id.bill_table_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }



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
