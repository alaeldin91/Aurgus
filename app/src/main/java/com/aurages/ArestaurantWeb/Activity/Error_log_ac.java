package com.aurages.ArestaurantWeb.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.AdapterErrorLog;
import com.aurages.ArestaurantWeb.Model.Error_log;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.ArrayList;

public class Error_log_ac extends AppCompatActivity {
    public RecyclerView rec_err;
    Toolbar toolbar;
    ArrayList<Error_log> error_logs;
    AdapterErrorLog adapterErrorLog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_log);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Error Log Error
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(this);
//        for(int i=0;i<5;i++){
//            databaseHelper.InsertTo_Error("Error"+i,"error"+i+"error"+i+"error"+i+"error"+i);
//        }

        progressBar = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.VISIBLE);

        rec_err = findViewById(R.id.rec_log_err);
        rec_err.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        error_logs = new ArrayList<>();
        error_logs = databaseHelper.Get_error_logs();

        if (error_logs.size() == 0) {
            Toast.makeText(this, "Array size = 0", Toast.LENGTH_SHORT).show();
        } else {
            adapterErrorLog = new AdapterErrorLog(this, error_logs, getSupportFragmentManager());
            rec_err.setAdapter(adapterErrorLog);
            progressBar.setVisibility(View.GONE);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.error_loc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bill_report_menu_del: {
       OpenDialog("Delete error log?");
                break;
            }
        }

        return true;
    }

    public void OpenDialog(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Error_log_ac.this);
        builder.setMessage(msg)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(Error_log_ac.this);
                                databaseHelper.deleteAllTableData("Error_log");
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

}
