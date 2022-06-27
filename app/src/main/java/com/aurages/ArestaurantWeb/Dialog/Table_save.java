package com.aurages.ArestaurantWeb.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.Table_Adapter;
import com.aurages.ArestaurantWeb.Model.ModelTables;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class Table_save extends AppCompatDialogFragment {
    public static RecyclerView tables_rec, tables_place_rec;
    public static ArrayList<ModelTables> tableModels = new ArrayList<>();
    public static Table_Adapter adapterTables;
    String type;
    public Table_save(String type) {
        this.type = type;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        View view = inflater.inflate(R.layout.tables, container, false);
        tables_place_rec = view.findViewById(R.id.tables_place_rec);
        tables_rec = view.findViewById(R.id.tables_rec);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int Table_no = Integer.parseInt(prefs.getString("table_element", "4"));
        tables_rec.setLayoutManager(new GridLayoutManager(getContext(), Table_no));
        tables_place_rec.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
       // tableModels = databaseHelper.GetAllTables();
        adapterTables = new Table_Adapter(getContext(), tableModels, type,Table_save.this);
        tables_rec.setAdapter(adapterTables);

        return view;
    }


}
