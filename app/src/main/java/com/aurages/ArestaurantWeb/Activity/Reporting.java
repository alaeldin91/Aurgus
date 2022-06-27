package com.aurages.ArestaurantWeb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aurages.ArestaurantWeb.R;

public class Reporting extends AppCompatActivity {

    CardView card_report_bills,card_report_mats_sale;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reporting);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        card_report_bills = findViewById(R.id.card_report_bills);
        card_report_mats_sale = findViewById(R.id.card_report_mats_sale);
        card_report_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Reporting.this,Report_Bills.class));
            }
        });
        card_report_mats_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Reporting.this,Report_mat_sale.class));
                //Toast.makeText(Reporting.this, "Mats Sales", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
