package com.aurages.ArestaurantWeb.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.aurages.ArestaurantWeb.Dialog.close_Dialoge;
import com.aurages.ArestaurantWeb.Model.local.DataManager;
import com.aurages.ArestaurantWeb.R;
import com.google.android.material.navigation.NavigationView;
import com.tapadoo.alerter.Alerter;

import java.lang.invoke.ConstantCallSite;

public class DashboardActivity extends AppCompatActivity {
    TextView dashboardCashierNameTextView;
    DrawerLayout drawerLayout;
    TextView host_name_txt;
    NavigationView navigationView;
    Toolbar toolbar;
    private static final String SHARED_PREF_NAME = "aurages_rest";
    ConstraintLayout constants;
    DrawerLayout mDrawerLayout;
    NavigationView mDrawerLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Handler handler = new Handler();
        setContentView(R.layout.nav_drawer);
        constants = findViewById(R.id.constants);
        mDrawerLayout = findViewById(R.id.n_drawer);
        mDrawerLay = findViewById(R.id.n_view);


        SharedPreferences sp = this.getSharedPreferences(SHARED_PREF_NAME, this.MODE_PRIVATE);
        String name = sp.getString("name", "");
        View headerView = mDrawerLay.getHeaderView(0);
        Menu menuNav = mDrawerLay.getMenu();
        MenuItem logoutItem = menuNav.findItem(R.id.nav_menu_login_ac);
        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
              //  String token = DataManager.read(DataManager.token,"");
                DataManager.write(DataManager.token,"");
                Intent i= new Intent(DashboardActivity.this,NewLoginActivity.class);
                startActivity(i);



                return true;
            }
        });
        TextView host_name_txt = (TextView) headerView.findViewById(R.id.host_name_txt);

        host_name_txt.setText(name);

        Animation an2 = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        constants.startAnimation(an2);
        //Alerter.create(this).enableVibration(true).
        //setBackgroundColorRes(R.color.orange).setTitle("HELLO").setText("WELCOME BACK"+" " +name+" !")
        //.enableSwipeToDismiss().show();
        dashboardCashierNameTextView = (TextView) findViewById(R.id.dashboardCashierNameTextView);
        Button ordersPageButton = (Button) findViewById(R.id.dashboardOpenOrdersButton);
        Button aboutPageButton = (Button) findViewById(R.id.dashboardOpenInfoButton);
        Button pinCodeButtonPage = (Button) findViewById(R.id.dashboardOpenPinCodeButton);
        Button billsPageButton = (Button) findViewById(R.id.dashboardOpenBillsButton);
        Button removePageButton = (Button) findViewById(R.id.dashboardOpenRemoveButton);
        Button cashOutPageButton = (Button) findViewById(R.id.dashboardOpenCashoutButton);
        Button settingsPageButton = (Button) findViewById(R.id.dashboardOpenSettingsButton);
        Button activeLiecencePage = (Button) findViewById(R.id.dashboardOpenActiveButton);
        Button reportPageButton = (Button) findViewById(R.id.dashboardOpenReportsButton);
        dashboardCashierNameTextView.setText(name);
// click listeners for buttons.. (*_*)


        ordersPageButton.setOnClickListener(v -> navigateToOrders());
        pinCodeButtonPage.setOnClickListener(v -> navigateToPinCode());
        activeLiecencePage.setOnClickListener(v -> showPopUps());
        aboutPageButton.setOnClickListener(v -> showPopUps());
        billsPageButton.setOnClickListener(v -> showPopUps());
        cashOutPageButton.setOnClickListener(v -> showPopUps());
        settingsPageButton.setOnClickListener(v -> navigateToSetting());
        reportPageButton.setOnClickListener(v -> showPopUps());
        removePageButton.setOnClickListener(v -> showPopUps());

    }

    private void showPopUps() {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Alerter.create(this).setText("COMING SOON!").enableSwipeToDismiss().enableVibration(true)
                .setBackgroundColorRes(R.color.app_blue).show();

    }

    private void navigateToPinCode() {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Intent intent = new Intent(getApplicationContext(), PinCodeAc.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void navigateToOrders() {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Intent intent = new Intent(getApplicationContext(), OrdersScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void navigateToSetting() {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_menu_login_ac:
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();


                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onBackPressed() {

     openDialog();

    }
    public void openDialog() {
        close_Dialoge close_dialoge= new close_Dialoge();
        close_dialoge.show(getSupportFragmentManager(), "close dialog");
    }
}