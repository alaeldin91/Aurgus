package com.aurages.ArestaurantWeb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;

import com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions;
import com.aurages.ArestaurantWeb.Dialog.TabFragmentView.OneFragmentView;
import com.aurages.ArestaurantWeb.Dialog.TabFragmentView.ThreeFragmentView;
import com.aurages.ArestaurantWeb.Dialog.TabFragmentView.TwoFragmentView;
import com.aurages.ArestaurantWeb.Dialog.TabItems.OneFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.ThreeFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.TwoFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.ViewPagerAdapter;
import com.aurages.ArestaurantWeb.Model.ModelBill;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Tablayout extends AppCompatActivity {


    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentManager fragmentManager;
    String type;
    ImageButton close_tab;


    private int[] tabIcons = {
            R.drawable.meal,
            R.drawable.takeaway,
            R.drawable.deliverymotorbike
    };

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        this.setFinishOnTouchOutside(true);
        //getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setLayout(((DialogeSave_PrinterOptions.getWidth(this) / 100) * 90), ((DialogeSave_PrinterOptions.getHeight(this) / 100) * 90));
        //Toolbar
        toolbar = findViewById(R.id.public_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        SqlLiteDataBase databaseHelper= new SqlLiteDataBase(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");
        }

        tabLayout = findViewById(R.id.viewdialogtab);
        viewPager = findViewById(R.id.viewdialogviewpager);
        close_tab= findViewById(R.id.close_tab);
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Tablayout.this,OrdersScreen.class);
                startActivity(i);
            }
        });
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        switch (type){
            case "view":{

                adapter.addFragment(new OneFragmentView(),"EAT IN");
                adapter.addFragment(new TwoFragmentView(),"External");
                adapter.addFragment(new ThreeFragmentView(),"TAKEAWAY");
                break;}
            case "close":{
                adapter.addFragment(new OneFragment(),"EAT IN");
                adapter.addFragment(new TwoFragment(),"TAKEAWAY");
                adapter.addFragment(new ThreeFragment(),"DELIVERY");
                break;}
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }
}
