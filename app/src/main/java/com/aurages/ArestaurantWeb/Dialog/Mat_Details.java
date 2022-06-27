package com.aurages.ArestaurantWeb.Dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.aurages.ArestaurantWeb.Model.BranchesModel;
import com.aurages.ArestaurantWeb.Model.ModelProduct;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Utils.URL.ROOT_image;

public class Mat_Details extends AppCompatDialogFragment {

    String mat_img_guid = "";

    TextView name, price,notes;
    ImageButton close_btn;
    ImageView mat_det_img;
    private ArrayList<ModelProduct> Mats;
Context context;
    private static final String SHARED_PREF_NAME = "aurages_rest";

    public ArrayList<BranchesModel> Sluglaby;
public Mat_Details(Context context,ArrayList<ModelProduct> m){
    this.Mats=m;
    this.context=context;
}



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

      //  getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.mat_details, null);
        builder.setView(view);

        Bundle args = getArguments();
        mat_img_guid = args.getString("mat_img_guid");

        name = view.findViewById(R.id.mat_det_name);
        notes = view.findViewById(R.id.mat_det_note);
        price = view.findViewById(R.id.mat_det_price);
        mat_det_img = view.findViewById(R.id.iv_auto_image_slider);
        close_btn = view.findViewById(R.id.mat_det_close);
        SharedPreferences sp =context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        SqlLiteDataBase databaseHelper= new SqlLiteDataBase(context);
        name.setText(args.getString("mat_name"));
        String image= args.getString("image_mats1","");

        notes.setText(args.getString("mat_note"));
        price.setText(round(Double.parseDouble(args.getString("mat_price")),1)+"");
        Sluglaby = databaseHelper.GetBranch();
        for (int i=0;i<Sluglaby.size();i++) {
            String slugbly= Sluglaby.get(i).getSlugable();


                Glide.with(context).load(ROOT_image + slugbly + "/" + "product" + "/" +image
                ).override(70, 70).error(R.drawable.foodmenubackgroundpicture).into(mat_det_img);

        }


        return builder.create();
    }




    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}
