package com.aurages.ArestaurantWeb.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aurages.ArestaurantWeb.Dialog.close_Dialoge;
import com.aurages.ArestaurantWeb.Model.BranchesModel;
import com.aurages.ArestaurantWeb.Model.ModelBill;
import com.aurages.ArestaurantWeb.Model.ModelCatogry;
import com.aurages.ArestaurantWeb.Model.ModelIngradient;
import com.aurages.ArestaurantWeb.Model.ModelMenu;
import com.aurages.ArestaurantWeb.Model.ModelModifiors;
import com.aurages.ArestaurantWeb.Model.ModelPPrinter;
import com.aurages.ArestaurantWeb.Model.ModelProduct;
import com.aurages.ArestaurantWeb.Model.ModelTables;
import com.aurages.ArestaurantWeb.Model.Model_Floor;
import com.aurages.ArestaurantWeb.Model.Model_PaymentType;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.Global;
import com.aurages.ArestaurantWeb.Utils.SharePref;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.aurages.ArestaurantWeb.Utils.URL;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiClient;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiInterface;
import com.tapadoo.alerter.Alerter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "aurages_rest";
    public static String token = "token";
    public String sluglaby = "";
    Toolbar toolbar;
    Button btn_sync, btn_later;
    TextView auth_user_data;
    SqlLiteDataBase databaseHelper;
    SharePref pref;
    TextView txt_sync;
    private ProgressDialog progressBar;
    private LinearLayout progressLay;
    private int progressBarStatus = 0;
    private long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        //Toolbar
        toolbar = findViewById(R.id.public_toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        token = "Bearer " + i.getStringExtra("token");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        progressLay = findViewById(R.id.progressLay);

        databaseHelper = new SqlLiteDataBase(getApplicationContext());

        btn_sync = findViewById(R.id.sync_ac_btn);
        btn_later = findViewById(R.id.sync_ac_notNow);
        TextView txt_sync = (TextView) findViewById(R.id.sync_ac_txt);
        if(databaseHelper.isTableExists(true)){
            btn_later.setVisibility(View.VISIBLE);
        }
       else {
            btn_later.setVisibility(View.INVISIBLE);
        }
        btn_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                progressBar = new ProgressDialog(SyncActivity.this);
                progressBar.setCancelable(true);
                progressBar.setMessage(getResources().getString(R.string.synchronizing));
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);
                progressBar.setMax(200);
                //progressBar.show();
                progressBar.setIndeterminate(true);
                progressBarStatus = 0;
                fileSize = 0;


                GET_BRANCH();
                GET_FLOOR();
                GET_categories();
                GET_Products();
                GET_MENU();
                GET_Ingredients();
                GET_Modifires();
                GET_Tables();
                GET_TypePayment();
                GET_Printer();
                GETBill();
                if(isFinishing())
                {
                    startActivity(new Intent(getApplicationContext(),PinCodeAc.class));
                }



            }
        });
        btn_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PinCodeAc.class));
            }
        });


        int x = 123;


    }

//    public void SyncGo(View view) {
//        int id = view.getId();
//        switch (id){
//            case R.id.sync_ac_btn:{
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                break;
//            }
//            case R.id.sync_ac_notNow:{
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                Toast.makeText(this, "Later", Toast.LENGTH_SHORT).show();
//                break;
//            }
//
//        }
//    }

    private void GET_BRANCH() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
