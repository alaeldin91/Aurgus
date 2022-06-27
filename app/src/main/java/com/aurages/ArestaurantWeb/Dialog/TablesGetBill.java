package com.aurages.ArestaurantWeb.Dialog;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.AdapterTablePlaceGitBill;
import com.aurages.ArestaurantWeb.Adapter.AdapterTablesGitBill;
import com.aurages.ArestaurantWeb.Model.TableModel;
import com.aurages.ArestaurantWeb.Model.TablePlaseModel;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class TablesGetBill extends AppCompatDialogFragment {

    public static RecyclerView tables_rec,tables_place_rec;
    public static ArrayList<TablePlaseModel> tablePlaseModels = new ArrayList<>();
    public static ArrayList<TableModel> tableModels = new ArrayList<>();
    public static AdapterTablePlaceGitBill adapterTablePlaceGitBill;
    public static AdapterTablesGitBill adapterTablesGitBill;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        View view = inflater.inflate(R.layout.tables, container, false);
        tables_place_rec = view.findViewById(R.id.tables_place_rec);
        tables_rec = view.findViewById(R.id.tables_rec);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int Table_no =Integer.parseInt(prefs.getString("table_element", "4"));
        tables_rec.setLayoutManager(new GridLayoutManager(getContext(), Table_no));
        tables_place_rec.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));


        boolean SAVE_STATUS = prefs.getBoolean("Status", false);

        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        if(SAVE_STATUS){
          //  tablePlaseModels = SqlConnector.GetAllTablePlaces(getContext());
        }else{
            //tablePlaseModels = databaseHelper.GetAllTablePlaces();
        }



        if(tablePlaseModels.size()!=0){
            if(SAVE_STATUS){
             //   tableModels = SqlConnector.GetAllTables(tablePlaseModels.get(0).getGuid(),getContext());
            }else{
//                tableModels = databaseHelper.GetAllTables(tablePlaseModels.get(0).getGuid());
            }

            tablePlaseModels.get(0).setSelected(true);
            adapterTablePlaceGitBill = new AdapterTablePlaceGitBill(getContext(), tablePlaseModels,this);
            adapterTablesGitBill = new AdapterTablesGitBill(getContext(), tableModels,this);

            tables_place_rec.setAdapter(adapterTablePlaceGitBill);
            tables_rec.setAdapter(adapterTablesGitBill);

            tables_place_rec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false){
                @Override
                public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                    // force height of viewHolder here, this will override layout_height from xml
                    lp.width = getWidth() / 2;
                    return true;
                }
            });

        }else{
            Toast.makeText(getContext(), "Data base is empty\",\"Data base no imported, you must Sync your data..", Toast.LENGTH_SHORT).show();
            databaseHelper.InsertTo_Error("Data base is empty","Data base no imported, you must Sync your data..");
        }

        return view;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}
