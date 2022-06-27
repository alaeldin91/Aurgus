package com.aurages.ArestaurantWeb.Dialog;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.aurages.ArestaurantWeb.Dialog.TabItems.OneFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.ThreeFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.TwoFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.ViewPagerAdapter;
import com.aurages.ArestaurantWeb.R;
import com.google.android.material.tabs.TabLayout;

public class TabViewDialog extends AppCompatDialogFragment {

//
//    EditText password;
//    AppCompatButton login_btn;
//    ProgressBar log_ProgressBar;


    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentManager fragmentManager;
    public  TabViewDialog(FragmentManager f){

        fragmentManager = f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.tabviewdialog, null);
        builder.setView(view);
        //Bundle args = getArguments();
//        mat_img_guid = args.getString("mat_img_guid");


        tabLayout = view.findViewById(R.id.viewdialogtab);
        viewPager = view.findViewById(R.id.viewdialogviewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new OneFragment(),"One");
        adapter.addFragment(new TwoFragment(),"Two");
        adapter.addFragment(new ThreeFragment(),"Three");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return builder.create();
    }


}
