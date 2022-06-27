package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Dialog.MatEl;
import com.aurages.ArestaurantWeb.Dialog.Mat_Details;
import com.aurages.ArestaurantWeb.Model.BranchesModel;
import com.aurages.ArestaurantWeb.Model.ModelProduct;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.temp_order;
import static com.aurages.ArestaurantWeb.Utils.URL.ROOT_image;

public class AdapterMats extends RecyclerView.Adapter<AdapterMats.ViewHolder> {

    private ArrayList<ModelProduct> Mats;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private static final String SHARED_PREF_NAME = "aurages_rest";
    public ArrayList<BranchesModel> Sluglaby;
    private Context context;

    public  int temp_id;



    public AdapterMats(Context context, ArrayList<ModelProduct> m) {
        this.mInflater = LayoutInflater.from(context);
        Mats = m;
this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.food_list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelProduct m = Mats.get(position);

        holder.MatName.setText(m.getNameAr());
        SharedPreferences sp =context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        String mat_description = m.getDescriptionAr();
        String image=m.getImage();

        edit.putString("desc",mat_description);
        edit.putString("image_mats1",image);
        edit.putInt("positions1",position);
        edit.apply();
        holder.mat_items.setText(m.getPrice());

        //holder.MatStoreCount.setText(m.getStoreCount() + "");

        if (new SqlLiteDataBase(holder.mat_items.getContext()).IsPoroductCategory(m.getId())) {
            //Toast.makeText(holder.mat_items.getContext(), ""+m.getNameAr()+" "+"\n is Group", Toast.LENGTH_SHORT).show();
        }
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);


        String uid= sp.getString("uid","");
            Sluglaby = databaseHelper.GetBranch();
            for (int i=0;i<Sluglaby.size();i++) {
                String slugbly= Sluglaby.get(i).getSlugable();

                Glide.with(context).load(ROOT_image+slugbly+ "/" + "product"+"/" + image).override(80,80).error(R.drawable.foodmenubackgroundpicture).into(holder.MatImage);

            }



        holder.MatImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDialog(m.getImage(), m.getNameAr(), m.getPrice(), m.getNote());
                return true;

            }
        });
holder.MatImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
            OrdersScreen.layout_cont1.setVisibility(View.VISIBLE);
            SqlLiteDataBase helper = new SqlLiteDataBase(holder.MatImage.getContext());
            boolean add_order = helper.InsertTo_Temp_orders(m.getId(), m.getNameAr(), m.getNameEn(), 1, m.getPrice(), m.getNote());

            if (add_order) {
                OrdersScreen.total = 0;

                temp_order.clear();

                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(holder.mat_items.getContext());

                temp_order = databaseHelper.GetAllTempOrder();
                OrdersScreen.adapterTemp = new AdapterTemp(holder.mat_items.getContext(), temp_order);


                OrdersScreen.TempOrder.setAdapter(OrdersScreen.adapterTemp);
                OrdersScreen.adapterTemp.notifyDataSetChanged();

              //  OrdersScreen.EdtTotal.setText(OrdersScreen.total+"");


            } else {
                Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
            }


        }
        catch (Exception e){

        }
    }
});


    }
    @Override
    public int getItemCount() {
        return Mats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView MatName;
        ImageView MatImage;
        TextView mat_items;

        ViewHolder(View itemView) {
            super(itemView);
            MatName = itemView.findViewById(R.id.orderItemNameTextView);
            //MatStoreCount = itemView.findViewById(R.id.StoreCount);
            MatImage=itemView.findViewById(R.id.orderItemImageView);
            mat_items = itemView.findViewById(R.id.orderItemPriceTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
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

    public void openDialog(String mat_img_guid,String mat_name,String mat_price,String mat_note) {
        Mat_Details mat_details = new Mat_Details(context,Mats);
        Bundle args=new Bundle();
        args.putString("mat_img_guid",mat_img_guid);
        args.putString("mat_name",mat_name);
        args.putString("mat_price",mat_price);
        args.putString("image_mats1",mat_img_guid);
        args.putString("mat_note",mat_note);
        mat_details.setArguments(args);
     //  mat_details .show(((FragmentActivity)context).getSupportFragmentManager()
        //        ,"New Order");
        //mat_details.setStyle(AppCompatDialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
        mat_details .show(((FragmentActivity)context).getSupportFragmentManager(),"Mat Details");

//                ,"Mat Details");



        mat_details.setCancelable(true);
    }

    public void openDialogMatE(String mat_guid) {
        MatEl matEl = new MatEl();
        Bundle args=new Bundle();
        args.putString("mat_guid",mat_guid);
        matEl.setArguments(args);

        matEl.setStyle(AppCompatDialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);

        matEl.setCancelable(true);
    }
    public String bitmapToString(Bitmap bmp) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
        byte[] bytes = os.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

}