//

                    //Global.openDataBase();

                    ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);


                    Log.i("TAG", token);
                    Call<BranchesModel> call = apiInterface.getbranches(token);
                    call.enqueue(new Callback<BranchesModel>() {
                        @Override
                        public void onResponse(Call<BranchesModel> call, Response<BranchesModel> response) {
                            try {
                                try {
                                    if (!response.equals("")) {

                                        databaseHelper.deleteAllTableData("branches");
                                        BranchesModel json = response.body();
                                        if (databaseHelper.InsertBranch(
                                                json.getId(),
                                                json.getName(),
                                                json.getSlugable(),
                                                json.getDeliveryPrice(),
                                                json.getAddressAddress(),
                                                json.getTax(),
                                                json.getPhone(),
                                                json.getAddByUserId(),
                                                json.getCreatedAt(),
                                                json.getUpdatedAt()


                                        )) {

                                            Alerter.create(SyncActivity.this)
                                                    .setTitle(getResources().getString(R.string.branchsuccess))
                                                    .setText(getResources().getString(R.string.branchinsert))
                                                    .setBackgroundColorRes(
                                                            R.color.orange)
                                                    .setDuration(2000).show();

                                            SharedPreferences sp = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, getApplicationContext().MODE_PRIVATE);
                                            SharedPreferences.Editor edit = sp.edit();
                                            edit.putString("branch_id", json.getId());
                                            edit.apply();

                                        } else {
                                            Alerter.create(SyncActivity.this)
                                                    .setTitle(getResources().getString(R.string.brancherror))
                                                    .setText(getResources().getString(R.string.branchinserterror))

                                                    .setBackgroundColorRes(
                                                            R.color.orange)
                                                    .setDuration(2000).show();
                                        }
                                        Alerter.create(SyncActivity.this)
                                                .setTitle(getResources().getString(R.string.branchdone))
                                                .setText(getResources().getString(R.string.branchinsertsuccess))

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();


                                    } else {
                                        progressBar.dismiss();
                                        Alerter.create(SyncActivity.this)
                                                .setTitle(getResources().getString(R.string.branchnodata))
                                                .setText(getResources().getString(R.string.branchnodata))

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                    }
                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    Alerter.create(SyncActivity.this)
                                            .setTitle(getResources().getString(R.string.brancherror))
                                            .setText(getResources().getString(R.string.brancherror))

                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();
                                }
                            } finally {
//                                        Global.SqlLitDb.endTransaction();
                                progressBar.dismiss();
                            }
                        }


                        @Override
                        public void onFailure(Call<BranchesModel> call, Throwable t) {
                            progressBar.dismiss();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            t.printStackTrace();

                        }
                    });
