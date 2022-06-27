package com.aurages.ArestaurantWeb.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.aurages.ArestaurantWeb.Adapter.AdapterTables;
import com.aurages.ArestaurantWeb.Adapter.PaymentAdapter;
import com.aurages.ArestaurantWeb.Adapter.Table_Adapter;
import com.aurages.ArestaurantWeb.Dialog.TabItems.OneFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.ThreeFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.TwoFragment;
import com.aurages.ArestaurantWeb.Dialog.TabItems.ViewPagerAdapter;
import com.aurages.ArestaurantWeb.Model.ImageModel;
import com.aurages.ArestaurantWeb.Model.ModelTables;
import com.aurages.ArestaurantWeb.Model.Model_PaymentType;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiClient;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment extends AppCompatDialogFragment {

    private static final String SHARED_PREF_NAME = "aurages_rest";
    public static CardView card_cash, card_bycard, card_smart;
    public Activity activity;
    FragmentManager fragmentManager;
    String type_payment;
    public static Tables tables;

    private LinearLayoutManager horizontalLayoutManager;

    public static RecyclerView payment_rec;
    public static ArrayList<Model_PaymentType> paymentTypesModels = new ArrayList<>();
    public static PaymentAdapter adapterpayment;
    String type;
    String billid;
    public Payment(String type,String billid) {
        this.type = type;
        this.billid=billid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.payment, container, false);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        payment_rec= view.findViewById(R.id.recycler_payments);
        payment_rec.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
      //  horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
       // paymentTypesModels = databaseHelper.GetAllPayment();
        //adapterpayment = new PaymentAdapter( paymentTypesModels,fillImage(), type,getContext(),Payment.this);
        //payment_rec.setLayoutManager(horizontalLayoutManager);

        payment_rec.setAdapter(adapterpayment);

        return view;
    }
public ArrayList<ImageModel>fillImage(){
    ArrayList<ImageModel> imageModelArrayList = new ArrayList<>();
    ImageModel imageModel0 = new ImageModel();
    imageModel0.setId(System.currentTimeMillis());
    imageModel0.setImagePath(R.drawable.ic_coin);
    imageModelArrayList.add(imageModel0);
    ImageModel imageModel1 = new ImageModel();
    imageModel1.setId(System.currentTimeMillis());
    imageModel1.setImagePath(R.drawable.ic_card);
    imageModelArrayList.add(imageModel1);
    ImageModel imageModel2 = new ImageModel();
    imageModel2.setId(System.currentTimeMillis());
    imageModel2.setImagePath(R.drawable.ic_creditdebit);
    imageModelArrayList.add(imageModel2);
    ImageModel imageModel3 = new ImageModel();
    imageModel3.setId(System.currentTimeMillis());
    imageModel3.setImagePath(R.drawable.ic_creditsmart);
    imageModelArrayList.add(imageModel3);
    ImageModel imageModel4 = new ImageModel();
    imageModel4.setId(System.currentTimeMillis());
    imageModel4.setImagePath(R.drawable.ic_creditdebit);
    imageModelArrayList.add(imageModel4);


    return imageModelArrayList;
}




}