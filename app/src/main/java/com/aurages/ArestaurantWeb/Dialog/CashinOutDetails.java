package com.aurages.ArestaurantWeb.Dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.aurages.ArestaurantWeb.R;

public class CashinOutDetails extends AppCompatDialogFragment implements View.OnClickListener {


    //CardView btn_delv, btn_inside;
    Button btn_save;
    String d_type;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.cashinout_details, null);
        builder.setView(view);

        Bundle args = getArguments();
        d_type = args.getString("d_type");

        btn_save  = view.findViewById(R.id.btn_save_takeawy_details);
        btn_save.setOnClickListener(this);


        return builder.create();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save_takeawy_details:{
                Toast.makeText(getContext(), d_type + " "+"done", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