//                    Global.SqlLitDb.beginTransaction();
                }
            } else {
                Alerter.create(SyncActivity.this)
                        .setTitle(getResources().getString(R.string.network))
                        .setText(getResources().getString(R.string.networkerror))

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();
            }

        } else {
            Alerter.create(SyncActivity.this)
                    .setTitle(getResources().getString(R.string.nointernet))
                    .setText(getResources().getString(R.string.nointernetaccess))

                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(2000).show();
        }
    }

    private void GET_FLOOR() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
                    progressBar.show();
                    Global.openDataBase();
                    ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    Call<Model_Floor> call = apiInterface.getFloor(token);
                    call.enqueue(new Callback<Model_Floor>() {
                        @Override
                        public void onResponse(Call<Model_Floor> call, Response<Model_Floor> response) {
                            try {
                                try {
                                    if (!response.equals("")) {

                                        databaseHelper.deleteAllTableData("floors");
                                        Model_Floor json = response.body();
                                        if (databaseHelper.InsertFloors(
                                                json.getId(),
                                                json.getName(),
                                                String.valueOf(json.getDescription()),
                                                json.getMenuId(),
                                                json.getBranchId(),
                                                json.getAddByUserId(),
                                                json.getCreatedAt(),
                                                json.getUpdatedAt()

                                        )) {
                                            Alerter.create(SyncActivity.this)
                                                    .setTitle("floor success")
                                                    .setText("floor insert success")

                                                    .setBackgroundColorRes(
                                                            R.color.orange)
                                                    .setDuration(2000).show();

                                        } else {
                                            Alerter.create(SyncActivity.this)
                                                    .setTitle("floor error")
                                                    .setText("floor insert error")

                                                    .setBackgroundColorRes(
                                                            R.color.orange)
                                                    .setDuration(2000).show();
                                        }
                                        Alerter.create(SyncActivity.this)
                                                .setTitle("floor done")
                                                .setText("floor insert success")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();
                                    } else {
                                        progressBar.dismiss();
                                        Alerter.create(SyncActivity.this)
                                                .setTitle("floor no data")
                                                .setText("floor no data array")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                    }


                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    //mProgressBar.setVisibility(View.GONE);
                                    Alerter.create(SyncActivity.this)
                                            .setTitle("floor error")
                                            .setText("floor insert already")

                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();
                                }
                            } finally {
//                                        Global.SqlLitDb.endTransaction();
                                progressBar.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<Model_Floor> call, Throwable t) {
                            progressBar.dismiss();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });

                }
            } else {
                Alerter.create(SyncActivity.this)
                        .setTitle("network")
                        .setText("network error")

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();

            }
        } else {
            Alerter.create(SyncActivity.this)
                    .setTitle("no internet")
                    .setText("no internet access")

                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(2000).show();
        }

    }

    private void GET_MENU() {

        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
                    progressBar.show();
                    Global.openDataBase();
                    ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    Call<ModelMenu> call = apiInterface.getMenus(token);

                    call.enqueue(new Callback<ModelMenu>() {
                        @Override
                        public void onResponse(Call<ModelMenu> call, Response<ModelMenu> response) {
                            try {
                                try {
                                    if (!response.equals("")) {
                                        databaseHelper.deleteAllTableData("menus");
                                        ModelMenu json = response.body();
                                        if (databaseHelper.InsertMenus(
                                                json.getId(),
                                                json.getName(),
                                                String.valueOf(json.getDescription()),
                                                json.getAddByUserId(),
                                                json.getCreatedAt(),
                                                json.getUpdatedAt()


                                        )) {
                                            Alerter.create(SyncActivity.this)
                                                    .setTitle("menu success")
                                                    .setText("menu insert success")

                                                    .setBackgroundColorRes(
                                                            R.color.orange)
                                                    .setDuration(2000).show();

                                        } else {
                                            Alerter.create(SyncActivity.this)
                                                    .setTitle("menu error")
                                                    .setText("menu insert error")

                                                    .setBackgroundColorRes(
                                                            R.color.orange)
                                                    .setDuration(1000).show();

                                        }
                                        Alerter.create(SyncActivity.this)
                                                .setTitle(" menu done")
                                                .setText("menu insert success")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();


                                    } else {
                                        progressBar.dismiss();
                                        Alerter.create(SyncActivity.this)
                                                .setTitle("menu no data")
                                                .setText("menu no data array")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                    }


                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    //mProgressBar.setVisibility(View.GONE);
                                    Alerter.create(SyncActivity.this)
                                            .setTitle("menu error")
                                            .setText("menu insert already")

                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();
                                }
                            } finally {
//                                        Global.SqlLitDb.endTransaction();
                                progressBar.dismiss();
                            }

                        }


                        @Override
                        public void onFailure(Call<ModelMenu> call, Throwable t) {
                            progressBar.dismiss();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                }
            } else {
                Alerter.create(SyncActivity.this)
                        .setTitle("network")
                        .setText("network error")

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();


            }
        } else {
            Alerter.create(SyncActivity.this)
                    .setTitle("no internet")
                    .setText("no internet access")

                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(2000).show();
        }
    }

    private void GET_categories() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
                    progressBar.show();
                    Global.openDataBase();
                    ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    Call<List<ModelCatogry>> call = apiInterface.getCategory(token);
                    call.enqueue(new Callback<List<ModelCatogry>>() {
                        @Override
                        public void onResponse(Call<List<ModelCatogry>> call, Response<List<ModelCatogry>> response) {
                            try {
                                try {
                                    if (!response.equals("")) {
                                        databaseHelper.deleteAllTableData("categories");

                                        for (int i = 0; i < response.body().size(); i++) {

                                            if (databaseHelper.InsertCategories(
                                                    response.body().get(i).getId(),
                                                    response.body().get(i).getName(),
                                                    response.body().get(i).getSku(),
                                                    response.body().get(i).getTimedEventFrom(),
                                                    response.body().get(i).getTimedEventTo(),
                                                    response.body().get(i).getActive(),
                                                    response.body().get(i).getCatId(),
                                                           response.body().get(i).getImage(),
                                                    response.body().get(i).getAddByUserId(),
                                                    response.body().get(i).getCreatedAt(),
                                                    response.body().get(i).getUpdatedAt(),
                                                    response.body().get(i).getCode(),
                                                    response.body().get(i).getPivot().getMenuId(),
                                                    response.body().get(i).getPivot().getCategoryId()

                                            )) {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("catogries success")
                                                        .setText("catogries insert success")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            } else {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("catogries error")
                                                        .setText("catogries insert error")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();

                                            }
                                        }
                                        Alerter.create(SyncActivity.this)
                                                .setTitle(" catogries done")
                                                .setText("catogries insert success")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();
                                    } else {
                                        progressBar.dismiss();
                                        Alerter.create(SyncActivity.this)
                                                .setTitle("catogries no data")
                                                .setText("catogries no data array")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                    }


                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    //mProgressBar.setVisibility(View.GONE);
                                    Alerter.create(SyncActivity.this)
                                            .setTitle("catogries error")
                                            .setText("catogry insert already")

                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();
                                }
                            } finally {
//                                        Global.SqlLitDb.endTransaction();
                                progressBar.dismiss();
                            }
                        }


                        @Override
                        public void onFailure(Call<List<ModelCatogry>> call, Throwable t) {
                            progressBar.dismiss();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            t.printStackTrace();
                        }
                    });

                }
            } else {
                Alerter.create(SyncActivity.this)
                        .setTitle("network")
                        .setText("network error")

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();
            }

        } else {
            Alerter.create(SyncActivity.this)
                    .setTitle("no internet")
                    .setText("no internet access")

                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(2000).show();
        }
    }

    private void GET_Printer() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
                    progressBar.show();
                    Global.openDataBase();
                    ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    Call<List<ModelPPrinter>> call = apiInterface.getprinter(token);
                    call.enqueue(new Callback<List<ModelPPrinter>>() {
                        @Override
                        public void onResponse(Call<List<ModelPPrinter>> call, Response<List<ModelPPrinter>> response) {
                            try {
                                try {
                                    if (!response.equals("")) {
                                        databaseHelper.deleteAllTableData("Printers");
                                        for (int i = 0; i < response.body().size(); i++) {
                                            if (databaseHelper.InsertPrinters(
                                                    response.body().get(i).getId(),
                                                    response.body().get(i).getName(),
                                                    response.body().get(i).getEnName(),
                                                    response.body().get(i).getPrinter(),
                                                    response.body().get(i).getPrinterName(),
                                                    response.body().get(i).getPrinterIndex(),
                                                    response.body().get(i).getCopiesNumber(),
                                                    response.body().get(i).getNote(),
                                                    response.body().get(i).getBranchId(),
                                                    response.body().get(i).getAddByUserId(),
                                                    response.body().get(i).getCreatedAt(),
                                                    response.body().get(i).getUpdatedAt()


                                            )) {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("printer success")
                                                        .setText("printer insert success")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();

                                            } else {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("printer error")
                                                        .setText("printer insert error")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            }
                                        }
                                        Alerter.create(SyncActivity.this)
                                                .setTitle(" printer done")
                                                .setText("printer insert success")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();
                                        progressBar.dismiss();
                                    } else {

                                        progressBar.dismiss();
                                        Alerter.create(SyncActivity.this)
                                                .setTitle("printer no data")
                                                .setText("printer no data array")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                    }
                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    //mProgressBar.setVisibility(View.GONE);
                                    Alerter.create(SyncActivity.this)
                                            .setTitle("printer error")
                                            .setText("printer insert already")

                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();
                                }
                            } finally {
//                                        Global.SqlLitDb.endTransaction();
                                progressBar.dismiss();
                            }


                        }

                        @Override
                        public void onFailure(Call<List<ModelPPrinter>> call, Throwable t) {

                        }
                    });


                }
            } else {
                Alerter.create(SyncActivity.this)
                        .setTitle("network")
                        .setText("network error")

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();
            }


        } else {
            Alerter.create(SyncActivity.this)
                    .setTitle("no internet")
                    .setText("no internet access")
                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(2000).show();
        }

    }
    private void GET_TypePayment() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
                    progressBar.show();
                    Global.openDataBase();
                    ApiInterface apiInterface =ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    Call<List<Model_PaymentType>> call=apiInterface.getAllPayment(token);
                    call.enqueue(new Callback<List<Model_PaymentType>>() {
                        @Override
                        public void onResponse(Call<List<Model_PaymentType>> call, Response<List<Model_PaymentType>> response) {
                            try {

                                try {

                                    if (!response.equals("")) {
                                        databaseHelper.deleteAllTableData("Payment");
                                        for (int i = 0; i < response.body().size(); i++) {

                                            if (databaseHelper.InsertPayment(
                                                    response.body().get(i).getId(),
                                                    response.body().get(i).getName()
                                                    ,
                                                    response.body().get(i).getNameEn(),
                                                    response.body().get(i).getValue(),
                                                    response.body().get(i).getType(),
                                                    response.body().get(i).getDefault(),
                                                    response.body().get(i).getNote(),
                                                    response.body().get(i).getAddByUserId(),
                                                    response.body().get(i).getCreatedAt(),
                                                    response.body().get(i).getUpdatedAt()
                                            )) {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("payment success")
                                                        .setText("payment insert success")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            } else {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("payment error")
                                                        .setText("payment insert error")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            }

                                        }
                                        Alerter.create(SyncActivity.this)
                                                .setTitle(" payment done")
                                                .setText("payment insert success")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();
                                        progressBar.dismiss();

                                    } else {

                                        progressBar.dismiss();
                                        Alerter.create(SyncActivity.this)
                                                .setTitle("payment no data")
                                                .setText("payment no data array")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                    }


                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    //mProgressBar.setVisibility(View.GONE);
                                    Alerter.create(SyncActivity.this)
                                            .setTitle("payment error")
                                            .setText("payment insert already")

                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();
                                }
                            } finally {
//                                        Global.SqlLitDb.endTransaction();
                                progressBar.dismiss();
                            }

                        }


                        @Override
                        public void onFailure(Call<List<Model_PaymentType>> call, Throwable t) {

                        }
                    });

                }
            }

            else {
                Alerter.create(SyncActivity.this)
                        .setTitle("network")
                        .setText("network error")

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();
            }
        } else {
            Alerter.create(SyncActivity.this)
                    .setTitle("no internet")
                    .setText("no internet access")

                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(2000).show();

        }
    }
    private void GETBill() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
                    progressBar.show();
                    Global.openDataBase();
                    ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    Call<List<ModelBill>> call=apiInterface.getAllBill(token);
                    call.enqueue(new Callback<List<ModelBill>>() {
                        @Override
                        public void onResponse(Call<List<ModelBill>> call, Response<List<ModelBill>> response) {

                          try {
                              try {


                                  if (!response.equals("")) {
                                      databaseHelper.deleteAllTableData("Bill");
                                      for (int i = 0; i < response.body().size(); i++) {

                                          if (databaseHelper.InsertBill(response.body().get(i).getId(),
                                                  response.body().get(i).getBillKindNumber(),
                                                  response.body().get(i).getBillKindName(),
                                                  response.body().get(i).getBillKindNameEnglish(),
                                                  response.body().get(i).getAddByUserId(),
                                                  response.body().get(i).getCreatedAt(),
                                                  response.body().get(i).getUpdatedAt())) {

                                              Alerter.create(SyncActivity.this)
                                                      .setTitle("Bill success")
                                                      .setText("Bill insert success")

                                                      .setBackgroundColorRes(
                                                              R.color.orange)
                                                      .setDuration(2000).show();

                                          } else {
                                              Alerter.create(SyncActivity.this)
                                                      .setTitle("Bill error")
                                                      .setText("Bill insert error")

                                                      .setBackgroundColorRes(
                                                              R.color.orange)
                                                      .setDuration(2000).show();
                                          }
                                          progressBar.dismiss();
                                          Alerter.create(SyncActivity.this)
                                                  .setTitle("Bill no data")
                                                  .setText("Bill no data array")

                                                  .setBackgroundColorRes(
                                                          R.color.orange)
                                                  .setDuration(2000).show();


                                      }
                                      Alerter.create(SyncActivity.this)
                                              .setTitle(" Bill done")
                                              .setText("Bill insert success")

                                              .setBackgroundColorRes(
                                                      R.color.orange)
                                              .setDuration(2000).show();
                                      progressBar.dismiss();
                                      progressBar.dismiss();


                                  } else {

                                      progressBar.dismiss();
                                      Alerter.create(SyncActivity.this)
                                              .setTitle("Bill no data")
                                              .setText("Bill no data array")

                                              .setBackgroundColorRes(
                                                      R.color.orange)
                                              .setDuration(2000).show();

                                  }
                              } catch (Exception e) {
                                  progressBar.dismiss();
                                  //mProgressBar.setVisibility(View.GONE);
                                  Alerter.create(SyncActivity.this)
                                          .setTitle("Bill error")
                                          .setText("Bill insert already")

                                          .setBackgroundColorRes(
                                                  R.color.orange)
                                          .setDuration(5000).show();
                              }
                          }
                          finally {
//                                        Global.SqlLitDb.endTransaction();
                              progressBar.dismiss();
                          }
                        }

                        @Override
                        public void onFailure(Call<List<ModelBill>> call, Throwable t) {

                        }
                    });

                }
            }
            else {
                Alerter.create(SyncActivity.this)
                        .setTitle("network")
                        .setText("network error")

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();
            }


        }
        else {
            Alerter.create(SyncActivity.this)
                    .setTitle("no internet")
                    .setText("no internet access")

                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(2000).show();

        }

    }

    private void GET_Products() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
                    progressBar.show();
