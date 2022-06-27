package com.aurages.ArestaurantWeb.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Adapter.AdapterTemp;
import com.aurages.ArestaurantWeb.Dialog.TabItems.OneFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.ThreeFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.TwoFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.ViewPagerAdapter;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.google.android.material.tabs.TabLayout;

public class TabLayDialog extends Dialog {

    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentManager fragmentManager;

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

    public Activity activity;
    public TabLayDialog(@NonNull Activity a, FragmentManager fragmentManager ) {
        super(a);
        this.activity = a;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tablayout);
        //getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        getWindow().setLayout(((getWidth(activity) / 100) * 90), ((getHeight(activity) / 100) * 90));
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        tabLayout = findViewById(R.id.viewdialogtab);
        viewPager = findViewById(R.id.viewdialogviewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
        adapter.addFragment(new OneFragment(),"EAT IN");
        adapter.addFragment(new TwoFragment(),"TAKEAWAY");
        adapter.addFragment(new ThreeFragment(),"DELIVERY");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }


    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    public static int getHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }



}
