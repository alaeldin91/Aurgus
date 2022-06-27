package com.aurages.ArestaurantWeb.Dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.aurages.ArestaurantWeb.R;

public class Cash_in_out extends AppCompatDialogFragment implements View.OnClickListener {


    CardView btn_in, btn_out;
    //String d_type;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.outincash, null);
        builder.setView(view);

//        Bundle args = getArguments();
//        d_type = args.getString("d_type");

        btn_in = view.findViewById(R.id.cash_in);
        btn_out = view.findViewById(R.id.cash_out);
        btn_in.setOnClickListener(this);
        btn_out.setOnClickListener(this);


        return builder.create();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cash_in:{
                //Toast.makeText(getContext(), "Delivery", Toast.LENGTH_SHORT).show();
                openDialogAwayDet(getFragmentManager(),"in");
                break;
            }
            case R.id.cash_out:{
                //Toast.makeText(getContext(), "Take away", Toast.LENGTH_SHORT).show();
                openDialogAwayDet(getFragmentManager(),"out");
                break;
            }
        }
    }

    public void openDialogAwayDet(FragmentManager fragmentManager,String type) {

//        if (!com.sama.securitymodule.DBSecurityHelper.IsActivated(this.getApplicationContext())) {
//            Snackbar_msg("Version was Not Activated !",0);
//        }else{

        CashinOutDetails cashinOutDetails = new CashinOutDetails();
        Bundle args=new Bundle();
        args.putString("d_type",type);
        cashinOutDetails.setArguments(args);
        cashinOutDetails.show(fragmentManager
                ,"cashinOutDetails details");
        cashinOutDetails.setCancelable(true);
        //}

    }

}