//                    Global.SqlLitDb.beginTransaction();
                    Global.openDataBase();
                    ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    Call<List<ModelProduct>> call = apiInterface.getProduct(token);
                    call.enqueue(new Callback<List<ModelProduct>>() {
                        @Override
                        public void onResponse(Call<List<ModelProduct>> call, Response<List<ModelProduct>> response) {

                            try {
                                try {
                                    if (!response.equals("")) {
                                        databaseHelper.deleteAllTableData("products");

                                        for (int i = 0; i < response.body().size(); i++) {

                                            if (databaseHelper.InsertProducts(
                                                    response.body().get(i).getId(),
                                                    response.body().get(i).getNameAr(),
                                                    response.body().get(i).getDescriptionAr(),
                                                    response.body().get(i).getNameEn(),
                                                    response.body().get(i).getDescriptionEn(),
                                                    response.body().get(i).getSku(),
                                                    response.body().get(i).getPrice(),
                                                    response.body().get(i).getSellType(),
                                                    response.body().get(i).getTax(),
                                                    response.body().get(i).getTimedEventFrom(),
                                                    response.body().get(i).getTimedEventTo(),
                                                    response.body().get(i).getActive(),
                                                   response.body().get(i).getImage(),
                                                    response.body().get(i).getPrinterId(),
                                                    response.body().get(i).getClassId(),
                                                    response.body().get(i).getCategoryId(),
                                                    response.body().get(i).getAddByUserId(),
                                                    response.body().get(i).getCreatedAt(),
                                                    response.body().get(i).getUpdatedAt(),
                                                    response.body().get(i).getCode()


                                            )) {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("product success")
                                                        .setText("product insert success")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            } else {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("product error")
                                                        .setText("product insert error")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            }
                                        }
                                        Alerter.create(SyncActivity.this)
                                                .setTitle(" product done")
                                                .setText("product insert success")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();
                                        progressBar.dismiss();

                                    } else {

                                        progressBar.dismiss();
                                        Alerter.create(SyncActivity.this)
                                                .setTitle("product no data")
                                                .setText("product no data array")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                    }
                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    //mProgressBar.setVisibility(View.GONE);
                                    Alerter.create(SyncActivity.this)
                                            .setTitle("product error")
                                            .setText("product insert already")

                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();
                                }
                            } finally {
//                                        Global.SqlLitDb.endTransaction();
                                progressBar.dismiss();
                            }

                        }


                        @Override
                        public void onFailure(Call<List<ModelProduct>> call, Throwable t) {

                        }
                    });

