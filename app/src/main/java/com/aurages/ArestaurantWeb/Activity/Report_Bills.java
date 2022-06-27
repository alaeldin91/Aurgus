package com.aurages.ArestaurantWeb.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
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

import com.aurages.ArestaurantWeb.Adapter.AdapterReport_bills;
import com.aurages.ArestaurantWeb.Fragment.DatePickerFragment;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Report_Bills extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView cal_from, cal_to;
    String _date1 = "", _date2 = "";
    String txt_from_to = "";
    String bill_type;
    Toolbar toolbar;
    CardView report_filter;
    AppCompatButton btnDoReport;
    AdapterReport_bills adapterReport_bills;
    ArrayList<WebOrders_model> webOrders_models = new ArrayList<>();
    public static RecyclerView report_rec;
    ProgressBar progressBar;
    TableLayout bill_table_layout;
    public static TextView _totalTXT, _discountTXT, _txTXT, _endtotalTXT,_no_of_row;

    private WebView billWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_bills);
        bill_table_layout = findViewById(R.id.bill_table_layout);
        _totalTXT = findViewById(R.id.b_total1);
        _discountTXT = findViewById(R.id.b_discount1);
        _txTXT = findViewById(R.id.b_tax1);
        _endtotalTXT = findViewById(R.id.b_end_total1);
        _no_of_row = findViewById(R.id.b_no1);
        billWebView = findViewById(R.id.bill_webView);

        btnDoReport = findViewById(R.id.btnDoReport);
        report_rec = findViewById(R.id.recycler_bill_report);
        btnDoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(Report_Bills.this);
                cal_from.setText("");
                cal_to.setText("");
                webOrders_models = databaseHelper.Get_report_bills(bill_type, _date1, _date2);
                if (webOrders_models.size() > 0) {
                    bill_table_layout.setVisibility(View.VISIBLE);
                    adapterReport_bills = new AdapterReport_bills(getApplicationContext(), webOrders_models, getSupportFragmentManager());
                    report_rec.setLayoutManager(new GridLayoutManager(Report_Bills.this, 1, RecyclerView.VERTICAL, false));
                    report_rec.setAdapter(adapterReport_bills);
                    report_filter.setVisibility(View.GONE);
                    showItem.setIcon(ContextCompat.getDrawable(Report_Bills.this, R.drawable.ic_arrow_drop_down_));
                    findViewById(R.id.no_results_found).setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    report_rec.setVisibility(View.VISIBLE);
                    _date1 = "";
                    _date2 = "";
                } else {
                    bill_table_layout.setVisibility(View.GONE);
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


        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"All", "0 Bill type", "1 Bill type"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    bill_type = "";
                } else {
                    bill_type = String.valueOf(position - 1);
                }

                Toast.makeText(Report_Bills.this, "Bill type = " + bill_type, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Report_Bills.this, "Bill type not selected", Toast.LENGTH_SHORT).show();
            }
        });


        progressBar = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.GONE);


    }


    private Menu menu;
    MenuItem showItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bill_reporting, menu);

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
                            .duration(1500)
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
            case R.id.bill_report_menu_del: {
                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(Report_Bills.this);
                databaseHelper.deleteAllTableData("WebOrders");
                break;
            }
            case R.id.bill_report_menu_pdf: {
              //createWebPrintJob(billWebView);
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


    //private void createWebPrintJob(WebView webView) {
     //   String jobName = getString(R.string.app_name) + " Document";
       // PrintAttributes attributes = new PrintAttributes.Builder()
         //       .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
           //     .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
             //   .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
        //File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
       // PdfPrint pdfPrint = new PdfPrint(attributes);
       // pdfPrint.print(webView.createPrintDocumentAdapter(jobName), path, "output_" + System.currentTimeMillis() + ".pdf");
    }




