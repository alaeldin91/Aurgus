package com.aurages.ArestaurantWeb.Dialog;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Adapter.AdapterOptions;
import com.aurages.ArestaurantWeb.Adapter.AdapterTemp;
import com.aurages.ArestaurantWeb.Model.OrderOptions;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.temp_order;

public class TempEdit extends AppCompatDialogFragment implements
        android.view.View.OnClickListener {
    public static TextView name, qty, price;
    public static EditText notes;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.temp_edit, null);
        builder.setView(view);
        return builder.create();
    }


    @Override
    public void onClick(View v) {

    }
}