//               }
                }
            } else {
                Alerter.create(SyncActivity.this)
                        .setTitle("network")
                        .setText("network error")

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();
            }
        } else {
            Alerter.create(SyncActivity.this)
                    .setTitle("no internet")
                    .setText("no internet access")

                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(2000).show();
        }
    }

    private void GET_Ingredients() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
                    progressBar.show();
//                    Global.SqlLitDb.beginTransaction();
                    Global.openDataBase();
                    ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    Call<List<ModelIngradient>> call = apiInterface.getGradient(token);
                    call.enqueue(new Callback<List<ModelIngradient>>() {
                        @Override
                        public void onResponse(Call<List<ModelIngradient>> call, Response<List<ModelIngradient>> response) {
                            try {
                                try {

                                    if (!response.equals("")) {
                                        databaseHelper.deleteAllTableData("ingredients");
                                        for (int i = 0; i < response.body().size(); i++) {
                                            if (databaseHelper.InsertIngredients(
                                                    response.body().get(i).getId(),
                                                    response.body().get(i).getNameAr(),
                                                    response.body().get(i).getNameEn(),
                                                    response.body().get(i).getNote(),
                                                    response.body().get(i).getPrice(),
                                                    response.body().get(i).getUnit(),
                                                    response.body().get(i).getSku(),
                                                    response.body().get(i).getAddByUserId(),
                                                    response.body().get(i).getCreatedAt(),
                                                    response.body().get(i).getUpdatedAt(),
                                                    response.body().get(i).getCode(),
                                                    response.body().get(i).getPivot().getProductId(),
                                                    response.body().get(i).getPivot().getIngredientId(),
                                                    response.body().get(i).getPivot().getQuantity()


                                            )) {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("Ingradient error")
                                                        .setText("Ingradient insert error")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            } else {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("ingradient error")
                                                        .setText("Ingradient insert error")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            }
                                        }
                                        progressBar.dismiss();
                                        Alerter.create(SyncActivity.this)
                                                .setTitle(" Ingradient done")
                                                .setText("Ingradient insert success")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();

                                    } else {
                                        Alerter.create(SyncActivity.this)
                                                .setTitle("Ingradient no data")
                                                .setText("Ingradient no data array")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();

                                    }

                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    //mProgressBar.setVisibility(View.GONE);
                                    Alerter.create(SyncActivity.this)
                                            .setTitle("Ingradient error")
                                            .setText("ingradient insert already")

                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();
                                }
                            } finally {
//                                        Global.SqlLitDb.endTransaction();
                            progressBar.dismiss();
                        }
                    }


                        @Override
                        public void onFailure(Call<List<ModelIngradient>> call, Throwable t) {

                        }
                    });
                }
                Intent i= new Intent(SyncActivity.this,PinCodeAc.class);
                startActivity(i);
            }

            else {
                Alerter.create(SyncActivity.this)
                        .setTitle("network")
                        .setText("network error")

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();

            }


        } else {
            Alerter.create(SyncActivity.this)
                    .setTitle("no internet")
                    .setText("no internet access")

                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(2000).show();
        }

    }

    private void GET_Modifires() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
                    progressBar.show();
