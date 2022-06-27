package com.aurages.ArestaurantWeb.Dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.PaymentAdapter;
import com.aurages.ArestaurantWeb.Adapter.PaymentAdapterSave;
import com.aurages.ArestaurantWeb.Model.ImageModel;
import com.aurages.ArestaurantWeb.Model.Model_PaymentType;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

public class payment_save  extends AppCompatDialogFragment {

    private static final String SHARED_PREF_NAME = "aurages_rest";
    public static CardView card_cash, card_bycard, card_smart;
    public Activity activity;
    FragmentManager fragmentManager;
    String type_payment;
    public static Tables tables;

    private LinearLayoutManager horizontalLayoutManager;

    public static RecyclerView payment_rec;
    public static ArrayList<Model_PaymentType> paymentTypesModels = new ArrayList<>();
    public static PaymentAdapterSave adapterpayment;
    String type;
    String billid;

    public payment_save(String type, String billid) {
        this.type = type;
        this.billid = billid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment, container, false);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        payment_rec = view.findViewById(R.id.recycler_payments);
        payment_rec.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        //  horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
       // paymentTypesModels = databaseHelper.GetAllPayment();
        adapterpayment = new PaymentAdapterSave(paymentTypesModels, fillImage(), type, getContext(),payment_save.this);
        //payment_rec.setLayoutManager(horizontalLayoutManager);

        payment_rec.setAdapter(adapterpayment);

        return view;
    }

    public ArrayList<ImageModel> fillImage() {
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