package com.aurages.ArestaurantWeb.Dialog;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.AdapterMats;
import com.aurages.ArestaurantWeb.Model.Mats;
import com.aurages.ArestaurantWeb.Model.ModelProduct;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class MatEl extends AppCompatDialogFragment {

    public static RecyclerView GridMats;
    SharedPreferences prefs;
    public static ArrayList<ModelProduct> MatsList = new ArrayList<>();
    public static AdapterMats adaptermats;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.mat_details, null);
        builder.setView(view);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Bundle args = getArguments();
        String mat_guid = args.getString("mat_guid");

        GridMats = view.findViewById(R.id.GridMats);
        int Mat_no =Integer.parseInt(prefs.getString("mat_element", "4"));
        GridMats.setLayoutManager(new GridLayoutManager(getContext(), Mat_no));
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        //MatsList = databaseHelper.GetAllMatsEl(mat_guid);

        if(MatsList.size()>0){
            adaptermats = new AdapterMats(getContext(), MatsList);
            GridMats.setAdapter(adaptermats);
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
