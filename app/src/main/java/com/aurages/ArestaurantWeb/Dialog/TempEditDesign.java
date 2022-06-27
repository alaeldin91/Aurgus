package com.aurages.ArestaurantWeb.Dialog;


import android.app.Dialog;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.AdapterOptions;
import com.aurages.ArestaurantWeb.Model.OrderOptions;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import java.util.ArrayList;
public class TempEditDesign extends AppCompatDialogFragment {



 public static    String temp_id = "";
    CardView del_card;
    public static EditText notetempedtdesign;
    Temp_Order temp_order_DB = new Temp_Order();
    RecyclerView order_option_recycler;
    ArrayList<OrderOptions> orderOptions = new ArrayList<>();
    AdapterOptions adapterOptions;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.temp_edit_design, null);
        builder.setView(view);


        Bundle args = getArguments();
        temp_id = args.getString("temp_id");
Toast.makeText(getContext(),temp_id,Toast.LENGTH_LONG).show();
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        temp_order_DB = databaseHelper.GetRowTempOrder(temp_id);

        del_card = view.findViewById(R.id.del_card_design);
        notetempedtdesign = view.findViewById(R.id.notetempedtdesign);
        del_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notetempedtdesign.setText("");
            }
        });

        notetempedtdesign.setText(temp_order_DB.getNotes());

        order_option_recycler = view.findViewById(R.id.order_option_recycler);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        order_option_recycler.addItemDecoration(dividerItemDecoration);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int Note_no = Integer.parseInt(prefs.getString("note_element", "1"));
        order_option_recycler.setLayoutManager(new GridLayoutManager(getContext(), Note_no, RecyclerView.HORIZONTAL, false));
        orderOptions = databaseHelper.OrderOptions(temp_id);
        adapterOptions = new AdapterOptions(getContext(), orderOptions);
        order_option_recycler.setAdapter(adapterOptions);


        RadioEvent(view);



        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        radioValue=1;
    }

    public static int radioValue =1;

    public static void RadioEvent(View view) {

        RadioGroup radioGroup;
        radioGroup = view.findViewById(R.id.temp_edit_radio2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_mins2:
                        radioValue = 0;
                        break;
                    case R.id.radio_plus2:
                        radioValue = 1;
                        break;
                    case R.id.radio_plus_plus2:
                        radioValue = 2;
                        break;
                }
            }
        });

    }


}