//                    Global.SqlLitDb.beginTransaction();
                    Global.openDataBase();
                    ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    Call<List<ModelModifiors>> call = apiInterface.getAllModifiers(token);
                    call.enqueue(new Callback<List<ModelModifiors>>() {
                        @Override
                        public void onResponse(Call<List<ModelModifiors>> call, Response<List<ModelModifiors>> response) {

                            try {
                                try {
                                    if (!response.equals("")) {

                                        databaseHelper.deleteAllTableData("modifires");
                                        for (int i = 0; i < response.body().size(); i++) {
                                            if (databaseHelper.InsertModifires(
                                                    response.body().get(i).getId(),
                                                    response.body().get(i).getNameAr(),
                                                    response.body().get(i).getNameEn(),
                                                    response.body().get(i).getSku(),
                                                    response.body().get(i).getCost(),
                                                    response.body().get(i).getTax(),
                                                    response.body().get(i).getPrice(),
                                                    response.body().get(i).getUnit(),
                                                    response.body().get(i).getAddByUserId(),
                                                    response.body().get(i).getCreatedAt(),
                                                    response.body().get(i).getUpdatedAt(),
                                                    response.body().get(i).getCode(),
                                                    response.body().get(i).getPivot().getProductId(),
                                                    response.body().get(i).getPivot().getProductId()
                                            )) {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("Modifiers insert")
                                                        .setText("Modifiers insert done")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            } else {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("Modifiers error")
                                                        .setText("Modifiers insert error")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            }
                                        }
                                        Alerter.create(SyncActivity.this)
                                                .setTitle(" Modifiers done")
                                                .setText("Modifiers insert success")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();
                                    } else {

                                        progressBar.dismiss();
                                        Alerter.create(SyncActivity.this)
                                                .setTitle("Modifiers no data")
                                                .setText("Modifiers no data array")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();

                                    }
                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    //mProgressBar.setVisibility(View.GONE);
                                    Alerter.create(SyncActivity.this)
                                            .setTitle("modifiers error")
                                            .setText("modifiers insert already")

                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();
                                }
                            } finally {
//                                            Global.SqlLitDb.endTransaction();
                                progressBar.dismiss();
                            }
                        }


                        @Override
                        public void onFailure(Call<List<ModelModifiors>> call, Throwable t) {
                            progressBar.dismiss();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            t.printStackTrace();

                        }
                    });
                }
            } else {
                Alerter.create(SyncActivity.this)
                        .setTitle("network")
                        .setText("network error")

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();
            }


        } else {
            Alerter.create(SyncActivity.this)
                    .setTitle("no internet")
                    .setText("no internet access")

                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(2000).show();


        }
    }

    private void GET_Tables() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(SyncActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SyncActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                } else {
                    progressBar.show();
//                    Global.SqlLitDb.beginTransaction();
                    Global.openDataBase();
                    ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    Call<List<ModelTables>> call = apiInterface.getAllTaables(token);
                    call.enqueue(new Callback<List<ModelTables>>() {
                        @Override
                        public void onResponse(Call<List<ModelTables>> call, Response<List<ModelTables>> response) {
                            try {
                                try {
                                    if (!response.equals("")) {
                                        databaseHelper.deleteAllTableData("Tables");
                                        for (int i = 0; i < response.body().size(); i++) {
                                            if (databaseHelper.InsertTables(
                                                    response.body().get(i).getId(),
                                                    response.body().get(i).getName(),
                                                    response.body().get(i).getNumber(),
                                                    response.body().get(i).getChairsNumber(),
                                                    response.body().get(i).getMaxChairsNumber(),
                                                    response.body().get(i).getStatus(),
                                                    response.body().get(i).getFloorId(),
                                                    response.body().get(i).getBranchId(),
                                                    response.body().get(i).getAddByUserId(),
                                                    response.body().get(i).getCreatedAt(),
                                                    response.body().get(i).getUpdatedAt())) {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("Taables insert")
                                                        .setText("Taables insert done")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();

                                            } else {
                                                Alerter.create(SyncActivity.this)
                                                        .setTitle("Tables error")
                                                        .setText("Taables insert error")

                                                        .setBackgroundColorRes(
                                                                R.color.orange)
                                                        .setDuration(2000).show();
                                            }


                                        }

                                        Alerter.create(SyncActivity.this)
                                                .setTitle(" Tables done")
                                                .setText("Tables insert success")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                        progressBar.dismiss();

                                    } else {

                                        progressBar.dismiss();
                                        Alerter.create(SyncActivity.this)
                                                .setTitle("Taables no data")
                                                .setText("Tables no data array")

                                                .setBackgroundColorRes(
                                                        R.color.orange)
                                                .setDuration(2000).show();
                                    }


                                } catch (Exception e) {
                                    progressBar.dismiss();
                                    //mProgressBar.setVisibility(View.GONE);
                                    Alerter.create(SyncActivity.this)
                                            .setTitle("table error")
                                            .setText("table insert already")

                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(2000).show();
                                }
                            } finally {
//                                        Global.SqlLitDb.endTransaction();
                                progressBar.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ModelTables>> call, Throwable t) {
                            progressBar.dismiss();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });


                }
            } else {
                Alerter.create(SyncActivity.this)
                        .setTitle("network")
                        .setText("network error")

                        .setBackgroundColorRes(
                                R.color.orange)
                        .setDuration(2000).show();
            }
        } else {
            Alerter.create(SyncActivity.this)
                    .setTitle("no internet")
                    .setText("no internet access")

                    .setBackgroundColorRes(
                            R.color.orange)
                    .setDuration(5000).show();
        }
    }
    public void onBackPressed() {

        openDialog();

    }
    public void openDialog() {
        close_Dialoge close_dialoge= new close_Dialoge();
        close_dialoge.show(getSupportFragmentManager(), "close dialog");
    }

}



