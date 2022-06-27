package com.aurages.ArestaurantWeb.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aurages.ArestaurantWeb.Model.ModelPPrinter;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.angmarch.views.SpinnerTextFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String SHARED_PREF_NAME = "aurages_rest";
    public static ArrayList<ModelPPrinter> listPrinter = new ArrayList<>();
    Context context;
    ArrayAdapter<String> mMyadapter;
    ArrayAdapter<String> mMyadapter_namePrinter;
    private ArrayList<String> Name = new ArrayList();
    private ArrayList<String> Ip = new ArrayList();
    private ArrayList<String> printer = new ArrayList();
    ArrayAdapter<String> mMyadapter_connect;
    Spinner nameprinter;
    EditText ip;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sp = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(this);
        //getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //Dialog.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        setupDefaultConnect();
        setupDefaultTypePrinter();
        setupNamePrinter();
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SettingsActivity.this,OrdersScreen.class);
                startActivity(i);

            }
        });


    }

    private void setupDefaultConnect() {
        NiceSpinner spinner = findViewById(R.id.nice_spinner_connect);
        List<String> dataset = new LinkedList<>(Arrays.asList("Network", "Usb", "Bluetooth"));
        spinner.attachDataSource(dataset);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(SettingsActivity.this, "Selected: " + item, Toast.LENGTH_SHORT).show();

                String selected = parent.getItemAtPosition(position).toString();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("selected_type_connect", selected);
                editor.apply();


            }
        });
    }

    private void setupDefaultTypePrinter() {
        NiceSpinner spinner_type = findViewById(R.id.nice_spinner_type);

        List<String> dataset = new LinkedList<>(Arrays.asList("Thermal", "Bixlon", "Epson", "Hprt"));
        spinner_type.attachDataSource(dataset);
        spinner_type.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(SettingsActivity.this, "Selected: " + item, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sp.edit();
                String selected = parent.getItemAtPosition(position).toString();

                editor.putString("selected_type_print", selected);
                editor.apply();
                if (selected == "thermal printer") {
                    Log.i("alaeldinhaji", "thermal");

                }

            }
        });

    }

    public void setupNamePrinter() {
        SqlLiteDataBase dataBase = new SqlLiteDataBase(this);
        nameprinter = findViewById(R.id.spinner_name);
        ip = findViewById(R.id.ip);

        listPrinter = dataBase.GetAllPrinter();
        for (int i = 0; i < listPrinter.size(); i++) {
            Name.add(listPrinter.get(i).getName());
            Log.i("namee", "list" + Name);
            mMyadapter_namePrinter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_spinner_item, Name);

            mMyadapter_namePrinter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            nameprinter.setAdapter(mMyadapter_namePrinter);
            nameprinter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selected = parent.getItemAtPosition(position).toString();

                    String ips = listPrinter.get(position).getPrinter();
                    String printcount = listPrinter.get(position).getId();


                    Log.i("namee", "list" + ips);
                    String names_printer = listPrinter.get(position).getPrinterName();
                    Log.i("namee", "list" + selected);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("selected_name_print", selected);
                    editor.putString("ip", ips);
                    editor.putString("idprinter", "");
                    Log.i("ip", "" + ips);
                    ip.setText(ips);

                    editor.apply();


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }
    }


    @Override
    public void onClick(View v) {

    }
}