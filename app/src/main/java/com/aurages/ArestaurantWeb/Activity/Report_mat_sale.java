package com.aurages.ArestaurantWeb.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.AdapterReport_bills_mats;
import com.aurages.ArestaurantWeb.Fragment.DatePickerFragment;
import com.aurages.ArestaurantWeb.Model.Category;
import com.aurages.ArestaurantWeb.Model.Groups;
import com.aurages.ArestaurantWeb.Model.Mats;
import com.aurages.ArestaurantWeb.Model.ModelCatogry;
import com.aurages.ArestaurantWeb.Model.ModelProduct;
import com.aurages.ArestaurantWeb.Model.WebOrderDetails_model;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Report_mat_sale extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static ArrayList<ModelCatogry> GroupsList = new ArrayList<>();
    public static ArrayList<ModelProduct> MatsList = new ArrayList<>();
    public static RecyclerView report_rec;
    //TextView cal_from, cal_to;
    String txt_from_to = "";
    String _date1 = "", _date2 = "";
    int bill_type;
    Toolbar toolbar;
    CardView report_filter;
    MenuItem showItem;
    String group = "", mat = "";
    ArrayList<String> mat_arr;
    ArrayAdapter<String> adapter;

    SqlLiteDataBase databaseHelper;
    Spinner dropdown_mat;
    ProgressBar progressBar;

    AppCompatButton btnDoReport;
    AdapterReport_bills_mats adapterReport_bills_mats;
    ArrayList<WebOrderDetails_model> webOrders_models = new ArrayList<>();
    private Menu menu;

    String group_guid,mat_guid;

    public static TextView _priceTXT,_totalTXT, _noOfRows;
    TableLayout mat_reports;

    TextView cal_from, cal_to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_mat_sale);

        _priceTXT = findViewById(R.id.b_price1);
        _totalTXT = findViewById(R.id.b_totatxt);

        btnDoReport = findViewById(R.id.btnDoReport_mat);
        report_rec = findViewById(R.id.recycler_bill_report);
        mat_reports = findViewById(R.id.mat_reports);
        _noOfRows =findViewById(R.id.b_mat_cod1q);

        btnDoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter=null;
                progressBar.setVisibility(View.VISIBLE);
                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(Report_mat_sale.this);
                webOrders_models = databaseHelper.Get_report_Mat_Sale(group_guid,mat_guid, _date1, _date2);
                cal_from.setText("");
                cal_to.setText("");
                if(webOrders_models.size()>0){
                    adapterReport_bills_mats = new AdapterReport_bills_mats(getApplicationContext(), webOrders_models, getSupportFragmentManager());
                    report_rec.setLayoutManager(new GridLayoutManager(Report_mat_sale.this, 1, RecyclerView.VERTICAL, false));
                    report_rec.setAdapter(adapterReport_bills_mats);
                    report_filter.setVisibility(View.GONE);
                    showItem.setIcon(ContextCompat.getDrawable(Report_mat_sale.this, R.drawable.ic_arrow_drop_down_));
                    progressBar.setVisibility(View.GONE);
                    findViewById(R.id.no_results_found).setVisibility(View.GONE);
                    report_rec.setVisibility(View.VISIBLE);
                    mat_reports.setVisibility(View.VISIBLE);
                    _date1 = "";
                    _date2 = "";
                }else{
                    mat_reports.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    findViewById(R.id.no_results_found).setVisibility(View.VISIBLE);
                    report_rec.setVisibility(View.GONE);
                    _date1 = "";
                    _date2 = "";
                }




            }
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        report_filter = findViewById(R.id.report_card_filter);

        DialogFragment datePicker = new DatePickerFragment();
        cal_from = findViewById(R.id.cal_from);
        cal_to = findViewById(R.id.cal_to);
        cal_from.setText(GetCurrentDateWhithoutTime());
        cal_to.setText(GetCurrentDateWhithoutTime());


        cal_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_from_to = "from";
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        cal_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_from_to = "to";
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        mat_arr = new ArrayList<>();
        mat_arr.add("All");
        dropdown_mat = findViewById(R.id.spinner1);

        dropdown_mat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bill_type = position;
                if (position != 0) {

                } else {
                    //mat = "";
                    mat_guid = "";
                }
                Toast.makeText(Report_mat_sale.this, "Bill type = " + mat_guid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Report_mat_sale.this, "Bill type not selected", Toast.LENGTH_SHORT).show();
            }
        });


        databaseHelper = new SqlLiteDataBase(this);
        GroupsList = databaseHelper.GetAllGroups();

        //get the spinner from the xml.
        Spinner group_spinner = findViewById(R.id.spinner2);
        ArrayList<String> group_arr = new ArrayList<>();
        group_arr.add("All");
        for (int i = 0; i < GroupsList.size(); i++) {
            group_arr.add(GroupsList.get(i).getName());
        }
        ArrayAdapter<String> adapter_group = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, group_arr);
        group_spinner.setAdapter(adapter_group);
        group_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bill_type = position;
                Toast.makeText(Report_mat_sale.this, "Bill type = " + bill_type, Toast.LENGTH_SHORT).show();

                MatsList.clear();
                if (position != 0) {
                    //MatsList = databaseHelper.GetAllMats(GroupsList.get(position-1).getGuid());
                    group = group_arr.get(position-1);
                    //group_guid = GroupsList.get(position-1).getGuid();
                } else {
                    group_guid = "";
                }


                mat_arr.clear();
                mat_arr.add("All");
                for (int i = 0; i < MatsList.size(); i++) {
                    //mat_arr.add(MatsList.get(i).getName());
                }
                adapter = new ArrayAdapter<>(Report_mat_sale.this, android.R.layout.simple_spinner_dropdown_item, mat_arr);
                dropdown_mat.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Report_mat_sale.this, "Bill type not selected", Toast.LENGTH_SHORT).show();
            }
        });


        progressBar = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bill_report, menu);

        this.menu = menu;
        showItem = menu.findItem(R.id.bill_report_menu_show);
        showItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up_drop_circle));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bill_report_menu_show: {
                if (report_filter.getVisibility() == View.VISIBLE) {
                    YoYo.with(Techniques.SlideOutUp)
                            .duration(700)
                            .playOn(report_filter);
                    report_filter.setVisibility(View.GONE);
                    showItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_down_));
                } else {
                    YoYo.with(Techniques.DropOut)
                            .duration(700)
                            .playOn(report_filter);
                    report_filter.setVisibility(View.VISIBLE);
                    showItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up_drop_circle));
                }
                break;
            }
            case R.id.bill_report_menu_del:{
                SqlLiteDataBase dataBase = new SqlLiteDataBase(getApplicationContext());
                dataBase.deleteAllTableData("WebOrderDetails");
                break;
            }
        }

        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        int month2=month+1;

        switch (txt_from_to) {
            case "from": {
                //cal_from.setText(currentDateString);
                cal_from.setText(year + "-" + month2 + "-" + dayOfMonth);
                _date1 = year + "-" + month2 + "-" + dayOfMonth;
                txt_from_to = "";
                break;
            }
            case "to": {
                //cal_to.setText(currentDateString);
                cal_to.setText(year + "-" + month2 + "-" + dayOfMonth);
                _date2 = year + "-" + month2 + "-" + dayOfMonth;
                txt_from_to = "";
                break;
            }
        }
    }


    public static String GetCurrentDateWhithoutTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        String date = sdf.format(new Date());
        return date;
    }


}
