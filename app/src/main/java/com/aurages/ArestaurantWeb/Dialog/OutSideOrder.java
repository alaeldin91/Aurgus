package com.aurages.ArestaurantWeb.Dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.aurages.ArestaurantWeb.Model.UserHostModel;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SharePref;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

public class OutSideOrder extends AppCompatDialogFragment implements View.OnClickListener {


    CardView btn_delv, btn_inside;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.outsideorder, null);
        builder.setView(view);

//        Bundle args = getArguments();
//        mat_img_guid = args.getString("mat_img_guid");

        btn_delv = view.findViewById(R.id.outside_delivery);
        btn_inside = view.findViewById(R.id.outside_in);
        btn_delv.setOnClickListener(this);
        btn_inside.setOnClickListener(this);


        return builder.create();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.outside_delivery:{
                Toast.makeText(getContext(), "Delivery", Toast.LENGTH_SHORT).show();
                openDialogAwayDet(getFragmentManager());
                break;
            }
            case R.id.outside_in:{
                Toast.makeText(getContext(), "Take away", Toast.LENGTH_SHORT).show();
                openDialogAwayDet(getFragmentManager());
                break;
            }
        }
    }

    public void openDialogAwayDet(FragmentManager fragmentManager) {

//        if (!com.sama.securitymodule.DBSecurityHelper.IsActivated(this.getApplicationContext())) {
//            Snackbar_msg("Version was Not Activated !",0);
//        }else{

        TakeAwayDetails takeAwayDetails = new TakeAwayDetails();
        //Bundle args=new Bundle();
        takeAwayDetails.show(fragmentManager
                ,"Take away details");
        takeAwayDetails.setCancelable(true);
        //}

    }

}
